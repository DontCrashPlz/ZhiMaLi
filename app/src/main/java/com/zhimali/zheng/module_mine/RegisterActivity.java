package com.zhimali.zheng.module_mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zheng.zchlibrary.apps.BaseActivity;
import com.zhimali.zheng.R;

/**
 * Created by Zheng on 2018/4/17.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;

    private EditText mPhoneEt;
    private EditText mYanZhengMaEt;
    private Button mGetBtn;
    private EditText mPasswordEt;
    private EditText mPasswordEt2;
    private EditText mInviteCodeEt;
    private Button mRegisterBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUI();
    }

    private void initUI() {
        mBackBtn= findViewById(R.id.toolbar_back);
        mBackBtn.setOnClickListener(this);
        mTitleTv= findViewById(R.id.toolbar_text);
        mTitleTv.setText("注册");
        mFuncationTv= findViewById(R.id.toolbar_funcation);
        mFuncationTv.setVisibility(View.GONE);

        mPhoneEt= findViewById(R.id.register_et_phone);
        mYanZhengMaEt= findViewById(R.id.register_et_yanzhengma);
        mGetBtn= findViewById(R.id.register_btn_get);
        mGetBtn.setOnClickListener(this);
        mPasswordEt= findViewById(R.id.register_et_password);
        mPasswordEt2= findViewById(R.id.register_et_password2);
        mInviteCodeEt= findViewById(R.id.register_et_invite_code);
        mRegisterBtn= findViewById(R.id.register_btn_register);
        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.toolbar_back:{
                finish();
                break;
            }
            case R.id.register_btn_get:{
                showShortToast("获取验证码");
                break;
            }
            case R.id.register_btn_register:{
                showShortToast("注册");
                break;
            }
            default:
                showShortToast("点击事件分发错误");
                break;
        }
    }
}
