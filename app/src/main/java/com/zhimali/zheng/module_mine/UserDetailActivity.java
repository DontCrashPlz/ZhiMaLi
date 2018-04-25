package com.zhimali.zheng.module_mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zhimali.zheng.R;
import com.zhimali.zheng.apps.MyApplication;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zheng on 2018/4/19.
 */

public class UserDetailActivity extends BaseActivity implements View.OnClickListener {

    private final int requestCode_head= 0x01;
    private final int requestCode_name= 0x02;
    private final int requestCode_mobile= 0x03;
    public static final int resultCode_head= 0x04;
    public static final int resultCode_name= 0x05;
    public static final int resultCode_mobile= 0x06;
    public static final String DATA_TAG_HEAD= "head";
    public static final String DATA_TAG_NAME= "name";
    public static final String DATA_TAG_MOBILE= "mobile";

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
        Glide.with(this)
                .load(MyApplication.appUser.getAvatar())
                .asBitmap()
                .placeholder(R.mipmap.yonghu)
                .error(R.mipmap.yonghu)
                .into(mUserHeadCiv);
        mNameTv= findViewById(R.id.user_detail_tv_name);
        mNameTv.setText(MyApplication.appUser.getNickname());
        mPhoneTv= findViewById(R.id.user_detail_tv_phone);
        mPhoneTv.setText(MyApplication.appUser.getMobile());
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
                startActivityForResult(
                        new Intent(getRealContext(), NameSetActivity.class),
                        requestCode_name);
                break;
            }
            case R.id.user_detail_rly_phone:{
                startActivityForResult(
                        new Intent(getRealContext(), BindPhoneActivity.class),
                        requestCode_mobile);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== requestCode_head && resultCode== resultCode_head){

        }
        if (requestCode== requestCode_name && resultCode== resultCode_name){
            mNameTv.setText(data.getStringExtra(DATA_TAG_NAME));
        }
        if (requestCode== requestCode_mobile && resultCode== resultCode_mobile){
            mPhoneTv.setText(data.getStringExtra(DATA_TAG_MOBILE));
        }
    }
}
