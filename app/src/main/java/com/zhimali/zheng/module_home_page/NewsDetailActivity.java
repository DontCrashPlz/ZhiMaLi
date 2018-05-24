package com.zhimali.zheng.module_home_page;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;
import com.zhimali.zheng.R;
import com.zhimali.zheng.adapter.PosterListAdapter;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.bean.HttpResult;
import com.zhimali.zheng.bean.NewsDetailEntity;
import com.zhimali.zheng.bean.PosterEntity;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;
import com.zhimali.zheng.widgets.MyNewsListItemDecoration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/5/3.
 */

public class NewsDetailActivity extends BaseActivity {

    private String id;

    private int viewId;

    private ImageView mBackIv;
    private ImageView mShareIv;
    private TextView mNewsTitleTv;
    private WebView mWebView;
    private RecyclerView mRecycler;
    private PosterListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
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
        mNewsTitleTv= findViewById(R.id.news_detail_title);
        mWebView= findViewById(R.id.news_detail_webview);
        mRecycler= findViewById(R.id.news_detail_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mAdapter= new PosterListAdapter(R.layout.item_poster_list);
        mRecycler.setAdapter(mAdapter);
        mRecycler.addItemDecoration(new MyNewsListItemDecoration(15));

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

        if (MyApplication.getInstance().isHadUser()){//如果用户登录，使用appToken加载新闻详情，并轮询计费接口
            //todo 请求新闻详情
            addNetWork(Network.getInstance()
                    .getNewsDetail(MyApplication.appToken, id)
                    .compose(ResponseTransformer.changeThread())
                    .compose(ResponseTransformer.handleResult())
                    .subscribe(new Consumer<NewsDetailEntity>() {
                        @Override
                        public void accept(NewsDetailEntity newsDetailEntity) throws Exception {
                            dismissProgressDialog();
                            mNewsTitleTv.setText(newsDetailEntity.getTitle());
                            mWebView.loadDataWithBaseURL(null, newsDetailEntity.getContent(), "text/html", "utf-8", null);
                            viewId= newsDetailEntity.getView_id();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            showShortToast(throwable.toString());
                            dismissProgressDialog();
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            dismissProgressDialog();
                        }
                    }, new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showProgressDialog();
                        }
                    }));
            //todo 请求计费接口
            addNetWork(Observable.interval(5, 5, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .flatMap(new Function<Long, ObservableSource<HttpResult<String>>>() {
                        @Override
                        public ObservableSource<HttpResult<String>> apply(Long aLong) throws Exception {
                            return Network.getInstance()
                                    .doCharge(MyApplication.appToken, String.valueOf(viewId));
                        }
                    })
                    .compose(ResponseTransformer.<String>handleResult())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            LogUtil.d("计费成功: ", s);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            LogUtil.d("计费失败: ", throwable.toString());
                        }
                    }));
        }else{//如果没有用户登录，使用空appToken加载新闻详情，不轮询计费接口
            addNetWork(Network.getInstance()
                    .getNewsDetail("", id)
                    .compose(ResponseTransformer.changeThread())
                    .compose(ResponseTransformer.handleResult())
                    .subscribe(new Consumer<NewsDetailEntity>() {
                        @Override
                        public void accept(NewsDetailEntity newsDetailEntity) throws Exception {
                            dismissProgressDialog();
                            mNewsTitleTv.setText(newsDetailEntity.getTitle());
                            mWebView.loadDataWithBaseURL(null, newsDetailEntity.getContent(), "text/html", "utf-8", null);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            showShortToast(throwable.toString());
                            dismissProgressDialog();
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            dismissProgressDialog();
                        }
                    }, new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showProgressDialog();
                        }
                    }));
        }

        addNetWork(Network.getInstance().getPosterList("12")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<ArrayList<PosterEntity>>handleResult())
                .subscribe(new Consumer<ArrayList<PosterEntity>>() {
                    @Override
                    public void accept(ArrayList<PosterEntity> posterEntities) throws Exception {
                        if (posterEntities!= null && posterEntities.size()> 0){
                            mAdapter.addData(posterEntities);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showShortToast(throwable.toString());
                    }
                }));

    }

    @Override
    public void initProgress() {
        mProgressDialog= new ProgressDialog(getRealContext());
        mProgressDialog.setLabel("正在加载新闻..");
    }
}
