package com.zhimali.zheng.apps;

import com.zheng.zchlibrary.apps.BaseApplication;
import com.zheng.zchlibrary.utils.SharedPrefUtils;

/**
 * Created by Zheng on 2018/4/20.
 */

public class MyApplication extends BaseApplication {

    public static final String TOKEN_TAG= "zhimali_token";

    private static MyApplication mSingleInstance;

    public static MyApplication getInstance(){
        return mSingleInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSingleInstance= this;
    }

    //是否保存有用户,保存有长度大于0的token信息即视为保存有用户
    public boolean isHadUser(){
        if (SharedPrefUtils.contains(getApplicationContext(), TOKEN_TAG)){
            String token= (String) SharedPrefUtils.get(getApplicationContext(), TOKEN_TAG, "");
            if (token!= null && token.length()>0){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

}
