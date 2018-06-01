package com.zhimali.zheng.module_mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zheng.zchlibrary.apps.BaseActivity;
import com.zhimali.zheng.R;

/**
 * Created by Zheng on 2018/6/1.
 */

public class TiXianSuccessActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;

    private Button mConfirmBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tixian_success);

        initUI();
    }

    private void initUI() {
        mBackBtn= findViewById(R.id.toolbar_back);
        mBackBtn.setOnClickListener(this);
        mTitleTv= findViewById(R.id.toolbar_text);
        mTitleTv.setText("申请提现");
        mFuncationTv= findViewById(R.id.toolbar_funcation);
        mFuncationTv.setVisibility(View.GONE);

        mConfirmBtn= findViewById(R.id.tixian_success_btn_confirm);
        mConfirmBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.toolbar_back:{
                finish();
                break;
            }
            case R.id.tixian_success_btn_confirm:{
                finish();
                break;
            }
            default:
                showShortToast("点击事件分发错误");
                break;
        }
    }

    @Override
    public void initProgress() {
    }
}
