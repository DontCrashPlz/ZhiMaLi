package com.zhimali.zheng.apps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zheng.zchlibrary.apps.BaseActivity;
import com.zhimali.zheng.R;

/**
 * Created by Zheng on 2018/4/17.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startActivity(new Intent(getRealContext(), HomeActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void initProgress() {

    }
}
