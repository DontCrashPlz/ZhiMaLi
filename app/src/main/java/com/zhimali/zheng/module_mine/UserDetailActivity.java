package com.zhimali.zheng.module_mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zheng.zchlibrary.apps.BaseActivity;
import com.zhimali.zheng.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zheng on 2018/4/19.
 */

public class UserDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;

    private RelativeLayout mHeadRly;
    private RelativeLayout mNameRly;
    private RelativeLayout mPhoneRly;

    private CircleImageView mUserHeadCiv;
    private TextView mNameTv;
    private TextView mPhoneTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        initUI();
    }

    private void initUI() {
        mBackBtn= findViewById(R.id.toolbar_back);
        mBackBtn.setOnClickListener(this);
        mTitleTv= findViewById(R.id.toolbar_text);
        mTitleTv.setText("帐号设置");
        mFuncationTv= findViewById(R.id.toolbar_funcation);
        mFuncationTv.setVisibility(View.GONE);

        mHeadRly= findViewById(R.id.user_detail_rly_head);
        mHeadRly.setOnClickListener(this);
        mNameRly= findViewById(R.id.user_detail_rly_name);
        mNameRly.setOnClickListener(this);
        mPhoneRly= findViewById(R.id.user_detail_rly_phone);
        mPhoneRly.setOnClickListener(this);

        mUserHeadCiv= findViewById(R.id.user_detail_civ_head);
        mNameTv= findViewById(R.id.user_detail_tv_name);
        mPhoneTv= findViewById(R.id.user_detail_tv_phone);
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.toolbar_back:{
                finish();
                break;
            }
            case R.id.user_detail_rly_head:{
                showShortToast("弹窗");
                break;
            }
            case R.id.user_detail_rly_name:{
                startActivity(new Intent(getRealContext(), NameSetActivity.class));
                break;
            }
            case R.id.user_detail_rly_phone:{
                startActivity(new Intent(getRealContext(), BindPhoneActivity.class));
                break;
            }
        }
    }
}
