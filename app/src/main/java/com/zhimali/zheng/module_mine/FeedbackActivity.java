package com.zhimali.zheng.module_mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zhimali.zheng.R;
import com.zhimali.zheng.bean.FeedBackEntity;
import com.zhimali.zheng.http.Network;

/**
 * Created by Zheng on 2018/4/19.
 */

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;

    private EditText mFeedBackEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initUI();
    }

    private void initUI() {
        mBackBtn= findViewById(R.id.toolbar_back);
        mBackBtn.setOnClickListener(this);
        mTitleTv= findViewById(R.id.toolbar_text);
        mTitleTv.setText("用户反馈");
        mFuncationTv= findViewById(R.id.toolbar_funcation);
        mFuncationTv.setText("提交");
        mFuncationTv.setOnClickListener(this);
        mFeedBackEt= findViewById(R.id.feedback_et_suggest);
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
                String str= mFeedBackEt.getText().toString().trim();
                if (str== null || str.length()< 1){
                    showShortToast("请输入您的意见");
                    return;
                }
                Network.getInstance().sendFeedBack(str, new IAsyncLoadListener<FeedBackEntity>() {
                    @Override
                    public void onSuccess(FeedBackEntity feedBackEntity) {
                        showShortToast(feedBackEntity.getMsg());
                        if (feedBackEntity.getCode()== 0){
                            showShortToast(feedBackEntity.getData());
                            finish();
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
