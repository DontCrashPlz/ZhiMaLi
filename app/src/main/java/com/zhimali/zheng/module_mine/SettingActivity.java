package com.zhimali.zheng.module_mine;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.DataCleanManager;
import com.zheng.zchlibrary.utils.SharedPrefUtils;
import com.zhimali.zheng.R;
import com.zhimali.zheng.apps.MyApplication;

import java.io.File;

/**
 * Created by Zheng on 2018/4/19.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;

    private TextView mZhangHaoTv;
    private TextView mPasswordTv;
    private TextView mAboutTv;
    private TextView mFeedbackTv;
    private TextView mCleanTv;
    private Button mLogoutBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUI();
    }

    @Override
    public void initProgress() {

    }

    private void initUI() {
        mBackBtn= findViewById(R.id.toolbar_back);
        mBackBtn.setOnClickListener(this);
        mTitleTv= findViewById(R.id.toolbar_text);
        mTitleTv.setText("设置");
        mFuncationTv= findViewById(R.id.toolbar_funcation);
        mFuncationTv.setVisibility(View.GONE);

        mZhangHaoTv= findViewById(R.id.setting_tv_zhanghao);
        mZhangHaoTv.setOnClickListener(this);
        mPasswordTv= findViewById(R.id.setting_tv_password);
        mPasswordTv.setOnClickListener(this);
        mAboutTv= findViewById(R.id.setting_tv_about);
        mAboutTv.setOnClickListener(this);
        mFeedbackTv= findViewById(R.id.setting_tv_feedback);
        mFeedbackTv.setOnClickListener(this);
        mCleanTv= findViewById(R.id.setting_tv_clean);
        mCleanTv.setOnClickListener(this);
        mLogoutBtn= findViewById(R.id.setting_btn_logout);
        mLogoutBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.toolbar_back:{
                finish();
                break;
            }
            case R.id.setting_tv_zhanghao:{
                if (!MyApplication.getInstance().isHadUser()){
                    showShortToast("请先登录");
                    return;
                }
                startActivity(new Intent(getRealContext(), UserDetailActivity.class));
                break;
            }
            case R.id.setting_tv_password:{
                if (!MyApplication.getInstance().isHadUser()){
                    showShortToast("请先登录");
                    return;
                }
                startActivity(new Intent(getRealContext(), ResetPasswordActivity.class));
                break;
            }
            case R.id.setting_tv_about:{
                startActivity(new Intent(getRealContext(), AboutUsActivity.class));
                break;
            }
            case R.id.setting_tv_feedback:{
                startActivity(new Intent(getRealContext(), FeedbackActivity.class));
                break;
            }
            case R.id.setting_tv_clean:{
                showCleanCacheMenu();
                break;
            }
            case R.id.setting_btn_logout:{
                if (MyApplication.getInstance().isHadUser()){//如果已登录，退出登录
                    SharedPrefUtils.remove(this, MyApplication.TOKEN_TAG);
                    if (MyApplication.getInstance().loadLocalToken()){
                        showShortToast("退出登录失败");
                    }else {
                        showShortToast("退出登录成功");
                        MyApplication.appToken= null;
                        MyApplication.appUser= null;
                    }
                }else {//如果没有登录，弹窗提示
                    showShortToast("您还没有登录");
                }
                break;
            }
            default:
                showShortToast("点击事件分发错误");
                break;
        }
    }

    /**
     * 弹出清除缓存弹窗
     */
    private void showCleanCacheMenu(){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_clean_cache, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(this, R.style.dialog_no_title);
        dialog.setContentView(view);
        dialog.show();

        // 确认按钮监听
        view.findViewById(R.id.clean_cache_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                DataCleanManager.cleanAppCache(getRealContext());
                showShortToast("缓存已清除");
            }
        });

        // 取消按钮监听
        view.findViewById(R.id.clean_cache_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 20);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }
}
