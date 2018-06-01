package com.zhimali.zheng.module_mine;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.Tools;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;
import com.zhimali.zheng.R;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.bean.ParentEntity;
import com.zhimali.zheng.bean.UserEntity;
import com.zhimali.zheng.http.HttpUtils;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/6/1.
 */

public class InviteCodeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;

    private TextView mMyCodeTv;

    private FrameLayout mPanelBindable;
    private EditText mInviteCodeEt;

    private FrameLayout mPanelUnBindable;
    private TextView mInviteCodeTv;

    private Button mBindBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        if (!MyApplication.getInstance().isHadUser()){
            showShortToast("无用户信息");
            finish();
            return;
        }

        initUI();
    }

    private void initUI() {
        mBackBtn= findViewById(R.id.toolbar_back);
        mBackBtn.setOnClickListener(this);
        mTitleTv= findViewById(R.id.toolbar_text);
        mTitleTv.setText("邀请码");
        mFuncationTv= findViewById(R.id.toolbar_funcation);
        mFuncationTv.setVisibility(View.GONE);

        mMyCodeTv= findViewById(R.id.invite_tv_mycode);
        mMyCodeTv.setText(MyApplication.appUser.getInvite_code());

        mPanelBindable= findViewById(R.id.invite_panel_bindable);
        mInviteCodeEt= findViewById(R.id.invite_et_invitecode);

        mPanelUnBindable= findViewById(R.id.invite_panel_unbindable);
        mInviteCodeTv= findViewById(R.id.invite_tv_invitecode);

        mBindBtn= findViewById(R.id.invite_btn_bind);
        mBindBtn.setOnClickListener(this);

        if (MyApplication.appUser.getParent()== null){
            mPanelBindable.setVisibility(View.VISIBLE);
            mPanelUnBindable.setVisibility(View.GONE);
            mBindBtn.setVisibility(View.VISIBLE);
        }else {
            showUnBindablePanel(MyApplication.appUser.getParent());
        }
    }

    private void showUnBindablePanel(ParentEntity entity){
        mPanelBindable.setVisibility(View.GONE);
        mPanelUnBindable.setVisibility(View.VISIBLE);
        mBindBtn.setVisibility(View.GONE);
        mInviteCodeTv.setText(entity.getInvite_code());
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.toolbar_back:{
                finish();
                break;
            }
            case R.id.invite_btn_bind:{
                String inviteCode= mInviteCodeEt.getText().toString().trim();
                if (inviteCode== null || inviteCode.length()< 1){
                    showShortToast("请填写邀请码");
                    return;
                }
                addNetWork(Network.getInstance().bindInviteCode(MyApplication.appToken, inviteCode)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(ResponseTransformer.<String>handleResult())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                MyApplication.getInstance().refreshUser(new IAsyncLoadListener<UserEntity>() {
                                    @Override
                                    public void onSuccess(UserEntity userEntity) {
                                        showUnBindablePanel(userEntity.getParent());
                                        dismissProgressDialog();
                                        showShortToast("绑定成功");
                                    }

                                    @Override
                                    public void onFailure(String msg) {
                                        dismissProgressDialog();
                                        showShortToast("页面错误，但您已成功绑定");
                                        LogUtil.d("绑定邀请码成功，用户信息刷新失败： ", msg);
                                    }
                                });
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
    public void initProgress() {
        mProgressDialog= new ProgressDialog(getRealContext());
        mProgressDialog.setLabel("正在提交..");
        mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearNetWork();
            }
        });
    }
}
