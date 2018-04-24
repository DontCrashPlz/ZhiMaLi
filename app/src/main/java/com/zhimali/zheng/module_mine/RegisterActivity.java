package com.zhimali.zheng.module_mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.SharedPrefUtils;
import com.zhimali.zheng.R;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.bean.RegisterEntity;
import com.zhimali.zheng.bean.YanZhengMaEntity;
import com.zhimali.zheng.http.Network;

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
                String mobileNum= mPhoneEt.getText().toString();
                if (mobileNum== null || mobileNum.length()!= 11){
                    showShortToast("请输入11位手机号码");
                }else {
                    Network.getInstance().getYanZhengMa(this, mobileNum);
                }
                break;
            }
            case R.id.register_btn_register:{
                String mobileNum= mPhoneEt.getText().toString();
                String yanZhengMa= mYanZhengMaEt.getText().toString();
                String password= mPasswordEt.getText().toString();
                String password2= mPasswordEt2.getText().toString();
                String inviteCode= mInviteCodeEt.getText().toString();

                if (mobileNum== null || mobileNum.length()!= 11){
                    showShortToast("请输入11位手机号码");
                    return;
                }

                if (yanZhengMa== null || yanZhengMa.length()== 0){
                    showShortToast("请输入您的验证码");
                    return;
                }

                if (password== null || password.length()== 0){
                    showShortToast("请输入您的密码");
                    return;
                }

                if (password2== null || password2.length()== 0){
                    showShortToast("请确认您的密码");
                    return;
                }

                if (!password.equals(password2)){
                    showShortToast("您两次输入的密码不一致");
                    return;
                }

                LogUtil.d("mobileNum", mobileNum);
                LogUtil.d("yanZhengMa", yanZhengMa);
                LogUtil.d("password", password);
                LogUtil.d("inviteCode", inviteCode);

                Network.getInstance().doRegister(
                        mobileNum,
                        yanZhengMa,
                        password,
                        inviteCode,
                        new IAsyncLoadListener<RegisterEntity>() {
                            @Override
                            public void onSuccess(RegisterEntity registerEntity) {
                                showShortToast(registerEntity.getMsg());
                                if (registerEntity.getCode()== 0){
                                    LogUtil.d("register data", registerEntity.getData());
                                    SharedPrefUtils.put(
                                            getRealContext(),
                                            MyApplication.TOKEN_TAG,
                                            registerEntity.getData());
                                }
                            }

                            @Override
                            public void onFailure(String msg) {
                                showShortToast(msg);
                            }
                        });

                break;
            }
            default:
                showShortToast("点击事件分发错误");
                break;
        }
    }
}
