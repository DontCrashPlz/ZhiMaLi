package com.zhimali.zheng.http;

import android.content.Context;
import android.widget.Toast;

import com.zheng.zchlibrary.https.NovateCookieManger;
import com.zhimali.zheng.apps.MyApplication;
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
    private static final String BASEURL= "http://www.52zhimali.com/index.php/";

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

    public void doRegister(final Context context, String mobile){
        Map<String, String> params= new HashMap<>();
        params.put(NetParams.PARAM1, NetParams.VALUE1);
        params.put(NetParams.PARAM2, NetParams.VALUE2);
        params.put(NetParams.PARAM_TAG, NetParams.TAG_REGISTER);
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

}
