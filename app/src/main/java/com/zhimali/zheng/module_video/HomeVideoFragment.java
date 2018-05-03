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
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zhimali.zheng.R;
import com.zhimali.zheng.adapter.VedioListAdapter;
import com.zhimali.zheng.bean.NewsListResponseEntity;
import com.zhimali.zheng.http.Network;

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

        requestNetData(mCurrentPage);

    }

    private void requestNetData(final int page){
        if (page< 1 ) {
            showShortToast("无效网络请求");
            return;
        }

        Network.getInstance().getNewsList(
                String.valueOf(17),
                String.valueOf(page),
                null,
                new IAsyncLoadListener<NewsListResponseEntity>() {
                    @Override
                    public void onSuccess(NewsListResponseEntity newsListResponseEntity) {
                        showShortToast(newsListResponseEntity.getMsg());
                        if (newsListResponseEntity.getCode()== 0){
                            if (newsListResponseEntity.getData().size()> 0){
                                mAdapter.addData(newsListResponseEntity.getData());
                                mAdapter.loadMoreComplete();
                            }else {
                                mAdapter.loadMoreComplete();
                                mAdapter.loadMoreEnd();
                                if (page== 1){
                                    mAdapter.setEmptyView(R.layout.layout_search_empty);
                                }
                            }
                        }else {
                            mAdapter.loadMoreFail();
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        showShortToast(msg);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
        mCurrentPage+= 1;
        requestNetData(mCurrentPage);
    }
}
