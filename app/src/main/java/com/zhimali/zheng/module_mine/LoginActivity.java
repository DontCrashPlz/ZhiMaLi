package com.zhimali.zheng.module_mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zheng.zchlibrary.apps.BaseActivity;
import com.zhimali.zheng.R;

/**
 * Created by Zheng on 2018/4/17.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;

    private EditText mUserNemEt;
    private EditText mPasswordEt;

    private TextView mForgetTv;
    private Button mLoginBtn;
    private Button mRegisterBtn;

    private ImageButton mWeiXinIbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
    }

    private void initUI() {
        mBackBtn= findViewById(R.id.toolbar_back);
        mBackBtn.setOnClickListener(this);
        mTitleTv= findViewById(R.id.toolbar_text);
        mTitleTv.setText("登录");
        mFuncationTv= findViewById(R.id.toolbar_funcation);
        mFuncationTv.setVisibility(View.GONE);

        mUserNemEt= findViewById(R.id.login_et_username);
        mPasswordEt= findViewById(R.id.login_et_password);

        mForgetTv= findViewById(R.id.login_tv_forget);
        mForgetTv.setOnClickListener(this);
        mLoginBtn= findViewById(R.id.login_btn_login);
        mLoginBtn.setOnClickListener(this);
        mRegisterBtn= findViewById(R.id.login_btn_register);
        mRegisterBtn.setOnClickListener(this);

        mWeiXinIbtn= findViewById(R.id.login_btn_weixin);
        mWeiXinIbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.toolbar_back:{
                finish();
                break;
            }
            case R.id.login_tv_forget:{
                startActivity(new Intent(getRealContext(), FindPasswordActivity.class));
                break;
            }
            case R.id.login_btn_login:{
                showShortToast("登录");
                break;
            }
            case R.id.login_btn_register:{
                startActivity(new Intent(getRealContext(), RegisterActivity.class));
                break;
            }
            case R.id.login_btn_weixin:{
                showShortToast("微信");
                break;
            }
            default:
                showShortToast("点击事件分发错误");
                break;
        }
    }
}
