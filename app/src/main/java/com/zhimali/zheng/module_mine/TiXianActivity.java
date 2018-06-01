package com.zhimali.zheng.module_mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;
import com.zhimali.zheng.R;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.http.HttpUtils;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/19.
 */

public class TiXianActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;

    private RadioButton mWeiXinRbtn;
    private RadioButton mZhiFuBaoRbtn;
    private EditText mZhangHaoEt;
    private EditText mPhoneEt;
    private EditText mYanZhengMaEt;
    private Button mGetYanZhengMaBtn;
    private TagFlowLayout mTagFlowLayout;
    private Button mCommitBtn;

    private String money;//提现金额
    private String type;//提现方式(0:微信，1:支付宝),默认0
    private String account;//提现帐号
    private String mobile;//手机号码
    private String verif;//验证码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tixian2);

        initUI();
    }

    @Override
    public void initProgress() {
        mProgressDialog= new ProgressDialog(getRealContext());
        mProgressDialog.setLabel("请稍候..");
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
        mTitleTv.setText("申请提现");
        mFuncationTv= findViewById(R.id.toolbar_funcation);
        mFuncationTv.setVisibility(View.GONE);

        mWeiXinRbtn= findViewById(R.id.tixian_radio_weixin);
        mWeiXinRbtn.setOnCheckedChangeListener(this);
        mZhiFuBaoRbtn= findViewById(R.id.tixian_radio_zhifubao);
        mZhiFuBaoRbtn.setOnCheckedChangeListener(this);
        mWeiXinRbtn.setChecked(true);

        mZhangHaoEt= findViewById(R.id.tixian_et_zhanghao);
        mPhoneEt= findViewById(R.id.tixian_et_phone);
        mYanZhengMaEt= findViewById(R.id.tixian_et_yanzhengma);
        mGetYanZhengMaBtn= findViewById(R.id.tixian_btn_get);
        mGetYanZhengMaBtn.setOnClickListener(this);
        mTagFlowLayout= findViewById(R.id.tixian_tagflow);
        if (MyApplication.appBaseEntity== null
                || MyApplication.appBaseEntity.getWithdraw_level()== null
                || MyApplication.appBaseEntity.getWithdraw_level().size()< 1){
            showShortToast("提现梯度获取失败");
            finish();
            return;
        }
        ArrayList<Integer> mTiXianList= MyApplication.appBaseEntity.getWithdraw_level();
        mTagFlowLayout.setAdapter(new TagAdapter<Integer>(mTiXianList) {
            @Override
            public View getView(FlowLayout parent, int position, Integer integer) {
                CheckBox checkBox= (CheckBox) LayoutInflater
                        .from(getRealContext())
                        .inflate(R.layout.item_withdraw_level, parent, false);
                checkBox.setText(String.valueOf(integer));
                return checkBox;
            }
        });
        mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                CheckBox checkBox= view.findViewById(R.id.checkbox);
                money= checkBox.getText().toString();
                return false;
            }
        });
        mCommitBtn= findViewById(R.id.tixian_btn_commit);
        mCommitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.toolbar_back:{
                finish();
                break;
            }
            case R.id.tixian_btn_get:{
                mobile= mPhoneEt.getText().toString().trim();
                if (mobile== null || mobile.length()< 1){
                    showShortToast("请填写手机号码");
                    return;
                }
                addNetWork(Network.getInstance().getYanZhengMa(mobile)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(ResponseTransformer.<String>handleResult())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                dismissProgressDialog();
                                showShortToast("验证码已发送，请注意查看短信");
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dismissProgressDialog();
                                showShortToast(HttpUtils.parseThrowableMsg(throwable));
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {

                            }
                        }, new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                mProgressDialog.setLabel("正在获取验证码..");
                                showProgressDialog();
                            }
                        }));
                break;
            }
            case R.id.tixian_btn_commit:{
                if (type== null || type.length()< 1){
                    showShortToast("请选择提现方式");
                    return;
                }

                account= mZhangHaoEt.getText().toString().trim();
                if (account== null || account.length()< 1){
                    showShortToast("请填写提现帐号");
                    return;
                }

                mobile= mPhoneEt.getText().toString().trim();
                if (mobile== null || mobile.length()< 1){
                    showShortToast("请填写手机号码");
                    return;
                }

                verif= mYanZhengMaEt.getText().toString().trim();
                if (verif== null || verif.length()< 1){
                    showShortToast("请填写验证码");
                    return;
                }

                if (money== null || money.length()< 1){
                    showShortToast("请选择提现金额");
                    return;
                }

                addNetWork(Network.getInstance().applyTiXian(
                        MyApplication.appToken,
                        money,
                        type,
                        account,
                        mobile,
                        verif)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(ResponseTransformer.<String>handleResult())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                dismissProgressDialog();
                                getRealContext().startActivity(
                                        new Intent(getRealContext(), TiXianSuccessActivity.class));
                                finish();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dismissProgressDialog();
                                showShortToast(HttpUtils.parseThrowableMsg(throwable));
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {

                            }
                        }, new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                mProgressDialog.setLabel("正在提交申请..");
                                showProgressDialog();
                            }
                        }));
                break;
            }
            default:
                showShortToast("点击事件分发错误");
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId()== R.id.tixian_radio_weixin){
            if (isChecked){
                type= "0";
                if (mZhiFuBaoRbtn.isChecked()) mZhiFuBaoRbtn.setChecked(false);
            }
        }else if (buttonView.getId()== R.id.tixian_radio_zhifubao){
            if (isChecked){
                type= "1";
                if (mWeiXinRbtn.isChecked()) mWeiXinRbtn.setChecked(false);
            }
        }
    }
}
