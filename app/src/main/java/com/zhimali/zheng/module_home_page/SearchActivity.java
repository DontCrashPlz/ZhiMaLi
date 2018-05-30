package com.zhimali.zheng.module_home_page;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.CustomTabLayout.Tool;
import com.zhimali.zheng.R;
import com.zhimali.zheng.adapter.SearchListAdapter;
import com.zhimali.zheng.bean.NewsListEntity;
import com.zhimali.zheng.dao.DaoMaster;
import com.zhimali.zheng.dao.DaoSession;
import com.zhimali.zheng.dao.HistoryData;
import com.zhimali.zheng.db.GreenDaoHelper;
import com.zhimali.zheng.http.HttpUtils;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;
import com.zhimali.zheng.widgets.MyNewsListItemDecoration;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by Zheng on 2018/4/28.
 */

public class SearchActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    private String catid;
    private int mCurrentPage= 1;
    private String mKeyword;

    private RelativeLayout mHistoryPanel;
    private TextView mCancelTv;
    private EditText mKeywordEt;
    private ImageButton mCleanHistoryIbtn;
    private TagFlowLayout mTagflowlayout;
    private TagAdapter<HistoryData> tagAdapter;
    private RecyclerView mRecycler;
    private SearchListAdapter mAdapter;

    private GreenDaoHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        catid= getIntent().getStringExtra("catid");
        if (catid== null || catid.length()<1 ){
            showShortToast("频道加载错误");
            finish();
            return;
        }
        LogUtil.d("catid", catid);

        initDao();

        initUI();
    }

    @Override
    public void initProgress() {
        mProgressBar= findViewById(R.id.progressBar);
    }

    private void initDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "zhimali.db");
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession mDaoSession = daoMaster.newSession();
        mHelper= new GreenDaoHelper(mDaoSession);
    }

    private void initUI() {
        mHistoryPanel= findViewById(R.id.search_history_panel);

        mCancelTv= findViewById(R.id.toolbar_back);
        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str= mCancelTv.getText().toString().trim();
                if ("搜索".equals(str)){
                    String keyword= mKeywordEt.getText().toString().trim();
                    if (keyword== null || keyword.length()< 1){
                        showShortToast("无效关键字");
                        return;
                    }
                    mAdapter.setNewData(new ArrayList<NewsListEntity>());
                    mCurrentPage= 1;
                    mKeyword= keyword;

                    mHelper.addHistoryData(keyword);
                    requestNetData(mCurrentPage, keyword);

                    mHistoryPanel.setVisibility(View.GONE);
                    mRecycler.setVisibility(View.VISIBLE);
                }else if ("取消".equals(str)){
                    finish();
                }else {
                    showShortToast("页面出现错误");
                    finish();
                }
            }
        });
        mKeywordEt= findViewById(R.id.toolbar_search);
        mKeywordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str= s.toString();
                if (str!= null && str.length()> 0){
                    mCancelTv.setText("搜索");
                }else {
                    mCancelTv.setText("取消");
                }
            }
        });
        mCleanHistoryIbtn= findViewById(R.id.search_delete_history);
        mCleanHistoryIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.clearHistoryData();
                refreshTagFlowLayout();
            }
        });
        mTagflowlayout= findViewById(R.id.search_tagflow);
        refreshTagFlowLayout();
        mRecycler= findViewById(R.id.search_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mAdapter= new SearchListAdapter(R.layout.item_news_search_list);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mRecycler.setAdapter(mAdapter);
        mRecycler.addItemDecoration(new MyNewsListItemDecoration(Tool.dp2px(getRealContext(), 15)));
    }

    private void requestNetData(final int page, String keyword){
        if (page< 1 || keyword== null || keyword.length()<1) {
            showShortToast("无效网络请求");
            return;
        }

        addNetWork(Network.getInstance()
                .getNewsList(catid, String.valueOf(page), keyword)
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
                            if (page== 1){
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
        requestNetData(mCurrentPage, mKeyword);
    }

    private void refreshTagFlowLayout(){
        final List<HistoryData> historyDatas= mHelper.loadAllHistoryData();
        tagAdapter= new TagAdapter<HistoryData>(historyDatas) {
            @Override
            public View getView(FlowLayout parent, int position, HistoryData historyData) {
                TextView textView= (TextView) LayoutInflater
                        .from(getRealContext())
                        .inflate(R.layout.item_flow_tag, parent, false);
                textView.setText(historyData.getData());
                return textView;
            }
        };
        mTagflowlayout.setAdapter(tagAdapter);
        mTagflowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                TextView textView= view.findViewById(R.id.commonItemTitle);
                String str= textView.getText().toString().trim();
                if (str== null || str.length()<1){
                    showShortToast("标签出错");
                    return false;
                }
                mCurrentPage= 1;
                mKeyword= str;
                mKeywordEt.setText(mKeyword);

                mHelper.addHistoryData(mKeyword);
                requestNetData(mCurrentPage, mKeyword);

                mHistoryPanel.setVisibility(View.GONE);
                mRecycler.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }

}
