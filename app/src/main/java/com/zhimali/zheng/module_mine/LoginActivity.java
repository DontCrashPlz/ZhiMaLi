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
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.SharedPrefUtils;
import com.zhimali.zheng.R;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.bean.LoginEntity;
import com.zhimali.zheng.http.Network;

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
                String username= mUserNemEt.getText().toString();
                String password= mPasswordEt.getText().toString();
                if (username== null || username.length()!= 11){
                    showShortToast("请输入11位手机号码");
                    return;
                }
                if (password== null || password.length()== 0){
                    showShortToast("请输入密码");
                    return;
                }
                Network.getInstance().doLogin(
                        username,
                        password,
                        new IAsyncLoadListener<LoginEntity>() {
                            @Override
                            public void onSuccess(LoginEntity loginEntity) {
                                showShortToast(loginEntity.getMsg());
                                if (loginEntity.getCode()== 0){
                                    LogUtil.d("register data", loginEntity.getData());
                                    MyApplication.getInstance().setToken(loginEntity.getData());
                                    MyApplication.getInstance().loadUser();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(String msg) {
                                showShortToast(msg);
                            }
                        });
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
