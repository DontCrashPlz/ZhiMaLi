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
import com.zhimali.zheng.bean.NewsListEntity;
import com.zhimali.zheng.module_video.VedioDetailActivity;

/**
 * Created by Zheng on 2018/5/3.
 */

public class VedioListAdapter extends BaseQuickAdapter<NewsListEntity,VedioListAdapter.VedioViewHolder> {

    public VedioListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(VedioViewHolder helper, final NewsListEntity item) {
        helper.mVedioListTitle.setText(item.getTitle());
        Glide.with(mContext)
                .load(item.getPics().get(0))
                .asBitmap()
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .into(helper.mVedioListPic);
        helper.mVedioListTime.setText(item.getFormat_date());
        helper.mVedioListRead.setText(item.getView_num());
        helper.mVedioListYb.setText(item.getCoin());

        helper.mVedioListPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, VedioDetailActivity.class);
                intent.putExtra("id", item.getId());
                mContext.startActivity(intent);
            }
        });
    }

    class VedioViewHolder extends BaseViewHolder{

        private RelativeLayout mVedioListPanel;
        private TextView mVedioListTitle;
        private ImageView mVedioListPic;
        private TextView mVedioListTime;
        private TextView mVedioListRead;
        private TextView mVedioListYb;

        public VedioViewHolder(View view) {
            super(view);
            mVedioListPanel= view.findViewById(R.id.vedio_list_panel);
            mVedioListTitle= view.findViewById(R.id.vedio_list_title);
            mVedioListPic= view.findViewById(R.id.vedio_list_pic);
            mVedioListTime= view.findViewById(R.id.vedio_list_time);
            mVedioListRead= view.findViewById(R.id.vedio_list_read);
            mVedioListYb= view.findViewById(R.id.vedio_list_yb);
        }
    }
}
