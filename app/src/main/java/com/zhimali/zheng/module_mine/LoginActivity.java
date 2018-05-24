package com.zhimali.zheng.module_mine;

import android.content.DialogInterface;
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
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;
import com.zhimali.zheng.R;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

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

    @Override
    public void initProgress() {
        mProgressDialog= new ProgressDialog(getRealContext());
        mProgressDialog.setLabel("正在登录..");
        mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearNetWork();
            }
        });
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

                addNetWork(Network.getInstance().doLogin(username, password)
                        .compose(ResponseTransformer.changeThread())
                        .compose(ResponseTransformer.handleResult())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String str) throws Exception {
                                dismissProgressDialog();
                                LogUtil.d("register data", str);
                                MyApplication.getInstance().setToken(str);
                                MyApplication.getInstance().loadUser();
                                finish();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dismissProgressDialog();
                                showShortToast(throwable.toString());
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                dismissProgressDialog();
                            }
                        }, new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                showProgressDialog();
                            }
                        }));
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
