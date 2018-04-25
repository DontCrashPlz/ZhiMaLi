package com.zhimali.zheng.module_mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zhimali.zheng.R;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.bean.NameSetEntity;
import com.zhimali.zheng.http.Network;

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

                Network.getInstance().changeName(
                        MyApplication.appToken,
                        name,
                        new IAsyncLoadListener<NameSetEntity>() {
                            @Override
                            public void onSuccess(NameSetEntity nameSetEntity) {
                                showShortToast(nameSetEntity.getMsg());
                                if (nameSetEntity.getCode()== 0){
                                    Intent intent= new Intent();
                                    intent.putExtra(UserDetailActivity.DATA_TAG_NAME, name);
                                    setResult(UserDetailActivity.resultCode_name, intent);
                                    finish();
                                    MyApplication.getInstance().loadUser();
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
