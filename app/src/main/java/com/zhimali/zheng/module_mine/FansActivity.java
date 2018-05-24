package com.zhimali.zheng.module_mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zhimali.zheng.R;
import com.zhimali.zheng.adapter.FansListAdapter;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.bean.FansEntity;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by Zheng on 2018/4/19.
 */

public class FansActivity extends BaseActivity implements
        View.OnClickListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;

    private RecyclerView mRecycler;
    private FansListAdapter mAdapter;

    private int mCurrentPage= 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);

        initUI();
    }

    @Override
    public void initProgress() {
        mProgressBar= findViewById(R.id.progressBar);
    }

    private void initUI() {
        mBackBtn= findViewById(R.id.toolbar_back);
        mBackBtn.setOnClickListener(this);
        mTitleTv= findViewById(R.id.toolbar_text);
        mTitleTv.setText("粉丝");
        mFuncationTv= findViewById(R.id.toolbar_funcation);
        mFuncationTv.setVisibility(View.GONE);

        mRecycler= findViewById(R.id.fans_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter= new FansListAdapter(R.layout.item_fans);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mRecycler.setAdapter(mAdapter);

        getFansList();
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

    private void getFansList(){

        addNetWork(Network.getInstance().getFans(MyApplication.appToken, 20, mCurrentPage, "")
                .compose(ResponseTransformer.changeThread())
                .compose(ResponseTransformer.handleResult())
                .subscribe(new Consumer<ArrayList<FansEntity>>() {
                    @Override
                    public void accept(ArrayList<FansEntity> fansEntities) throws Exception {
                        dismissProgressBar();
                        if (fansEntities.size()> 0){
                            mAdapter.addData(fansEntities);
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
                        showShortToast(throwable.toString());
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
        getFansList();
    }
}
