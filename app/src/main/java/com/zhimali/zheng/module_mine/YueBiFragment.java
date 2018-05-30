package com.zhimali.zheng.module_mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zheng.zchlibrary.apps.BaseFragment;
import com.zhimali.zheng.R;
import com.zhimali.zheng.adapter.YueBiListAdapter;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.bean.YueBiEntity;
import com.zhimali.zheng.http.HttpUtils;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by Zheng on 2018/4/20.
 */

public class YueBiFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    public static final int YUEBI_MY= 0;
    public static final int YUEBI_FANS= 1;
    public static final int YUEBI_TIXIAN= 2;

    public static YueBiFragment newInstance(int pageTag){
        YueBiFragment instance = new YueBiFragment();
        Bundle args = new Bundle();
        args.putInt("page_tag", pageTag);
        instance.setArguments(args);
        return instance;
    }

    private RecyclerView mRecycler;
    private YueBiListAdapter mAdapter;
    private int mFragmentTag;
    private int mCurrentPage= 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.layout_recycler_only, container, false);
        mRecycler= mView.findViewById(R.id.recyclerview);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));

        mFragmentTag= getArguments().getInt("page_tag");
        if (mFragmentTag== 0){
            mAdapter= new YueBiListAdapter(R.layout.item_yuebi_my);
            mRecycler.setAdapter(mAdapter);
        }else if (mFragmentTag== 1){
            mAdapter= new YueBiListAdapter(R.layout.item_yuebi_fans);
            mRecycler.setAdapter(mAdapter);
        }else if (mFragmentTag== 2){
            mAdapter= new YueBiListAdapter(R.layout.item_yuebi_tixian);
            mRecycler.setAdapter(mAdapter);
        }
        mAdapter.setOnLoadMoreListener(this, mRecycler);

        requestNetData();

        return mView;
    }

    private void requestNetData(){

        addNetWork(Network.getInstance()
                .getYueBiHistory(MyApplication.appToken,
                        String.valueOf(mFragmentTag),
                        String.valueOf(20),
                        String.valueOf(mCurrentPage))
                .compose(ResponseTransformer.changeThread())
                .compose(ResponseTransformer.handleResult())
                .subscribe(new Consumer<ArrayList<YueBiEntity>>() {
                    @Override
                    public void accept(ArrayList<YueBiEntity> yueBiEntities) throws Exception {
                        dismissProgressBar();
                        if (yueBiEntities.size()> 0){
                            mAdapter.addData(yueBiEntities);
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
