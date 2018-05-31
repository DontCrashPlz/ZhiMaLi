package com.zhimali.zheng.module_mine;

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
import com.zhimali.zheng.R;
import com.zhimali.zheng.apps.MyApplication;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Zheng on 2018/4/19.
 */

public class TiXianActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;

    private RadioButton mWeiXinRbtn;
    private RadioButton mZhiFuBaoTbtn;
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
        mZhiFuBaoTbtn= findViewById(R.id.tixian_radio_zhifubao);
        mZhiFuBaoTbtn.setOnCheckedChangeListener(this);
        mZhangHaoEt= findViewById(R.id.tixian_et_zhanghao);
        mPhoneEt= findViewById(R.id.tixian_et_phone);
        mYanZhengMaEt= findViewById(R.id.tixian_et_yanzhengma);
        mGetYanZhengMaBtn= findViewById(R.id.tixian_btn_get);
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
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.toolbar_back:{
                finish();
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
                if (mZhiFuBaoTbtn.isChecked()) mZhiFuBaoTbtn.setChecked(false);
            }
        }else if (buttonView.getId()== R.id.tixian_radio_zhifubao){
            if (isChecked){
                type= "1";
                if (mWeiXinRbtn.isChecked()) mWeiXinRbtn.setChecked(false);
            }
        }
    }
}
