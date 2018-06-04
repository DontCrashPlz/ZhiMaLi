package com.zhimali.zheng.apps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.NetworkUtil;
import com.zheng.zchlibrary.utils.Tools;
import com.zhimali.zheng.R;
import com.zhimali.zheng.bean.AppBaseEntity;
import com.zhimali.zheng.bean.HttpResult;
import com.zhimali.zheng.bean.PosterEntity;
import com.zhimali.zheng.http.ApiException;
import com.zhimali.zheng.http.HttpUtils;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/17.
 */

public class SplashActivity extends BaseActivity {

    private String xVersion;//当前设备版本号
    private String uuid;//设备唯一标识
    private String brand = null;//设备品牌
    private String network = null;//设备当前网络信息(0:3G，1:4G，2:WI-FI，3:其他)
    private String gps = null;//地理位置信息（格式：经度,纬度）
    private String screen = null;//设备屏幕分辨率信息（格式：宽,高 eg:720,1080）

    private Location location;

    private FrameLayout mPanelFly;
    private ImageView mPosterIv;
    private TextView mTimerTv;
    private ProgressBar mProgress;
    private Button mPassBtn;

    private ImageView mLogoIv;
    private ImageView mNameIv;
    private ImageView mRemarkIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPanelFly= findViewById(R.id.splash_panel);
        mPosterIv= findViewById(R.id.splash_poster);
        mTimerTv= findViewById(R.id.splash_timer);
        mProgress= findViewById(R.id.splash_progress);
        mPassBtn= findViewById(R.id.splash_pass);
        mPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getRealContext(), HomeActivity.class));
                finish();
            }
        });

        mLogoIv= findViewById(R.id.splash_logo);
        mNameIv= findViewById(R.id.splash_name);
        mRemarkIv= findViewById(R.id.splash_remark);

        try {
            xVersion = Tools.getVersionName(getRealContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        uuid = MyApplication.uuid;
        brand = Build.BRAND;
        switch (NetworkUtil.getNetWorkStatus(getRealContext())) {
            case NetworkUtil.NETWORK_CLASS_3_G:
                network = "0";
                break;
            case NetworkUtil.NETWORK_CLASS_4_G:
                network = "1";
                break;
            case NetworkUtil.NETWORK_WIFI:
                network = "2";
                break;
            default:
                network = "3";
                break;
        }
        gps = getGpsInfo();
        screen= MyApplication.sw + "," + MyApplication.sh;

        final Observable<String> initObservable=
                Network.getInstance().initApp(xVersion, uuid, brand, network, gps, screen)
                        .compose(ResponseTransformer.<String>handleResult());
         final Observable<AppBaseEntity> getAppInfoObservable=
                Network.getInstance().getAppInfo()
                        .compose(ResponseTransformer.<AppBaseEntity>handleResult());

        Observable.timer(2, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Long aLong) throws Exception {
                        return Observable.zip(
                                getAppInfoObservable,
                                initObservable, new BiFunction<AppBaseEntity, String, String>() {
                                    @Override
                                    public String apply(AppBaseEntity appBaseEntity, String s) throws Exception {
                                        MyApplication.appBaseEntity= appBaseEntity;
                                        return "";
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@Nullable String s) throws Exception {
                        startPoster();
//                        startActivity(new Intent(getRealContext(), HomeActivity.class));
//                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.e("初始化请求失败：", throwable.toString());
                        showShortToast(HttpUtils.parseThrowableMsg(throwable));
                        startPoster();
//                        startActivity(new Intent(getRealContext(), HomeActivity.class));
//                        finish();
                    }
                });

    }

    private void startPoster(){
        addNetWork(Network.getInstance().getPosterList("11")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<ArrayList<PosterEntity>>handleResult())
                .subscribe(new Consumer<ArrayList<PosterEntity>>() {
                    @Override
                    public void accept(final ArrayList<PosterEntity> posterEntities) throws Exception {
                        if (posterEntities!= null && posterEntities.size()> 0){
                            mPanelFly.setVisibility(View.VISIBLE);
                            mLogoIv.setVisibility(View.GONE);
                            mNameIv.setVisibility(View.GONE);
                            mRemarkIv.setVisibility(View.GONE);
                            Glide.with(getRealContext())
                                    .load(posterEntities.get(0).getImg())
                                    .asBitmap()
                                    .into(mPosterIv);
                            mPosterIv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getRealContext(), HomeActivity.class));
                                    Intent intent= new Intent(getRealContext(), PosterActivity.class);
                                    intent.putExtra("poster_url", posterEntities.get(0).getUrl());
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            timerStart();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.d("广告列表加载失败: ", throwable.toString());
                        startActivity(new Intent(getRealContext(), HomeActivity.class));
                        finish();
                    }
                }));
    }

    private void timerStart(){
        addNetWork(Observable.interval(30, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mProgress.setProgress(aLong.intValue());
                        if (aLong== 0){
                            mTimerTv.setText("3s");
                        }else if (aLong== 33){
                            mTimerTv.setText("2s");
                        }else if (aLong== 66){
                            mTimerTv.setText("1s");
                        }else if (aLong== 99){
                            mTimerTv.setText("0s");
                        }else if (aLong== 100){
                            startActivity(new Intent(getRealContext(), HomeActivity.class));
                            finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.d("倒计时错误：", throwable.toString());
                        startActivity(new Intent(getRealContext(), HomeActivity.class));
                        finish();
                    }
                }));
    }

    @Override
    public void initProgress() {

    }

    private String getGpsInfo() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        String provider;
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            // 当没有可用的位置提供器时，弹出Toast提示用户
            LogUtil.d("获取定位信息失败", "No location provider to use");
            return null;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
            return null;
        }else {
            LogUtil.d("定位信息获取方式", provider);
            location= locationManager.getLastKnownLocation(provider);
            return "" + location.getLongitude() + "," + location.getLatitude();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode== 100){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED
                    && grantResults[1]== PackageManager.PERMISSION_GRANTED){
                gps= getGpsInfo();
            }
            return;
        }

    }
}
