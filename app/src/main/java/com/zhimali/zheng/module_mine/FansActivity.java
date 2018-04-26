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
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zhimali.zheng.R;
import com.zhimali.zheng.adapter.FansListAdapter;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.bean.FansResponseEntity;
import com.zhimali.zheng.http.Network;

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

        getFansList(mCurrentPage);
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

    private void getFansList(int page){
        if (page< 1) return;
        Network.getInstance().getFans(
                MyApplication.appToken,
                20,
                page,
                "",
                new IAsyncLoadListener<FansResponseEntity>() {
                    @Override
                    public void onSuccess(FansResponseEntity fansResponseEntity) {
                        showShortToast(fansResponseEntity.getMsg());
                        if (fansResponseEntity.getCode()== 0){
                            if (fansResponseEntity.getData().size()> 0){
                                mAdapter.addData(fansResponseEntity.getData());
                            }
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        showShortToast(msg);
                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
        mCurrentPage+= 1;
        getFansList(mCurrentPage);
    }
}
