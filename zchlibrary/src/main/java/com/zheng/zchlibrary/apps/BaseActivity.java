package com.zheng.zchlibrary.apps;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.zheng.zchlibrary.interfaces.IBaseView;
import com.zheng.zchlibrary.utils.LogUtil;

/**
 * Created by Zheng on 2017/10/16.
 */

public class BaseActivity extends AppCompatActivity implements IBaseView {

    private final String ACTIVITY_TAG= this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }

        LogUtil.e(ACTIVITY_TAG, ACTIVITY_TAG + " was Created.");
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().removeActivity(this);
        LogUtil.e(ACTIVITY_TAG, ACTIVITY_TAG + " was Destroyed.");
        super.onDestroy();
    }

    @Override
    public void showLongToast(String msg) {
        if (msg!= null)
            Toast.makeText(getRealContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showShortToast(String msg) {
        if (msg!= null)
            Toast.makeText(getRealContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getRealContext() {
        return this;
    }
}
