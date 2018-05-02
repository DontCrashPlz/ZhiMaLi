package com.zhimali.zheng.module_home_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zheng.zchlibrary.apps.BaseActivity;
import com.zhimali.zheng.R;
import com.zhy.view.flowlayout.TagFlowLayout;

/**
 * Created by Zheng on 2018/4/28.
 */

public class SearchActivity extends BaseActivity {

    private TextView mCancelTv;
    private EditText mKeywordEt;
    private ImageButton mCleanHistoryIbtn;
    private TagFlowLayout mTagflowlayout;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initUI();
    }

    private void initUI() {
        mCancelTv= findViewById(R.id.toolbar_back);
        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str= mCancelTv.getText().toString();
                if ("搜索".equals(str)){

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

            }
        });
        mTagflowlayout= findViewById(R.id.search_tagflow);
        mRecycler= findViewById(R.id.search_recycler);
    }

}
