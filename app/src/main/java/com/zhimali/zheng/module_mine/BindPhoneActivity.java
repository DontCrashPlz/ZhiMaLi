package com.zhimali.zheng.module_mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Zheng on 2018/4/19.
 */

public class BindPhoneActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;

    private EditText mPhoneEt;
    private EditText mYanZhengMaEt;
    private Button mGetBtn;
    private Button mBindBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);

        initUI();
    }

    @Override
    public void initProgress() {
        mProgressDialog= new ProgressDialog(getRealContext());
        mProgressDialog.setLabel("正在绑定手机..");
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
        mTitleTv.setText("手机绑定");
        mFuncationTv= findViewById(R.id.toolbar_funcation);
        mFuncationTv.setVisibility(View.GONE);

        mPhoneEt= findViewById(R.id.bind_phone_et_number);
        mYanZhengMaEt= findViewById(R.id.bind_phone_et_yanzhengma);
        mGetBtn= findViewById(R.id.bind_phone_btn_get);
        mGetBtn.setOnClickListener(this);
        mBindBtn= findViewById(R.id.bind_phone_btn_bind);
        mBindBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.toolbar_back:{
                finish();
                break;
            }
            case R.id.bind_phone_btn_get:{
                String mobileNum= mPhoneEt.getText().toString();
                if (mobileNum== null || mobileNum.length()!= 11){
                    showShortToast("请输入11位手机号码");
                }else {
                    Network.getInstance().getYanZhengMa(this, mobileNum);
                }
                break;
            }
            case R.id.bind_phone_btn_bind:{
                final String mobileNum= mPhoneEt.getText().toString();
                String yanZhengMa= mYanZhengMaEt.getText().toString();

                if (mobileNum== null || mobileNum.length()!= 11){
                    showShortToast("请输入11位手机号码");
                    return;
                }

                if (yanZhengMa== null || yanZhengMa.length()== 0){
                    showShortToast("请输入您的验证码");
                    return;
                }

                LogUtil.d("mobileNum", mobileNum);
                LogUtil.d("yanZhengMa", yanZhengMa);

                addNetWork(Network.getInstance()
                        .bindMobile(MyApplication.appToken, mobileNum, yanZhengMa)
                        .compose(ResponseTransformer.changeThread())
                        .compose(ResponseTransformer.handleResult())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                dismissProgressDialog();
                                Intent intent= new Intent();
                                intent.putExtra(UserDetailActivity.DATA_TAG_MOBILE, mobileNum);
                                setResult(UserDetailActivity.resultCode_mobile, intent);
                                finish();
                                MyApplication.getInstance().loadUser();
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
            default:
                showShortToast("点击事件分发错误");
                break;
        }
    }
}
