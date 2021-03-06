package com.zhimali.zheng.module_mine;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;
import com.zhimali.zheng.R;
import com.zhimali.zheng.bean.HelpDetailEntity;
import com.zhimali.zheng.bean.NoticeDetailEntity;
import com.zhimali.zheng.http.HttpUtils;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by Zheng on 2018/4/19.
 */

public class HelpDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;

    private WebView mWebView;

    private String mHelpId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        mHelpId= getIntent().getStringExtra("id");
        if (mHelpId== null || mHelpId.length()< 1){
            showShortToast("无效的公告");
            finish();
            return;
        }

        initUI();
    }

    @Override
    public void initProgress() {
        mProgressDialog= new ProgressDialog(getRealContext());
        mProgressDialog.setLabel("正在加载..");
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
        mTitleTv.setVisibility(View.INVISIBLE);
        mFuncationTv= findViewById(R.id.toolbar_funcation);
        mFuncationTv.setVisibility(View.GONE);

        mWebView= findViewById(R.id.notice_detail_webview);
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

        addNetWork(Network.getInstance()
                .getHelpDetail(mHelpId)
                .compose(ResponseTransformer.changeThread())
                .compose(ResponseTransformer.handleResult())
                .subscribe(new Consumer<HelpDetailEntity>() {
                    @Override
                    public void accept(HelpDetailEntity helpDetailEntity) throws Exception {
                        dismissProgressDialog();
                        mWebView.loadDataWithBaseURL(null, helpDetailEntity.getContent(), "text/html", "utf-8", null);
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
