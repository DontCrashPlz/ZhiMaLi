package com.zhimali.zheng.module_mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;
import com.zhimali.zheng.R;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.http.HttpUtils;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by Zheng on 2018/4/19.
 */

public class NameSetActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;

    private EditText mNameSetEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_set);

        initUI();
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

    private void initUI() {
        mBackBtn= findViewById(R.id.toolbar_back);
        mBackBtn.setOnClickListener(this);
        mTitleTv= findViewById(R.id.toolbar_text);
        mTitleTv.setText("修改昵称");
        mFuncationTv= findViewById(R.id.toolbar_funcation);
        mFuncationTv.setText("保存");
        mFuncationTv.setOnClickListener(this);

        mNameSetEt= findViewById(R.id.name_set_edittext);
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.toolbar_back:{
                finish();
                break;
            }
            case R.id.toolbar_funcation:{
                final String name= mNameSetEt.getText().toString();

                if (name== null || name.length()== 0){
                    showShortToast("请输入昵称");
                    return;
                }

                LogUtil.d("name", name);

                addNetWork(Network.getInstance()
                        .changeName(MyApplication.appToken, name)
                        .compose(ResponseTransformer.changeThread())
                        .compose(ResponseTransformer.handleResult())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                dismissProgressDialog();
                                showShortToast("设置成功");
                                Intent intent= new Intent();
                                intent.putExtra(UserDetailActivity.DATA_TAG_NAME, name);
                                setResult(UserDetailActivity.resultCode_name, intent);
                                finish();
                                MyApplication.getInstance().loadUser();
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
}
