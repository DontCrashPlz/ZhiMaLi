package com.zhimali.zheng.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhimali.zheng.R;
import com.zhimali.zheng.bean.FansEntity;
import com.zhimali.zheng.bean.NoticeEntity;
import com.zhimali.zheng.module_mine.NoticeDetailActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zheng on 2018/5/21.
 */

public class NoticeListAdapter extends BaseQuickAdapter<NoticeEntity, NoticeListAdapter.NoticeListHolder> {

    public NoticeListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(NoticeListHolder helper, final NoticeEntity item) {
        helper.mPanelRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, NoticeDetailActivity.class);
                intent.putExtra("id", item.getId());
                mContext.startActivity(intent);
            }
        });
        helper.mTitleTv.setText(item.getTitle());
    }

    class NoticeListHolder extends BaseViewHolder {

        private RelativeLayout mPanelRly;
        private TextView mTitleTv;

        public NoticeListHolder(View view) {
            super(view);
            mPanelRly= view.findViewById(R.id.item_notice_panel);
            mTitleTv= view.findViewById(R.id.item_notice_title);
        }

    }
}
