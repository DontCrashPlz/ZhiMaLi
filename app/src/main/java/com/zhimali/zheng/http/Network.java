package com.zhimali.zheng.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.zheng.zchlibrary.https.NovateCookieManger;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.bean.RegisterEntity;
import com.zhimali.zheng.bean.YanZhengMaEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zheng on 2018/4/23.
 */

public class Network {

    private static Network mInstance= null;

    public static Network getInstance(){
        if (mInstance== null){
            synchronized (Network.class){
                if (mInstance== null){
                    mInstance= new Network();
                }
            }
        }
        return mInstance;
    }

    private Network(){
        if (apiService == null) {
            if (mOkHttpClient== null){
                mOkHttpClient= new OkHttpClient.Builder()
                        .cookieJar(new NovateCookieManger(MyApplication.getInstance()))
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15,TimeUnit.SECONDS)
                        .build();
            }
            if (mRetrofit== null){
                mRetrofit= new Retrofit.Builder()
                        .client(mOkHttpClient)
                        .baseUrl(BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
            }
            apiService = mRetrofit.create(ApiService.class);
        }
    }

    private static ApiService apiService;
    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;
    private static final String BASEURL= "http://www.52zhimali.com/";

    public void getYanZhengMa(final Context context, String mobile){
        Map<String, String> params= new HashMap<>();
        params.put(NetParams.PARAM1, NetParams.VALUE1);
        params.put(NetParams.PARAM2, NetParams.VALUE2);
        params.put(NetParams.PARAM_TAG, NetParams.TAG_YANZHENGMA);
        params.put("mobile", mobile);
        Call<YanZhengMaEntity> yanZhengMaCall= apiService.getYanZhengMa(params);
        yanZhengMaCall.enqueue(new Callback<YanZhengMaEntity>() {
            @Override
            public void onResponse(Call<YanZhengMaEntity> call, Response<YanZhengMaEntity> response) {
                Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<YanZhengMaEntity> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void doRegister(String mobile,
                           String yanZhengMa,
                           String password,
                           String inviteCode,
                           final IAsyncLoadListener<RegisterEntity> listener){
        Map<String, String> params= new HashMap<>();
        params.put(NetParams.PARAM1, NetParams.VALUE1);
        params.put(NetParams.PARAM2, NetParams.VALUE2);
        params.put(NetParams.PARAM_TAG, NetParams.TAG_REGISTER);
        params.put("mobile", mobile);
        params.put("password", password);
//        params.put("gender","1");
        params.put("verify", yanZhengMa);
        if (inviteCode!= null && inviteCode.length()> 0){
            params.put("invite_code", inviteCode);
        }
        Call<RegisterEntity> registerCall= apiService.doRegister(params);
        registerCall.enqueue(new Callback<RegisterEntity>() {
            @Override
            public void onResponse(Call<RegisterEntity> call, Response<RegisterEntity> response) {
                LogUtil.d("doRegister response", response.toString());
                if (response.isSuccessful()){
                    LogUtil.d("doRegister response success", response.body().toString());
                    listener.onSuccess(response.body());
                }else {
                    LogUtil.d("doRegister response fail", response.errorBody().toString());
                    listener.onFailure("网络请求失败");
                }
            }

            @Override
            public void onFailure(Call<RegisterEntity> call, Throwable t) {
                LogUtil.d("doRegister onFailure", t.toString());
                listener.onFailure(t.toString());
            }
        });
    }

}
