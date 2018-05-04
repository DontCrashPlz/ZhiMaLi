package com.zhimali.zheng.module_mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zhimali.zheng.R;
import com.zhimali.zheng.bean.AboutUsEntity;
import com.zhimali.zheng.http.Network;

/**
 * Created by Zheng on 2018/4/19.
 */

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        initUI();
    }

    private void initUI() {
        mBackBtn= findViewById(R.id.toolbar_back);
        mBackBtn.setOnClickListener(this);
        mTitleTv= findViewById(R.id.toolbar_text);
        mTitleTv.setText("关于我们");
        mFuncationTv= findViewById(R.id.toolbar_funcation);
        mFuncationTv.setVisibility(View.GONE);

        mWebView= findViewById(R.id.about_us_webview);
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        // 设置文本编码
        webSetting.setDefaultTextEncodingName("UTF-8");
		/*
		 * LayoutAlgorithm是一个枚举用来控制页面的布局，有三个类型：
		 * 1.NARROW_COLUMNS：可能的话使所有列的宽度不超过屏幕宽度
		 * 2.NORMAL：正常显示不做任何渲染
		 * 3.SINGLE_COLUMN：把所有内容放大webview等宽的一列中
		 * 用SINGLE_COLUMN类型可以设置页面居中显示，
		 * 页面可以放大缩小，但这种方法不怎么好，
		 * 有时候会让你的页面布局走样而且我测了一下，只能显示中间那一块，超出屏幕的部分都不能显示。
		 */
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setSupportZoom(false);// 用于设置webview放大
        webSetting.setBuiltInZoomControls(false);

        Network.getInstance().getAboutUs(new IAsyncLoadListener<AboutUsEntity>() {
            @Override
            public void onSuccess(AboutUsEntity aboutUsEntity) {
                showShortToast(aboutUsEntity.getMsg());
                if (aboutUsEntity.getCode()== 0){
                    mWebView.loadDataWithBaseURL(
                            null,
                            aboutUsEntity.getData(),
                            "text/html",
                            "utf-8",
                            null);
                }
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
            }
        });
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
}
