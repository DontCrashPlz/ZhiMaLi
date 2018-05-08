package com.zhimali.zheng.module_home_page;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zhimali.zheng.R;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.bean.NewsDetailResponseEntity;
import com.zhimali.zheng.http.Network;

/**
 * Created by Zheng on 2018/5/3.
 */

public class NewsDetailActivity extends BaseActivity {

    private String id;

    private ImageView mBackIv;
    private ImageView mShareIv;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_detail);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        id= getIntent().getStringExtra("id");
        if (id== null || id.length()< 1){
            showShortToast("内容id无效");
            finish();
            return;
        }
        mBackIv= findViewById(R.id.toolbar_back);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mShareIv= findViewById(R.id.toolbar_share);
        mShareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShortToast("分享");
            }
        });
        mWebView= findViewById(R.id.vedio_detail_webview);
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
//        webSetting.setAllowFileAccess(true);
//        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSetting.setSupportZoom(true);
//        webSetting.setBuiltInZoomControls(true);
//        webSetting.setUseWideViewPort(true);
//        webSetting.setSupportMultipleWindows(false);
//        // webSetting.setLoadWithOverviewMode(true);
//        webSetting.setAppCacheEnabled(true);
//        // webSetting.setDatabaseEnabled(true);
//        webSetting.setDomStorageEnabled(true);
//        webSetting.setJavaScriptEnabled(true);
//        webSetting.setGeolocationEnabled(true);
//        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
//        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
//        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
//        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
//                .getPath());
//        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
//        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);

        String mToken= "";
        if (MyApplication.appToken != null && MyApplication.appToken.length()> 0){
            mToken= MyApplication.appToken;
        }
        Network.getInstance().getNewsDetail(mToken, id, new IAsyncLoadListener<NewsDetailResponseEntity>() {
            @Override
            public void onSuccess(NewsDetailResponseEntity newsDetailResponseEntity) {
                showShortToast(newsDetailResponseEntity.getMsg());
                if (newsDetailResponseEntity.getCode()== 0){
                    mWebView.loadDataWithBaseURL(null, newsDetailResponseEntity.getData().getContent(), "text/html", "utf-8", null);
//                    mWebView.loadData(newsDetailResponseEntity.getData().getContent(), "text/html", "uft-8");
                }
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
            }
        });
    }
}