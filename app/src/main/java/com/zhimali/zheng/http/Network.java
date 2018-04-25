package com.zhimali.zheng.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.zheng.zchlibrary.https.NovateCookieManger;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.bean.BindMobileEntity;
import com.zhimali.zheng.bean.ChangePasswordEntity;
import com.zhimali.zheng.bean.FindPasswordEntity;
import com.zhimali.zheng.bean.LoginEntity;
import com.zhimali.zheng.bean.NameSetEntity;
import com.zhimali.zheng.bean.RegisterEntity;
import com.zhimali.zheng.bean.UserResponseEntity;
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

    private Map<String, String> getBaseParamMap(String networkTag){
        Map<String, String> baseParamMap= new HashMap<>();
        baseParamMap.put(NetParams.PARAM1, NetParams.VALUE1);
        baseParamMap.put(NetParams.PARAM2, NetParams.VALUE2);
        baseParamMap.put(NetParams.PARAM_TAG, networkTag);
        return baseParamMap;
    }

    public void getYanZhengMa(final Context context, String mobile){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_YANZHENGMA);
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
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_REGISTER);
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

    public void doLogin(String mobile,
                        String password,
                        final IAsyncLoadListener<LoginEntity> listener){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_LOGIN);
        params.put("mobile", mobile);
        params.put("password", password);
        Call<LoginEntity> loginCall= apiService.doLogin(params);
        loginCall.enqueue(new Callback<LoginEntity>() {
            @Override
            public void onResponse(Call<LoginEntity> call, Response<LoginEntity> response) {
                LogUtil.d("doLogin response", response.toString());
                if (response.isSuccessful()){
                    LogUtil.d("doLogin response success", response.body().toString());
                    listener.onSuccess(response.body());
                }else {
                    LogUtil.d("doLogin response fail", response.errorBody().toString());
                    listener.onFailure("网络请求失败");
                }
            }

            @Override
            public void onFailure(Call<LoginEntity> call, Throwable t) {
                LogUtil.d("doLogin onFailure", t.toString());
                listener.onFailure(t.toString());
            }
        });
    }

    public void getUserInfo(String token, final IAsyncLoadListener<UserResponseEntity> listener){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_USER_INFO);
        Call<UserResponseEntity> userInfoCall= apiService.getUserInfo(token, params);
        userInfoCall.enqueue(new Callback<UserResponseEntity>() {
            @Override
            public void onResponse(Call<UserResponseEntity> call, Response<UserResponseEntity> response) {
                LogUtil.d("getUserInfo response", response.toString());
                if (response.isSuccessful()){
                    LogUtil.d("getUserInfo response success", response.body().toString());
                    listener.onSuccess(response.body());
                }else {
                    LogUtil.d("getUserInfo response fail", response.errorBody().toString());
                    listener.onFailure("网络请求失败");
                }
            }

            @Override
            public void onFailure(Call<UserResponseEntity> call, Throwable t) {
                LogUtil.d("getUserInfo onFailure", t.toString());
                listener.onFailure(t.toString());
            }
        });
    }

    public void bindMobile(String token,
                           String mobile,
                           String verify,
                           final IAsyncLoadListener<BindMobileEntity> listener){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_BIND_PHONE);
        params.put("mobile", mobile);
        params.put("verify", verify);
        Call<BindMobileEntity> bindMobileCall= apiService.bindMobile(token, params);
        bindMobileCall.enqueue(new Callback<BindMobileEntity>() {
            @Override
            public void onResponse(Call<BindMobileEntity> call, Response<BindMobileEntity> response) {
                LogUtil.d("bindMobile response", response.toString());
                if (response.isSuccessful()){
                    LogUtil.d("bindMobile response success", response.body().toString());
                    listener.onSuccess(response.body());
                }else {
                    LogUtil.d("bindMobile response fail", response.errorBody().toString());
                    listener.onFailure("网络请求失败");
                }
            }

            @Override
            public void onFailure(Call<BindMobileEntity> call, Throwable t) {
                LogUtil.d("bindMobile onFailure", t.toString());
                listener.onFailure(t.toString());
            }
        });
    }

    public void changeName(String token,
                           String nickname,
                           final IAsyncLoadListener<NameSetEntity> listener){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_CHANGE_USERNAME);
        params.put("nickname", nickname);
        Call<NameSetEntity> changeNameCall= apiService.changeUserName(token, params);
        changeNameCall.enqueue(new Callback<NameSetEntity>() {
            @Override
            public void onResponse(Call<NameSetEntity> call, Response<NameSetEntity> response) {
                LogUtil.d("changeNameCall response", response.toString());
                if (response.isSuccessful()){
                    LogUtil.d("changeNameCall response success", response.body().toString());
                    listener.onSuccess(response.body());
                }else {
                    LogUtil.d("changeNameCall response fail", response.errorBody().toString());
                    listener.onFailure("网络请求失败");
                }
            }

            @Override
            public void onFailure(Call<NameSetEntity> call, Throwable t) {
                LogUtil.d("changeNameCall onFailure", t.toString());
                listener.onFailure(t.toString());
            }
        });
    }

    public void changePassword(String token,
                           String mobile,
                           String password,
                           String repassword,
                           String verify,
                           final IAsyncLoadListener<ChangePasswordEntity> listener){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_CHANGE_PASSWORD);
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("repassword", repassword);
        params.put("verify", verify);
        Call<ChangePasswordEntity> changePasswordCall= apiService.changePassword(token, params);
        changePasswordCall.enqueue(new Callback<ChangePasswordEntity>() {
            @Override
            public void onResponse(Call<ChangePasswordEntity> call, Response<ChangePasswordEntity> response) {
                LogUtil.d("changePasswordCall response", response.toString());
                if (response.isSuccessful()){
                    LogUtil.d("changePasswordCall response success", response.body().toString());
                    listener.onSuccess(response.body());
                }else {
                    LogUtil.d("changePasswordCall response fail", response.errorBody().toString());
                    listener.onFailure("网络请求失败");
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordEntity> call, Throwable t) {
                LogUtil.d("changePasswordCall onFailure", t.toString());
                listener.onFailure(t.toString());
            }
        });
    }

    public void resetPassword(String mobile,
                              String password,
                              String repassword,
                              String verify,
                              final IAsyncLoadListener<FindPasswordEntity> listener){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_RESET_PASSWORD);
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("repassword", repassword);
        params.put("verify", verify);
        Call<FindPasswordEntity> resetPasswordCall= apiService.resetPassword(params);
        resetPasswordCall.enqueue(new Callback<FindPasswordEntity>() {
            @Override
            public void onResponse(Call<FindPasswordEntity> call, Response<FindPasswordEntity> response) {
                LogUtil.d("resetPasswordCall response", response.toString());
                if (response.isSuccessful()){
                    LogUtil.d("resetPasswordCall response success", response.body().toString());
                    listener.onSuccess(response.body());
                }else {
                    LogUtil.d("resetPasswordCall response fail", response.errorBody().toString());
                    listener.onFailure("网络请求失败");
                }
            }

            @Override
            public void onFailure(Call<FindPasswordEntity> call, Throwable t) {
                LogUtil.d("resetPasswordCall onFailure", t.toString());
                listener.onFailure(t.toString());
            }
        });
    }

}
