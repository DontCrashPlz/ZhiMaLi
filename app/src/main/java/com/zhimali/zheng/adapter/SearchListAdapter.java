package com.zhimali.zheng.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhimali.zheng.R;
import com.zhimali.zheng.bean.NewsListEntity;

/**
 * Created by Zheng on 2018/4/26.
 */

public class SearchListAdapter extends BaseQuickAdapter<NewsListEntity, SearchListAdapter.SearchListHolder> {

    public SearchListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(SearchListHolder helper, NewsListEntity item) {
        if (item.getPics().size()== 0){
            dealType3(helper, item);
        }else if (item.getPics().size()== 1){
            dealType2(helper, item);
        }else {
            dealType1(helper, item);
        }
    }

    /**
     * 多图片新闻
     * @param helper
     * @param item
     */
    private void dealType1(SearchListHolder helper, NewsListEntity item){
        helper.mType1.setVisibility(View.VISIBLE);
        helper.mType2.setVisibility(View.GONE);
        helper.mType3.setVisibility(View.GONE);
        helper.mType1_title.setText(item.getTitle());
        if (item.getPics().size()> 0){
            Glide.with(mContext)
                    .load(item.getPics().get(0))
                    .asBitmap()
                    .placeholder(R.drawable.zhanwei)
                    .error(R.drawable.zhanwei)
                    .into(helper.mType1_pic1);
        }
        if (item.getPics().size()> 1){
            Glide.with(mContext)
                    .load(item.getPics().get(1))
                    .asBitmap()
                    .placeholder(R.drawable.zhanwei)
                    .error(R.drawable.zhanwei)
                    .into(helper.mType1_pic2);
        }
        if (item.getPics().size()> 2){
            Glide.with(mContext)
                    .load(item.getPics().get(2))
                    .asBitmap()
                    .placeholder(R.drawable.zhanwei)
                    .error(R.drawable.zhanwei)
                    .into(helper.mType1_pic3);
        }
        helper.mType1_time.setText(item.getFormat_date());
        helper.mType1_read.setText(item.getView_num());
        helper.mType1_yb.setText(item.getCoin());
    }

    /**
     * 大图片新闻
     * @param helper
     * @param item
     */
    private void dealType2(SearchListHolder helper, NewsListEntity item){
        helper.mType1.setVisibility(View.GONE);
        helper.mType2.setVisibility(View.VISIBLE);
        helper.mType3.setVisibility(View.GONE);
        helper.mType2_title.setText(item.getTitle());
        Glide.with(mContext)
                .load(item.getPics().get(0))
                .asBitmap()
                .placeholder(R.drawable.zhanwei)
                .error(R.drawable.zhanwei)
                .into(helper.mType2_pic);
        helper.mType2_time.setText(item.getFormat_date());
        helper.mType2_read.setText(item.getView_num());
        helper.mType2_yb.setText(item.getCoin());
    }

    /**
     * 无图片新闻
     * @param helper
     * @param item
     */
    private void dealType3(SearchListHolder helper, NewsListEntity item){
        helper.mType1.setVisibility(View.GONE);
        helper.mType2.setVisibility(View.GONE);
        helper.mType3.setVisibility(View.VISIBLE);
        helper.mType3_title.setText(item.getTitle());
        helper.mType3_time.setText(item.getFormat_date());
        helper.mType3_read.setText(item.getView_num());
        helper.mType3_yb.setText(item.getCoin());
    }

    class SearchListHolder extends BaseViewHolder {

        private RelativeLayout mType1;
        private RelativeLayout mType2;
        private RelativeLayout mType3;

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

        public SearchListHolder(View view) {
            super(view);
            mType1= view.findViewById(R.id.news_search_type1);
            mType1_title= view.findViewById(R.id.news_search_type1_title);
            mType1_pic1= view.findViewById(R.id.news_search_type1_pic1);
            mType1_pic2= view.findViewById(R.id.news_search_type1_pic2);
            mType1_pic3= view.findViewById(R.id.news_search_type1_pic3);
            mType1_time= view.findViewById(R.id.news_search_type1_time);
            mType1_read= view.findViewById(R.id.news_search_type1_read);
            mType1_yb= view.findViewById(R.id.news_search_type1_yb);

            mType2= view.findViewById(R.id.news_search_type2);
            mType2_title= view.findViewById(R.id.news_search_type2_title);
            mType2_pic= view.findViewById(R.id.news_search_type2_pic);
            mType2_time= view.findViewById(R.id.news_search_type2_time);
            mType2_read= view.findViewById(R.id.news_search_type2_read);
            mType2_yb= view.findViewById(R.id.news_search_type2_yb);

            mType3= view.findViewById(R.id.news_search_type3);
            mType3_title= view.findViewById(R.id.news_search_type3_title);
            mType3_time= view.findViewById(R.id.news_search_type3_time);
            mType3_read= view.findViewById(R.id.news_search_type3_read);
            mType3_yb= view.findViewById(R.id.news_search_type3_yb);
        }

    }

}
