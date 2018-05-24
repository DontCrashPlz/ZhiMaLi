package com.zhimali.zheng.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhimali.zheng.R;
import com.zhimali.zheng.apps.PosterActivity;
import com.zhimali.zheng.bean.NewsListEntity;
import com.zhimali.zheng.bean.PosterEntity;
import com.zhimali.zheng.module_home_page.NewsDetailActivity;

/**
 * Created by Zheng on 2018/4/26.
 */

public class PosterListAdapter extends BaseQuickAdapter<PosterEntity, PosterListAdapter.PosterListHolder> {

    public PosterListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(PosterListHolder helper, final PosterEntity item) {
        helper.mPosterPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, PosterActivity.class);
                intent.putExtra("poster_url", item.getUrl());
                mContext.startActivity(intent);
            }
        });
        helper.mPosterTitle.setText(item.getTitle());
        Glide.with(mContext)
                .load(item.getImg())
                .asBitmap()
                .placeholder(R.drawable.zhanwei)
                .error(R.drawable.zhanwei)
                .into(helper.mPosterPic);
    }

    class PosterListHolder extends BaseViewHolder {

        private RelativeLayout mPosterPanel;
        private TextView mPosterTitle;
        private ImageView mPosterPic;

        public PosterListHolder(View view) {
            super(view);
            mPosterPanel= view.findViewById(R.id.poster_rly_panel);
            mPosterTitle= view.findViewById(R.id.poster_tv_title);
            mPosterPic= view.findViewById(R.id.poster_iv_pic);
        }

    }

}
