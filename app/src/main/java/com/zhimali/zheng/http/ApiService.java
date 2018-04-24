package com.zhimali.zheng.http;

import com.zhimali.zheng.bean.LoginEntity;
import com.zhimali.zheng.bean.RegisterEntity;
import com.zhimali.zheng.bean.YanZhengMaEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Zheng on 2018/4/22.
 */

public interface ApiService {

//    http://www.52zhimali.com/index.php?m=mobile&c=member&a=wechatLogin
//    http://www.52zhimali.com/index.php?m=mobile&c=member&a=bindMobile
//    http://www.52zhimali.com/index.php?m=mobile&c=member&a=resetPassword

    //http://www.52zhimali.com/index.php?m=mobile&c=member&a=verify
    @GET("/index.php")
    Call<YanZhengMaEntity> getYanZhengMa(@QueryMap Map<String, String> params);

    //http://www.52zhimali.com/index.php?m=mobile&c=member&a=register
    @GET("/index.php")
    Call<RegisterEntity> doRegister(@QueryMap Map<String, String> params);

    //http://www.52zhimali.com/index.php?m=mobile&c=member&a=login
    @GET("/index.php")
    Call<LoginEntity> doLogin(@QueryMap Map<String, String> params);

}
