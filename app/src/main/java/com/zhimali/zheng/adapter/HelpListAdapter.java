package com.zhimali.zheng.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhimali.zheng.R;
import com.zhimali.zheng.bean.HelpEntity;
import com.zhimali.zheng.bean.NoticeEntity;
import com.zhimali.zheng.module_mine.HelpDetailActivity;
import com.zhimali.zheng.module_mine.NoticeDetailActivity;

/**
 * Created by Zheng on 2018/5/21.
 */

public class HelpListAdapter extends BaseQuickAdapter<HelpEntity, HelpListAdapter.HelpListHolder> {

    public HelpListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(HelpListHolder helper, final HelpEntity item) {
        helper.mPanelRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, HelpDetailActivity.class);
                intent.putExtra("id", item.getId());
                mContext.startActivity(intent);
            }
        });
        helper.mTitleTv.setText(item.getTitle());
    }

    class HelpListHolder extends BaseViewHolder {

        private RelativeLayout mPanelRly;
        private TextView mTitleTv;

        public HelpListHolder(View view) {
            super(view);
            mPanelRly= view.findViewById(R.id.item_notice_panel);
            mTitleTv= view.findViewById(R.id.item_notice_title);
        }
    }
}
