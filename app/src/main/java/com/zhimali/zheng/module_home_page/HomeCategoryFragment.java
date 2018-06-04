package com.zhimali.zheng.module_home_page;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.CustomTabLayout.Tool;
import com.zheng.zchlibrary.widgets.LazyLoadFragment;
import com.zhimali.zheng.R;
import com.zhimali.zheng.adapter.NewsListAdapter;
import com.zhimali.zheng.apps.GlideImageLoader;
import com.zhimali.zheng.apps.PosterActivity;
import com.zhimali.zheng.bean.NewsListEntity;
import com.zhimali.zheng.bean.PosterEntity;
import com.zhimali.zheng.http.HttpUtils;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;
import com.zhimali.zheng.widgets.MyNewsListItemDecoration;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/27.
 */

public class HomeCategoryFragment extends LazyLoadFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    public static HomeCategoryFragment newInstance(String cateId){
        HomeCategoryFragment instance = new HomeCategoryFragment();
        Bundle args = new Bundle();
        args.putString("cateId", cateId);
        instance.setArguments(args);
        return instance;
    }

    private String catid;
    private int mCurrentPage= 1;

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecycler;
    private NewsListAdapter mAdapter;

    private Banner mRecommendBanner;

    private boolean isPrepared;
    private boolean isLoadedOnce;

    @Override
    public void lazyLoad() {
        if (isPrepared && !isLoadedOnce && isVisible){
            requestNetData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        catid= getArguments().getString("cateId");
        LogUtil.d("catid", catid);

        if (catid== null || catid.length()< 1){
            showShortToast("频道id获取失败");
            return null;
        }

        View mView= inflater.inflate(R.layout.fragment_home_category, container, false);

        mRefreshLayout= mView.findViewById(R.id.swiperefresh);
        mRefreshLayout.setColorSchemeColors(Color.rgb(62,144,253));
        mRefreshLayout.setOnRefreshListener(this);
        mRecycler= mView.findViewById(R.id.recyclerview);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mRecycler.addItemDecoration(new MyNewsListItemDecoration(Tool.dp2px(getRealContext(), 15)));
        mAdapter= new NewsListAdapter(R.layout.item_news_list);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        if ("6".equals(catid)){
            LinearLayout mHeaderGroup=
                    ((LinearLayout) LayoutInflater
                            .from(getRealContext())
                            .inflate(R.layout.layout_banner, null));
            mRecommendBanner = mHeaderGroup.findViewById(R.id.head_banner);
            mHeaderGroup.removeView(mRecommendBanner);
            mAdapter.addHeaderView(mRecommendBanner);
        }
        mRecycler.setAdapter(mAdapter);

        isPrepared= true;

        lazyLoad();

        return mView;
    }

    private void requestNetData(){

        addNetWork(
                Network.getInstance().getNewsList(catid, String.valueOf(mCurrentPage), null)
                        .compose(ResponseTransformer.changeThread())
                        .compose(ResponseTransformer.handleResult())
                        .subscribe(new Consumer<ArrayList<NewsListEntity>>() {
                            @Override
                            public void accept(ArrayList<NewsListEntity> newsListEntities) throws Exception {
                                dismissProgressBar();
                                if (!isLoadedOnce){
                                    isLoadedOnce= true;
                                }
                                if (newsListEntities.size()> 0){
                                    mAdapter.addData(newsListEntities);
                                    mAdapter.loadMoreComplete();
                                }else {
                                    mAdapter.loadMoreFail();
                                    mAdapter.loadMoreEnd();
                                    if (mCurrentPage== 1){
                                        mAdapter.setEmptyView(R.layout.layout_recycler_empty);
                                    }
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dismissProgressBar();
                                mAdapter.loadMoreFail();
                                showShortToast(HttpUtils.parseThrowableMsg(throwable));
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                dismissProgressBar();
                            }
                        }, new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (mCurrentPage== 1) showProgressBar();
                            }
                        }));

        if ("6".equals(catid) && mCurrentPage== 1){
            addNetWork(Network.getInstance().getPosterList("12")
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(ResponseTransformer.<ArrayList<PosterEntity>>handleResult())
                    .subscribe(new Consumer<ArrayList<PosterEntity>>() {
                        @Override
                        public void accept(final ArrayList<PosterEntity> posterEntities) throws Exception {
                            if (posterEntities!= null && posterEntities.size()> 0){
                                ArrayList<String> imageList=new ArrayList<>();
                                final ArrayList<String> urlList=new ArrayList<>();
                                for (PosterEntity entity : posterEntities){
                                    imageList.add(entity.getImg());
                                    urlList.add(entity.getUrl());
                                }
                                mRecommendBanner.setImageLoader(new GlideImageLoader());
                                mRecommendBanner.setImages(imageList);
                                mRecommendBanner.setOnBannerListener(new OnBannerListener() {
                                    @Override
                                    public void OnBannerClick(int position) {
                                        Intent intent= new Intent(getRealContext(), PosterActivity.class);
                                        intent.putExtra("poster_url", urlList.get(position));
                                        startActivity(intent);
                                    }
                                });
                                //设置banner样式
                                mRecommendBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                                //设置指示器位置（当banner模式中有指示器时）
                                mRecommendBanner.setIndicatorGravity(BannerConfig.LEFT);
                                mRecommendBanner.setDelayTime(2000);
                                mRecommendBanner.start();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            LogUtil.d("广告列表加载失败: ", throwable.toString());
                        }
                    }));
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mCurrentPage+= 1;
        requestNetData();
    }

    @Override
    public void initProgressDialog() {

    }

    @Override
    public void initProgressBar(View view) {
        mProgressBar= view.findViewById(R.id.progressBar);
    }

    @Override
    public void onRefresh() {
        mCurrentPage= 1;
        addNetWork(
                Network.getInstance().getNewsList(catid, String.valueOf(mCurrentPage), null)
                        .compose(ResponseTransformer.changeThread())
                        .compose(ResponseTransformer.handleResult())
                        .subscribe(new Consumer<ArrayList<NewsListEntity>>() {
                            @Override
                            public void accept(ArrayList<NewsListEntity> newsListEntities) throws Exception {
                                if (!isLoadedOnce){
                                    isLoadedOnce= true;
                                }
                                if (newsListEntities.size()> 0){
                                    mAdapter.setNewData(newsListEntities);
                                    mRefreshLayout.setRefreshing(false);
                                }else {
                                    mRefreshLayout.setRefreshing(false);
                                    if (mCurrentPage== 1){
                                        mAdapter.setEmptyView(R.layout.layout_recycler_empty);
                                    }
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mRefreshLayout.setRefreshing(false);
                                showShortToast(HttpUtils.parseThrowableMsg(throwable));
                            }
                        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRecommendBanner != null) {
            mRecommendBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mRecommendBanner != null) {
            mRecommendBanner.stopAutoPlay();
        }
    }

}
