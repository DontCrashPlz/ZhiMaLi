package com.zhimali.zheng.module_video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zheng.zchlibrary.apps.BaseFragment;
import com.zhimali.zheng.R;
import com.zhimali.zheng.adapter.VedioListAdapter;
import com.zhimali.zheng.bean.NewsListEntity;
import com.zhimali.zheng.http.HttpUtils;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by Zheng on 2018/4/19.
 */

public class HomeVideoFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    public static HomeVideoFragment newInstance(String content){
        HomeVideoFragment instance = new HomeVideoFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    private TextView mSearchTv;
    private RecyclerView mRecycler;
    private VedioListAdapter mAdapter;

    private int mCurrentPage= 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_video, container, false);

        initUI(mView);

        return mView;
    }

    private void initUI(View mView) {
        mSearchTv= mView.findViewById(R.id.toolbar_search);
        mSearchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getRealContext(), VedioSearchActivity.class));
            }
        });
        mRecycler= mView.findViewById(R.id.video_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mAdapter= new VedioListAdapter(R.layout.item_vedio_list);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mRecycler.setAdapter(mAdapter);

        requestNetData();

    }

    private void requestNetData(){
//        if (mCurrentPage< 1 ) {
//            showShortToast("无效网络请求");
//            return;
//        }

        addNetWork(
                Network.getInstance().getNewsList(String.valueOf(17), String.valueOf(mCurrentPage), null)
                        .compose(ResponseTransformer.changeThread())
                        .compose(ResponseTransformer.handleResult())
                        .subscribe(new Consumer<ArrayList<NewsListEntity>>() {
                            @Override
                            public void accept(ArrayList<NewsListEntity> newsListEntities) throws Exception {
                                dismissProgressBar();
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
