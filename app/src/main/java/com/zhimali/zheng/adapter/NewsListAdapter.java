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
import com.zhimali.zheng.module_home_page.NewsDetailActivity;
import com.zhimali.zheng.module_video.VedioDetailActivity;

/**
 * Created by Zheng on 2018/4/26.
 */

public class NewsListAdapter extends BaseQuickAdapter<NewsListEntity, NewsListAdapter.NewsListHolder> {

    public NewsListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(NewsListHolder helper, NewsListEntity item) {
        if (item.getUrl()!= null && item.getUrl().length()> 0){
            dealType4(helper, item);
        }else {
            if (item.getPics().size()== 0){
                dealType3(helper, item);
            }else if (item.getPics().size()== 1){
                dealType2(helper, item);
            }else {
                dealType1(helper, item);
            }
        }
    }

    /**
     * 多图片新闻
     * @param helper
     * @param item
     */
    private void dealType1(NewsListHolder helper, final NewsListEntity item){
        helper.mType1.setVisibility(View.VISIBLE);
        helper.mType2.setVisibility(View.GONE);
        helper.mType3.setVisibility(View.GONE);
        helper.mType4.setVisibility(View.GONE);
        helper.mType1_title.setText(item.getTitle());
        if (item.getPics().size()> 0){
            Glide.with(mContext)
                    .load(item.getPics().get(0))
                    .asBitmap()
                    .placeholder(R.mipmap.zhanwei)
                    .error(R.mipmap.zhanwei)
                    .into(helper.mType1_pic1);
        }
        if (item.getPics().size()> 1){
            Glide.with(mContext)
                    .load(item.getPics().get(1))
                    .asBitmap()
                    .placeholder(R.mipmap.zhanwei)
                    .error(R.mipmap.zhanwei)
                    .into(helper.mType1_pic2);
        }
        if (item.getPics().size()> 2){
            Glide.with(mContext)
                    .load(item.getPics().get(2))
                    .asBitmap()
                    .placeholder(R.mipmap.zhanwei)
                    .error(R.mipmap.zhanwei)
                    .into(helper.mType1_pic3);
        }
        helper.mType1_time.setText(item.getFormat_date());
        helper.mType1_read.setText(item.getView_num());
        helper.mType1_yb.setText(item.getCoin());

        helper.mType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("id", item.getId());
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 大图片新闻
     * @param helper
     * @param item
     */
    private void dealType2(NewsListHolder helper, final NewsListEntity item){
        helper.mType1.setVisibility(View.GONE);
        helper.mType2.setVisibility(View.VISIBLE);
        helper.mType3.setVisibility(View.GONE);
        helper.mType4.setVisibility(View.GONE);
        helper.mType2_title.setText(item.getTitle());
        Glide.with(mContext)
                .load(item.getPics().get(0))
                .asBitmap()
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .into(helper.mType2_pic);
        helper.mType2_time.setText(item.getFormat_date());
        helper.mType2_read.setText(item.getView_num());
        helper.mType2_yb.setText(item.getCoin());

        helper.mType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("id", item.getId());
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 无图片新闻
     * @param helper
     * @param item
     */
    private void dealType3(NewsListHolder helper, final NewsListEntity item){
        helper.mType1.setVisibility(View.GONE);
        helper.mType2.setVisibility(View.GONE);
        helper.mType3.setVisibility(View.VISIBLE);
        helper.mType4.setVisibility(View.GONE);
        helper.mType3_title.setText(item.getTitle());
        helper.mType3_time.setText(item.getFormat_date());
        helper.mType3_read.setText(item.getView_num());
        helper.mType3_yb.setText(item.getCoin());

        helper.mType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("id", item.getId());
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 广告
     * @param helper
     * @param item
     */
    private void dealType4(NewsListHolder helper, final NewsListEntity item){
        helper.mType1.setVisibility(View.GONE);
        helper.mType2.setVisibility(View.GONE);
        helper.mType3.setVisibility(View.GONE);
        helper.mType4.setVisibility(View.VISIBLE);
        helper.mType4_title.setText(item.getTitle());
        if (item.getPics().size()> 0){
            Glide.with(mContext)
                    .load(item.getPics().get(0))
                    .asBitmap()
                    .placeholder(R.mipmap.zhanwei)
                    .error(R.mipmap.zhanwei)
                    .into(helper.mType4_pic1);
        }
        if (item.getPics().size()> 1){
            Glide.with(mContext)
                    .load(item.getPics().get(1))
                    .asBitmap()
                    .placeholder(R.mipmap.zhanwei)
                    .error(R.mipmap.zhanwei)
                    .into(helper.mType4_pic2);
        }
        if (item.getPics().size()> 2){
            Glide.with(mContext)
                    .load(item.getPics().get(2))
                    .asBitmap()
                    .placeholder(R.mipmap.zhanwei)
                    .error(R.mipmap.zhanwei)
                    .into(helper.mType4_pic3);
        }
        helper.mType4_mark.setText("");

        helper.mType4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("id", item.getId());
                mContext.startActivity(intent);
            }
        });
    }

    class NewsListHolder extends BaseViewHolder {

        private RelativeLayout mType1;
        private RelativeLayout mType2;
        private RelativeLayout mType3;
        private RelativeLayout mType4;

        private TextView mType1_title;
        private ImageView mType1_pic1;
        private ImageView mType1_pic2;
        private ImageView mType1_pic3;
        private TextView mType1_time;
        private TextView mType1_read;
        private TextView mType1_yb;

        private TextView mType2_title;
        private ImageView mType2_pic;
        private TextView mType2_time;
        private TextView mType2_read;
        private TextView mType2_yb;

        private TextView mType3_title;
        private TextView mType3_time;
        private TextView mType3_read;
        private TextView mType3_yb;

        private TextView mType4_title;
        private ImageView mType4_pic1;
        private ImageView mType4_pic2;
        private ImageView mType4_pic3;
        private TextView mType4_mark;

        public NewsListHolder(View view) {
            super(view);
            mType1= view.findViewById(R.id.news_type1);
            mType1_title= view.findViewById(R.id.news_type1_title);
            mType1_pic1= view.findViewById(R.id.news_type1_pic1);
            mType1_pic2= view.findViewById(R.id.news_type1_pic2);
            mType1_pic3= view.findViewById(R.id.news_type1_pic3);
            mType1_time= view.findViewById(R.id.news_type1_time);
            mType1_read= view.findViewById(R.id.news_type1_read);
            mType1_yb= view.findViewById(R.id.news_type1_yb);

            mType2= view.findViewById(R.id.news_type2);
            mType2_title= view.findViewById(R.id.news_type2_title);
            mType2_pic= view.findViewById(R.id.news_type2_pic);
            mType2_time= view.findViewById(R.id.news_type2_time);
            mType2_read= view.findViewById(R.id.news_type2_read);
            mType2_yb= view.findViewById(R.id.news_type2_yb);

            mType3= view.findViewById(R.id.news_type3);
            mType3_title= view.findViewById(R.id.news_type3_title);
            mType3_time= view.findViewById(R.id.news_type3_time);
            mType3_read= view.findViewById(R.id.news_type3_read);
            mType3_yb= view.findViewById(R.id.news_type3_yb);

            mType4= view.findViewById(R.id.news_type4);
            mType4_title= view.findViewById(R.id.news_type4_title);
            mType4_pic1= view.findViewById(R.id.news_type4_pic1);
            mType4_pic2= view.findViewById(R.id.news_type4_pic2);
            mType4_pic3= view.findViewById(R.id.news_type4_pic3);
            mType4_mark= view.findViewById(R.id.news_type4_mark);
        }

    }

}
