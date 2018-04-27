package com.zhimali.zheng.http;

import com.zhimali.zheng.bean.BindMobileEntity;
import com.zhimali.zheng.bean.CategoryResponseEntity;
import com.zhimali.zheng.bean.ChangePasswordEntity;
import com.zhimali.zheng.bean.FansResponseEntity;
import com.zhimali.zheng.bean.FindPasswordEntity;
import com.zhimali.zheng.bean.InviteCodeEntity;
import com.zhimali.zheng.bean.LoginEntity;
import com.zhimali.zheng.bean.NameSetEntity;
import com.zhimali.zheng.bean.NewsListResponseEntity;
import com.zhimali.zheng.bean.RegisterEntity;
import com.zhimali.zheng.bean.SignInEntity;
import com.zhimali.zheng.bean.TiXianEntity;
import com.zhimali.zheng.bean.UserResponseEntity;
import com.zhimali.zheng.bean.YanZhengMaEntity;
import com.zhimali.zheng.bean.YueBiResponseEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Zheng on 2018/4/22.
 */

public interface ApiService {

    //1 http://www.52zhimali.com/index.php?m=mobile&c=member&a=verify
    @GET("/index.php")
    Call<YanZhengMaEntity> getYanZhengMa(@QueryMap Map<String, String> params);

    //2 http://www.52zhimali.com/index.php?m=mobile&c=member&a=register
    @GET("/index.php")
    Call<RegisterEntity> doRegister(@QueryMap Map<String, String> params);

    //3 http://www.52zhimali.com/index.php?m=mobile&c=member&a=login
    @GET("/index.php")
    Call<LoginEntity> doLogin(@QueryMap Map<String, String> params);

    //4 http://www.52zhimali.com/index.php?m=mobile&c=member&a=wechatLogin
    @GET("/index.php")
    Call<LoginEntity> doWechatLogin(@QueryMap Map<String, String> params);

    //5 http://www.52zhimali.com/index.php?m=mobile&c=member&a=bindMobile
    @GET("/index.php")
    Call<BindMobileEntity> bindMobile(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //6 http://www.52zhimali.com/index.php?m=mobile&c=member&a=resetPassword
    @GET("/index.php")
    Call<FindPasswordEntity> resetPassword(@QueryMap Map<String, String> params);

    //7 http://www.52zhimali.com/index.php?m=mobile&c=member&a=information
    @GET("/index.php")
    Call<UserResponseEntity> getUserInfo(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //8 http://www.52zhimali.com/index.php?m=mobile&c=member&a=editAvatar
    //设置头像请求方式为：multipart/form-data, 客户端需对图片预先进行无损压缩
    @GET("/index.php")
    Call changeUserHead(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //9 http://www.52zhimali.com/index.php?m=mobile&c=member&a=editMember
    @GET("/index.php")
    Call<NameSetEntity> changeUserName(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //10 http://www.52zhimali.com/index.php?m=mobile&c=member&a=editPassword
    @GET("/index.php")
    Call<ChangePasswordEntity> changePassword(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //11 http://www.52zhimali.com/index.php?m=mobile&c=member&a=bindInviteCode
    @GET("/index.php")
    Call<InviteCodeEntity> bindInviteCode(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //12 http://www.52zhimali.com/index.php?m=mobile&c=member&a=fans
    @GET("/index.php")
    Call<FansResponseEntity> getFansList(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //13 http://www.52zhimali.com/index.php?m=mobile&c=member&a=withdraw
    @GET("/index.php")
    Call<TiXianEntity> applyTiXian(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //14 http://www.52zhimali.com/index.php?m=mobile&c=member&a=walletRecord
    @GET("/index.php")
    Call<YueBiResponseEntity> getYueBiHistory(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //15 http://www.52zhimali.com/index.php?m=mobile&c=member&a=signin
    @GET("/index.php")
    Call<SignInEntity> doSignIn(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //16 http://www.52zhimali.com/index.php?m=mobile&c=content&a=category
    @GET("/index.php")
    Call<CategoryResponseEntity> getCategory(@QueryMap Map<String, String> params);

    //17 http://www.52zhimali.com/index.php?m=mobile&c=content&a=news_list
    @GET("/index.php")
    Call<NewsListResponseEntity> getNewsList(@QueryMap Map<String, String> params);

}
