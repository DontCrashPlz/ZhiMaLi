package com.zhimali.zheng.module_home_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.widgets.CustomTabLayout.Tool;
import com.zheng.zchlibrary.widgets.LazyLoadFragment;
import com.zhimali.zheng.R;
import com.zhimali.zheng.adapter.NewsListAdapter;
import com.zhimali.zheng.bean.NewsListResponseEntity;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.widgets.MyNewsListItemDecoration;

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

    @Override
    public void lazyLoad() {
        requestNetData(mCurrentPage);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        catid= getArguments().getString("cateId");

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
        return mView;
    }

    private void requestNetData(int page){
        if (page< 1) return;

        Network.getInstance().getNewsList(
                catid,
                String.valueOf(page),
                null,
                new IAsyncLoadListener<NewsListResponseEntity>() {
                    @Override
                    public void onSuccess(NewsListResponseEntity newsListResponseEntity) {
                        showShortToast(newsListResponseEntity.getMsg());
                        if (newsListResponseEntity.getCode()== 0){
                            if (newsListResponseEntity.getData().size()> 0){
                                mAdapter.addData(newsListResponseEntity.getData());
                            }
                        }
                        mAdapter.loadMoreComplete();
                    }

                    @Override
                    public void onFailure(String msg) {
                        showShortToast(msg);
                        mAdapter.loadMoreComplete();
                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
        mCurrentPage+= 1;
        requestNetData(mCurrentPage);
    }
}
