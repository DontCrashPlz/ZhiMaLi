package com.zhimali.zheng.module_home_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.CustomTabLayout.Tool;
import com.zheng.zchlibrary.widgets.LazyLoadFragment;
import com.zhimali.zheng.R;
import com.zhimali.zheng.adapter.NewsListAdapter;
import com.zhimali.zheng.bean.NewsListEntity;
import com.zhimali.zheng.http.HttpUtils;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;
import com.zhimali.zheng.widgets.MyNewsListItemDecoration;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by Zheng on 2018/4/27.
 */

public class HomeCategoryFragment extends LazyLoadFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    public static HomeCategoryFragment newInstance(String cateId){
        HomeCategoryFragment instance = new HomeCategoryFragment();
        Bundle args = new Bundle();
        args.putString("cateId", cateId);
        instance.setArguments(args);
        return instance;
    }

    private String catid;
    private int mCurrentPage= 1;

    private RecyclerView mRecycler;
    private NewsListAdapter mAdapter;

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

        mRecycler= mView.findViewById(R.id.recyclerview);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mRecycler.addItemDecoration(new MyNewsListItemDecoration(Tool.dp2px(getRealContext(), 15)));
        mAdapter= new NewsListAdapter(R.layout.item_news_list);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
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
                                        mAdapter.setEmptyView(R.layout.layout_search_empty);
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
}
