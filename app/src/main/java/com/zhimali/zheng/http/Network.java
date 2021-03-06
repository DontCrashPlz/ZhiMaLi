package com.zhimali.zheng.http;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zheng.zchlibrary.utils.LogUtil;
import com.zhimali.zheng.apps.MyApplication;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
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
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                mOkHttpClient= new OkHttpClient.Builder()
//                        .cookieJar(new NovateCookieManger(MyApplication.getInstance()))
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15,TimeUnit.SECONDS)
                        .addInterceptor(logging)
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

    private Map<String, String> getContentParamMap(String networkTag){
        Map<String, String> baseParamMap= new HashMap<>();
        baseParamMap.put(NetParams.PARAM1, NetParams.VALUE1);
        baseParamMap.put(NetParams.PARAM2, NetParams.VALUE3);
        baseParamMap.put(NetParams.PARAM_TAG, networkTag);
        return baseParamMap;
    }

    private Map<String, String> getOtherParamMap(String networkTag){
        Map<String, String> baseParamMap= new HashMap<>();
        baseParamMap.put(NetParams.PARAM1, NetParams.VALUE1);
        baseParamMap.put(NetParams.PARAM2, NetParams.VALUE4);
        baseParamMap.put(NetParams.PARAM_TAG, networkTag);
        return baseParamMap;
    }

    private Map<String, String> getNoticeParamMap(String networkTag){
        Map<String, String> baseParamMap= new HashMap<>();
        baseParamMap.put(NetParams.PARAM1, NetParams.VALUE1);
        baseParamMap.put(NetParams.PARAM2, NetParams.VALUE5);
        baseParamMap.put(NetParams.PARAM_TAG, networkTag);
        return baseParamMap;
    }

    private Map<String, String> getHelpParamMap(String networkTag){
        Map<String, String> baseParamMap= new HashMap<>();
        baseParamMap.put(NetParams.PARAM1, NetParams.VALUE1);
        baseParamMap.put(NetParams.PARAM2, NetParams.VALUE6);
        baseParamMap.put(NetParams.PARAM_TAG, networkTag);
        return baseParamMap;
    }

    private Map<String, String> getPosterParamMap(String networkTag){
        Map<String, String> baseParamMap= new HashMap<>();
        baseParamMap.put(NetParams.PARAM1, NetParams.VALUE1);
        baseParamMap.put(NetParams.PARAM2, NetParams.VALUE7);
        baseParamMap.put(NetParams.PARAM_TAG, networkTag);
        return baseParamMap;
    }

    private Map<String, String> getDeviceParamMap(String networkTag){
        Map<String, String> baseParamMap= new HashMap<>();
        baseParamMap.put(NetParams.PARAM1, NetParams.VALUE1);
        baseParamMap.put(NetParams.PARAM2, NetParams.VALUE_DEVICE);
        baseParamMap.put(NetParams.PARAM_TAG, networkTag);
        return baseParamMap;
    }

    private Map<String, String> getSysParamMap(String networkTag){
        Map<String, String> baseParamMap= new HashMap<>();
        baseParamMap.put(NetParams.PARAM1, NetParams.VALUE1);
        baseParamMap.put(NetParams.PARAM2, NetParams.VALUE_SYS);
        baseParamMap.put(NetParams.PARAM_TAG, networkTag);
        return baseParamMap;
    }

    /**
     * 1 获取验证码
     * @param mobile
     */
    public Observable<HttpResult<String>> getYanZhengMa(String mobile){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_YANZHENGMA);
        params.put("mobile", mobile);
        return apiService.getYanZhengMa(params);
    }

    /**
     * 2 注册
     * @param mobile
     * @param yanZhengMa
     * @param password
     * @param inviteCode
     */
    public Observable<HttpResult<String>> doRegister(String mobile,
                           String yanZhengMa,
                           String password,
                           String inviteCode){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_REGISTER);
        params.put("mobile", mobile);
        params.put("password", password);
