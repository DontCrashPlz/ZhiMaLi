package com.zhimali.zheng.module_mine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zheng.zchlibrary.apps.BaseFragment;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zhimali.zheng.R;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.bean.UserEntity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zheng on 2018/4/19.
 */

public class HomeMineFragment extends BaseFragment implements View.OnClickListener {

    public static HomeMineFragment newInstance(String content){
        HomeMineFragment instance = new HomeMineFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    private CircleImageView mUserHeadCiv;
    private TextView mUserNameTv;
    private ImageView mUserDetailIv;

    private TextView mFansBtn;
    private TextView mYueBiBtn;
    private TextView mQianDaoBtn;

    private RelativeLayout mNoticeRly;
    private RelativeLayout mHelpRly;
    private RelativeLayout mBusinessRly;
    private RelativeLayout mTiXianRly;
    private RelativeLayout mInviteCodeRly;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_mine, container, false);

        initUI(mView);

        return mView;
    }

    private void initUI(View mView) {
        mUserHeadCiv= mView.findViewById(R.id.mine_civ_yonghu);
//        mUserHeadCiv.setOnClickListener(this);
        mUserNameTv= mView.findViewById(R.id.mine_tv_username);
        mUserNameTv.setOnClickListener(this);
        mUserDetailIv= mView.findViewById(R.id.mine_iv_user_detail);
        mUserDetailIv.setOnClickListener(this);

        mFansBtn= mView.findViewById(R.id.mine_btn_fans);
        mFansBtn.setOnClickListener(this);
        mYueBiBtn= mView.findViewById(R.id.mine_btn_yuebi);
        mYueBiBtn.setOnClickListener(this);
        mQianDaoBtn= mView.findViewById(R.id.mine_btn_qiandao);
        mQianDaoBtn.setOnClickListener(this);

        mNoticeRly= mView.findViewById(R.id.mine_rly_notice);
        mNoticeRly.setOnClickListener(this);
        mHelpRly= mView.findViewById(R.id.mine_rly_help);
        mHelpRly.setOnClickListener(this);
        mBusinessRly= mView.findViewById(R.id.mine_rly_business);
        mBusinessRly.setOnClickListener(this);
        mTiXianRly= mView.findViewById(R.id.mine_rly_tixian);
        mTiXianRly.setOnClickListener(this);
        mInviteCodeRly= mView.findViewById(R.id.mine_rly_invite_code);
        mInviteCodeRly.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
//            case R.id.mine_civ_yonghu:{
//                if (!isSignIn()){
//                    showShortToast("请先登录");
//                    return;
//                }
//                startActivity(new Intent(getRealContext(), UserDetailActivity.class));
//                break;
//            }
            case R.id.mine_tv_username:{
                if (MyApplication.getInstance().isHadUser()){//如果已登录，提示一下
                    showShortToast("您已登录");
                }else {//如果没有登录，跳转到登录界面
                    startActivity(new Intent(getRealContext(), LoginActivity.class));
                }
                break;
            }
            case R.id.mine_iv_user_detail:{
                startActivity(new Intent(getRealContext(), SettingActivity.class));
                break;
            }
            case R.id.mine_btn_fans:{
                if (MyApplication.getInstance().isHadUser()){
                    if (MyApplication.appUser.getFans()< 1){
                        showShortToast("您还没有粉丝");
                    }else {
                        startActivity(new Intent(getRealContext(), FansActivity.class));
                    }
                }else {
                    showShortToast("请先登录");
                }
                break;
            }
            case R.id.mine_btn_yuebi:{
                if (MyApplication.getInstance().isHadUser()){
                    startActivity(new Intent(getRealContext(), YueBiActivity.class));
                }else {//如果没有登录，跳转到登录界面
                    showShortToast("请先登录");
                }
                break;
            }
            case R.id.mine_btn_qiandao:{
                if (MyApplication.getInstance().isHadUser()){
                    if (MyApplication.appUser.getSigned()== 0){
                        showShortToast("签到");
                    }else if (MyApplication.appUser.getSigned()== 1){
                        showShortToast("您已签到");
                    }
                }else {//如果没有登录，跳转到登录界面
                    showShortToast("请先登录");
                }
                break;
            }
            case R.id.mine_rly_notice:{

                break;
            }
            case R.id.mine_rly_help:{

                break;
            }
            case R.id.mine_rly_business:{
                startActivity(new Intent(getRealContext(), BusinessActivity.class));
                break;
            }
            case R.id.mine_rly_tixian:{

                break;
            }
            case R.id.mine_rly_invite_code:{

                break;
            }
            default:
                showShortToast("点击事件分发错误");
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (MyApplication.getInstance().isHadUser()){
            Glide.with(this)
                    .load(MyApplication.appUser.getAvatar())
                    .asBitmap()
                    .placeholder(R.mipmap.yonghu)
                    .error(R.mipmap.yonghu)
                    .into(mUserHeadCiv);
            mUserNameTv.setText(MyApplication.appUser.getNickname());
            mFansBtn.setText("粉丝" + MyApplication.appUser.getFans());
            mYueBiBtn.setText("阅币" + MyApplication.appUser.getCoin());
            if (MyApplication.appUser.getSigned()== 0){
                mQianDaoBtn.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        getResources().getDrawable(R.mipmap.qd),
                        null,
                        null);
            }else if (MyApplication.appUser.getSigned()== 1){
                mQianDaoBtn.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        getResources().getDrawable(R.mipmap.qdd),
                        null,
                        null);
            }
        }else {
            mUserHeadCiv.setImageResource(R.mipmap.yonghu);
            mUserNameTv.setText("立即登录");
            mFansBtn.setText("粉丝");
            mYueBiBtn.setText("阅币");
        }
    }

    @Override
    public void initProgressDialog() {

    }

    @Override
    public void initProgressBar(View view) {

    }
}
