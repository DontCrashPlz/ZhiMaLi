package com.zhimali.zheng.apps;

import android.os.Build;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.zheng.zchlibrary.apps.BaseApplication;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.ScreenUtils;
import com.zheng.zchlibrary.utils.SharedPrefUtils;
import com.zhimali.zheng.bean.AppBaseEntity;
import com.zhimali.zheng.bean.UserEntity;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/20.
 */

public class MyApplication extends BaseApplication {

    public static final String TOKEN_TAG= "zhimali_token";

    private static MyApplication mSingleInstance;

    public static MyApplication getInstance(){
        return mSingleInstance;
    }

    //屏幕宽度
    public static String sw;
    //屏幕高度
    public static String sh;
    //uuid
    public static String uuid;

    public static AppBaseEntity appBaseEntity;

    @Override
    public void onCreate() {
        super.onCreate();
        mSingleInstance= this;

        loadUser();

        sw= String.valueOf(ScreenUtils.getScreenWidth(this));
        sh= String.valueOf(ScreenUtils.getScreenHeight(this));
        uuid= getPesudoUniqueID();
        LogUtil.d("应用初始化： ", "屏幕宽度:" + sw + "; 屏幕高度:" + sh + "; 设备唯一标识:"+ uuid + ";");
    }

    //根据本地保存的token加载用户
    public void loadUser(){
        if (loadLocalToken()){
            refreshUser(null);
        }
    }

    /********************** Token相关方法 *************************/
    //全局token值
    public static String appToken;
    /**
     * 是否保存有token值
     */
    @Deprecated
    public boolean isHadToken(){
        if (SharedPrefUtils.contains(getApplicationContext(), TOKEN_TAG)){
            String token= (String) SharedPrefUtils.get(getApplicationContext(), TOKEN_TAG, "");
            if (token!= null && token.length()>0){
                LogUtil.d("application token", token);
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
    //设置token值
    public void setToken(String token){
        if (token== null || token.length()== 0){
            Toast.makeText(getApplicationContext(), "token值无效", Toast.LENGTH_SHORT).show();
            return;
        }
        if (SharedPrefUtils.contains(getApplicationContext(), TOKEN_TAG)){
            SharedPrefUtils.remove(getApplicationContext(), TOKEN_TAG);
        }
        SharedPrefUtils.put(getApplicationContext(), TOKEN_TAG, token);
    }
    //app启动时加载本地保存的token,返回值表示是否加载成功
    public boolean loadLocalToken(){
        if (SharedPrefUtils.contains(getApplicationContext(), TOKEN_TAG)){
            String token= (String) SharedPrefUtils.get(getApplicationContext(), TOKEN_TAG, "");
            if (token!= null && token.length()>0){
                LogUtil.d("loadLocalToken", "成功" + token);
                appToken= token;
                return true;
            }else {
                LogUtil.d("loadLocalToken", "本地token无效");
                return false;
            }
        }else {
            LogUtil.d("loadLocalToken", "没有本地token");
            return false;
        }
    }

    /********************** UserEntity相关方法 *************************/
    //全局UserEntity
    public static UserEntity appUser;
    //是否存在全局UserEntity对象
    public boolean isHadUser(){
        if (appUser== null){
            return false;
        }
        if (appUser.getUserid()!= null && appUser.getUserid().length()>0){
            return true;
        }
        return false;
    }

    /**
     * 根据token加载用户信息
     * 使用此方法前要判断appToken是否有效
     * @param listener
     * @return
     */
    public Disposable refreshUser(@Nullable final IAsyncLoadListener<UserEntity> listener){
        return Network.getInstance().getUserInfo(appToken)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<UserEntity>handleResult())
                .subscribe(new Consumer<UserEntity>() {
                    @Override
                    public void accept(UserEntity userEntity) throws Exception {
                        appUser= userEntity;
                        if (listener!= null) listener.onSuccess(userEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.d("刷新用户信息出现错误", throwable.toString());
                        if (listener!= null) listener.onFailure(throwable.toString());
                    }
                });
    }

    /**
     * 获取设备唯一标识
     * @return
     */
    private String getPesudoUniqueID() {
        String m_szDevIDShort = "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 digits
        return m_szDevIDShort;
    }

}