//        params.put("gender","1");
        params.put("verify", yanZhengMa);
        if (inviteCode!= null && inviteCode.length()> 0){
            params.put("invite_code", inviteCode);
        }
        return apiService.doRegister(params);
    }

    /**
     * 3 登录
     * @param mobile
     * @param password
     */
    public Observable<HttpResult<String>> doLogin(String mobile,
                        String password){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_LOGIN);
        params.put("mobile", mobile);
        params.put("password", password);
        return apiService.doLogin(params);
    }

    /**
     * 4 微信登录
     * @param openid
     * @param avatar
     * @param nickname
     */
    public Observable<HttpResult<String>> doWechatLogin(String openid,
                              String avatar,
                              String nickname){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_WEIXIN);
        return apiService.doWechatLogin(params);
    }

    /**
     * 5 绑定手机
     * @param token
     * @param mobile
     * @param verify
     */
    public Observable<HttpResult<String>> bindMobile(String token,
                           String mobile,
                           String verify){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_BIND_PHONE);
        params.put("mobile", mobile);
        params.put("verify", verify);
        return apiService.bindMobile(token, params);
    }

    /**
     * 6 重置密码
     * @param mobile
     * @param password
     * @param repassword
     * @param verify
     */
    public Observable<HttpResult<String>> resetPassword(String mobile,
                              String password,
                              String repassword,
                              String verify){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_RESET_PASSWORD);
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("repassword", repassword);
        params.put("verify", verify);
        return apiService.resetPassword(params);
    }

    /**
     * 7 获取用户信息
     * @param token
     */
    public Observable<HttpResult<UserEntity>> getUserInfo(String token){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_USER_INFO);
        return apiService.getUserInfo(token, params);
    }

    /**
     * 8 修改头像
     */
    public Observable<HttpResult<String>> changeHead(String token, String filePath){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_CHANGE_HEAD);
        params.put("avatar", "avatar");
        //1、根据地址拿到File
        File file = new File(filePath);
        LogUtil.d("图片绝对路径： ", file.getAbsolutePath());
        //2、创建RequestBody，其中`multipart/form-data`为编码类型
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        //3、创建`MultipartBody.Part`，其中需要注意第一个参数`fileUpload`需要与服务器对应,也就是`键`
        MultipartBody.Part part = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
        return apiService.changeUserHead(token, params, part);
    }

    /**
     * 9 修改昵称
     * @param token
     * @param nickname
     */
    public Observable<HttpResult<String>> changeName(String token,
                           String nickname){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_CHANGE_USERNAME);
        params.put("nickname", nickname);
        return apiService.changeUserName(token, params);
    }

    /**
     * 10 修改密码
     * @param token
     * @param mobile
     * @param password
     * @param repassword
     * @param verify
     */
    public Observable<HttpResult<String>> changePassword(String token,
                           String mobile,
                           String password,
                           String repassword,
                           String verify){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_CHANGE_PASSWORD);
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("repassword", repassword);
        params.put("verify", verify);
        return apiService.changePassword(token, params);
    }

    /**
     * 11 绑定邀请码
     * @param token
     * @param code
     */
    public Observable<HttpResult<String>> bindInviteCode(String token,
                               String code){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_INVITE_CODE);
        params.put("code", code);
        return apiService.bindInviteCode(token, params);
    }

    /**
     * 12 获取粉丝列表
     */
    public Observable<HttpResult<ArrayList<FansEntity>>> getFans(String token,
                                                                 int pagesize,
                                                                 int page,
                                                                 String search){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_FANS);
        params.put("pagesize", String.valueOf(pagesize));
        params.put("page", String.valueOf(page));
        if (search!= null && search.length()> 0){
            params.put("search", search);
        }
        return apiService.getFansList(token, params);
    }

    /**
     * 13 申请提现
     * @param token
     * @param money
     * @param type
     * @param account
     * @param mobile
     * @param verify
     */
    public Observable<HttpResult<String>> applyTiXian(String token,
                            String money,
                            String type,
                            String account,
                            String mobile,
                            String verify){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_TIXIAN);
        params.put("money", money);
        params.put("type", type);
        params.put("account", account);
        params.put("mobile", mobile);
        params.put("verify", verify);
        return apiService.applyTiXian(token, params);
    }

    /**
     * 14 阅币记录
     * @param token
     * @param type
     * @param pagesize
     * @param page
     */
    public Observable<HttpResult<ArrayList<YueBiEntity>>> getYueBiHistory(String token,
                                                                          String type,
                                                                          String pagesize,
                                                                          String page){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_YUEBI);
        params.put("type", type);
        params.put("pagesize", pagesize);
        params.put("page", page);
        return apiService.getYueBiHistory(token, params);
    }

    /**
     * 15 签到
     * @param token
     */
    public Observable<HttpResult<String>> doSignIn(String token){
        Map<String, String> params=
                getBaseParamMap(NetParams.TAG_SIGNIN);
        return apiService.doSignIn(token, params);
    }

    /**
     * 16 获取频道列表
     */
    public Observable<HttpResult<ArrayList<CategoryEntity>>> getCategory(){
        Map<String, String> params=
                getContentParamMap(NetParams.TAG_CATEGORY);
        return apiService.getCategory(params);
    }

    /**
     * 17 获取频道新闻列表
     * @param catid
     * @param page
     * @param search
     */
    public Observable<HttpResult<ArrayList<NewsListEntity>>> getNewsList(
            String catid,
            String page,
            String search){
        Map<String, String> params=
                getContentParamMap(NetParams.TAG_NEWSLIST);
        params.put("catid", catid);
        params.put("pagesize", "20");
        params.put("page", page);
        if (search!= null && search.length()> 0){
            params.put("search", search);
        }
        return apiService.getNewsList(params);
    }

    /**
     * 18 获取新闻详情
     * @param token
     * @param id
     */
    public Observable<HttpResult<NewsDetailEntity>> getNewsDetail(
            String token,
            String id){
        Map<String, String> params=
                getContentParamMap(NetParams.TAG_NEWSDETAIL);
        params.put("id", id);
        if (MyApplication.sw!= null && MyApplication.sw.length()> 0){
            params.put("sw", MyApplication.sw);
        }
        if (MyApplication.sh!= null && MyApplication.sh.length()> 0){
            params.put("sh", MyApplication.sh);
        }
        if (MyApplication.uuid!= null && MyApplication.uuid.length()> 0){
            params.put("uuid", MyApplication.uuid);
        }

//        params.put("uuid", "");
        return apiService.getNewsDetail(token, params);
    }

    /**
     * 19 关于我们
     */
    public Observable<HttpResult<String>> getAboutUs(){
        Map<String, String> params=
                getOtherParamMap(NetParams.TAG_ABOUT_US);
        return apiService.getAboutUs(params);
    }

    /**
     * 20 用户反馈
     * @param content
     */
    public Observable<HttpResult<String>> sendFeedBack(String content){
        Map<String, String> params=
                getOtherParamMap(NetParams.TAG_FEEDBACK);
        params.put("content", content);
        return apiService.sendFeedback(params);
    }

    /**
     * 21 商务合作
     */
    public Observable<HttpResult<String>> getBusiness(){
        Map<String, String> params=
                getOtherParamMap(NetParams.TAG_BUSINESS);
        return apiService.getBusiness(params);
    }

    /**
     * 22 公告列表
     */
    public Observable<HttpResult<ArrayList<NoticeEntity>>> getNoticeList(){
        Map<String, String> params=
                getNoticeParamMap(NetParams.TAG_NOTICE_LIST);
        return apiService.getNoticeList(params);
    }

    /**
     * 23 公告详情
     */
    public Observable<HttpResult<NoticeDetailEntity>> getNoticeDetail(String id){
        Map<String, String> params=
                getNoticeParamMap(NetParams.TAG_NOTICE_DETAIL);
        params.put("id", id);
        return apiService.getNoticeDetail(params);
    }

    /**
     * 24 帮助列表
     */
    public Observable<HttpResult<ArrayList<HelpEntity>>> getHelpList(){
        Map<String, String> params=
                getHelpParamMap(NetParams.TAG_HELP_LIST);
        return apiService.getHelpList(params);
    }

    /**
     * 25 帮助详情
     */
    public Observable<HttpResult<HelpDetailEntity>> getHelpDetail(String id){
        Map<String, String> params=
                getHelpParamMap(NetParams.TAG_HELP_DETAIL);
        params.put("id", id);
        return apiService.getHelpDetail(params);
    }

    /**
     * 26 计费
     * @param token
     */
    public Observable<HttpResult<String>> doCharge(
            String token,
            String viewId){
        Map<String, String> params=
                getContentParamMap(NetParams.TAG_DO_CHARGE);
        params.put("view_id", viewId);
        if (MyApplication.uuid!= null && MyApplication.uuid.length()> 0){
            params.put("uuid", MyApplication.uuid);
        }
        return apiService.doCharge(token, params);
    }

    /**
     * 27 获取广告列表
     * @param posid 广告位id
     */
    public Observable<HttpResult<ArrayList<PosterEntity>>> getPosterList(String posid){
        Map<String, String> params=
                getPosterParamMap(NetParams.TAG_POSTER_LIST);
        params.put("posid", posid);
        return apiService.getPosterList(params);
    }

    /**
     * 28 设备信息上报
     * @param xVersion
     * @param uuid
     * @param brand
     * @param network
     * @param gps
     * @param screen
     * @return
     */
    public Observable<HttpResult<String>> initApp(
            String xVersion,
            String uuid,
            @Nullable String brand,
            @Nullable String network,
            @Nullable String gps,
            @Nullable String screen){
        Map<String, String> params=
                getDeviceParamMap(NetParams.TAG_INITAPP);
        params.put("uuid", uuid);
        if (brand!= null) params.put("brand", brand);
        if (network!= null) params.put("network", network);
        if (gps!= null) params.put("gps", gps);
        if (screen!= null) params.put("screen", screen);
        return apiService.initApp(xVersion, params);
    }

    /**
     * 29 获取APP基础信息
     */
    public Observable<HttpResult<AppBaseEntity>> getAppInfo(){
        Map<String, String> params=
                getSysParamMap(NetParams.TAG_GET_APPBASEINFO);
        return apiService.getAppBaseInfo(params);
    }

}
