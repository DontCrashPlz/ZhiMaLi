package com.zhimali.zheng.http;

import com.zhimali.zheng.bean.AppBaseEntity;
import com.zhimali.zheng.bean.CategoryEntity;
import com.zhimali.zheng.bean.FansEntity;
import com.zhimali.zheng.bean.HelpDetailEntity;
import com.zhimali.zheng.bean.HelpEntity;
import com.zhimali.zheng.bean.HttpResult;
import com.zhimali.zheng.bean.NewsDetailEntity;
import com.zhimali.zheng.bean.NewsListEntity;
import com.zhimali.zheng.bean.NoticeDetailEntity;
import com.zhimali.zheng.bean.NoticeEntity;
import com.zhimali.zheng.bean.PosterEntity;
import com.zhimali.zheng.bean.UserEntity;
import com.zhimali.zheng.bean.YueBiEntity;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;

/**
 * Created by Zheng on 2018/4/22.
 */

public interface ApiService {

    //1 http://www.52zhimali.com/index.php?m=mobile&c=member&a=verify
    @GET("/index.php")
    Observable<HttpResult<String>> getYanZhengMa(@QueryMap Map<String, String> params);

    //2 http://www.52zhimali.com/index.php?m=mobile&c=member&a=register
    @GET("/index.php")
    Observable<HttpResult<String>> doRegister(@QueryMap Map<String, String> params);

    //3 http://www.52zhimali.com/index.php?m=mobile&c=member&a=login
    @GET("/index.php")
    Observable<HttpResult<String>> doLogin(@QueryMap Map<String, String> params);

    //4 http://www.52zhimali.com/index.php?m=mobile&c=member&a=wechatLogin
    @GET("/index.php")
    Observable<HttpResult<String>> doWechatLogin(@QueryMap Map<String, String> params);

    //5 http://www.52zhimali.com/index.php?m=mobile&c=member&a=bindMobile
    @GET("/index.php")
    Observable<HttpResult<String>> bindMobile(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //6 http://www.52zhimali.com/index.php?m=mobile&c=member&a=resetPassword
    @GET("/index.php")
    Observable<HttpResult<String>> resetPassword(@QueryMap Map<String, String> params);

    //7 http://www.52zhimali.com/index.php?m=mobile&c=member&a=information
    @GET("/index.php")
    Observable<HttpResult<UserEntity>> getUserInfo(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //8 http://www.52zhimali.com/index.php?m=mobile&c=member&a=editAvatar
    //设置头像请求方式为：multipart/form-data, 客户端需对图片预先进行无损压缩
    @GET("/index.php")
    Call changeUserHead(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //9 http://www.52zhimali.com/index.php?m=mobile&c=member&a=editMember
    @GET("/index.php")
    Observable<HttpResult<String>> changeUserName(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //10 http://www.52zhimali.com/index.php?m=mobile&c=member&a=editPassword
    @GET("/index.php")
    Observable<HttpResult<String>> changePassword(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //11 http://www.52zhimali.com/index.php?m=mobile&c=member&a=bindInviteCode
    @GET("/index.php")
    Observable<HttpResult<String>> bindInviteCode(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //12 http://www.52zhimali.com/index.php?m=mobile&c=member&a=fans
    @GET("/index.php")
    Observable<HttpResult<ArrayList<FansEntity>>> getFansList(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //13 http://www.52zhimali.com/index.php?m=mobile&c=member&a=withdraw
    @GET("/index.php")
    Observable<HttpResult<String>> applyTiXian(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //14 http://www.52zhimali.com/index.php?m=mobile&c=member&a=walletRecord
    @GET("/index.php")
    Observable<HttpResult<ArrayList<YueBiEntity>>> getYueBiHistory(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //15 http://www.52zhimali.com/index.php?m=mobile&c=member&a=signin
    @GET("/index.php")
    Observable<HttpResult<String>> doSignIn(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //16 http://www.52zhimali.com/index.php?m=mobile&c=content&a=category
    @GET("/index.php")
    Observable<HttpResult<ArrayList<CategoryEntity>>> getCategory(@QueryMap Map<String, String> params);

    //17 http://www.52zhimali.com/index.php?m=mobile&c=content&a=news_list
    @GET("/index.php")
    Observable<HttpResult<ArrayList<NewsListEntity>>> getNewsList(@QueryMap Map<String, String> params);

    //18 http://www.52zhimali.com/index.php?m=mobile&c=content&a=news_detail
    @GET("/index.php")
    Observable<HttpResult<NewsDetailEntity>> getNewsDetail(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //19 http://www.52zhimali.com/index.php?m=mobile&c=other&a=about
    @GET("/index.php")
    Observable<HttpResult<String>> getAboutUs(@QueryMap Map<String, String> params);

    //20 http://www.52zhimali.com/index.php?m=mobile&c=other&a=feedback
    @GET("/index.php")
    Observable<HttpResult<String>> sendFeedback(@QueryMap Map<String, String> params);

    //21 http://www.52zhimali.com/index.php?m=mobile&c=other&a=business
    @GET("/index.php")
    Observable<HttpResult<String>> getBusiness(@QueryMap Map<String, String> params);

    //22 http://www.52zhimali.com/index.php?m=mobile&c=notice&a=notice_list
    @GET("/index.php")
    Observable<HttpResult<ArrayList<NoticeEntity>>> getNoticeList(@QueryMap Map<String, String> params);

    //23 http://www.52zhimali.com/index.php?m=mobile&c=notice&a=notice_detail
    @GET("/index.php")
    Observable<HttpResult<NoticeDetailEntity>> getNoticeDetail(@QueryMap Map<String, String> params);

    //24 http://www.52zhimali.com/index.php?m=mobile&c=help&a=help_list
    @GET("/index.php")
    Observable<HttpResult<ArrayList<HelpEntity>>> getHelpList(@QueryMap Map<String, String> params);

    //25 http://www.52zhimali.com/index.php?m=mobile&c=help&a=help_detail
    @GET("/index.php")
    Observable<HttpResult<HelpDetailEntity>> getHelpDetail(@QueryMap Map<String, String> params);

    //26 http://www.52zhimali.com/index.php?m=mobile&c=content&a=news_view_charge
    @GET("/index.php")
    Observable<HttpResult<String>> doCharge(@Header("Xauth")String token, @QueryMap Map<String, String> params);

    //27 http://www.52zhimali.com/index.php?m=mobile&c=poster&a=poster_list
    @GET("/index.php")
    Observable<HttpResult<ArrayList<PosterEntity>>> getPosterList(@QueryMap Map<String, String> params);

    //28 http://www.52zhimali.com/index.php?m=mobile&c=device&a=init
    @GET("/index.php")
    Observable<HttpResult<String>> initApp(@Header("Xversion")String xVersion, @QueryMap Map<String, String> params);

    //29 http://www.52zhimali.com/index.php?m=mobile&c=sys&a=config
    @GET("/index.php")
    Observable<HttpResult<AppBaseEntity>> getAppBaseInfo(@QueryMap Map<String, String> params);
}
