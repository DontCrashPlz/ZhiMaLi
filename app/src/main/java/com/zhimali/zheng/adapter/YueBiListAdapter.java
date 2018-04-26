package com.zhimali.zheng.adapter;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhimali.zheng.R;
import com.zhimali.zheng.bean.YueBiEntity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zheng on 2018/4/26.
 */

public class YueBiListAdapter extends BaseQuickAdapter<YueBiEntity, YueBiListAdapter.YueBiListHolder> {

    public YueBiListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(YueBiListHolder helper, YueBiEntity item) {
        if (mLayoutResId== R.layout.item_yuebi_my){
            helper.mTitleTv.setText(item.getTitle());
            helper.mTimeTv.setText(item.getCreate_time());
            helper.mDetailTv.setText(item.getAmount());
        }else if (mLayoutResId== R.layout.item_yuebi_fans){
            Glide.with(mContext)
                    .load(item.getAvatar())
                    .asBitmap()
                    .placeholder(R.mipmap.yonghu)
                    .error(R.mipmap.yonghu)
                    .into(helper.mHeadCiv);
            helper.mUserNameTv.setText(item.getNickname());
            helper.mTitleTv.setText(item.getTitle());
            helper.mTimeTv.setText(item.getCreate_time());
            helper.mDetailTv.setText(item.getAmount());
        }else if (mLayoutResId== R.layout.item_yuebi_tixian){
            helper.mTitleTv.setText(item.getTitle());
            helper.mTimeTv.setText(item.getCreate_time());
            helper.mDetailTv.setText(item.getAmount());
        }
    }

    class YueBiListHolder extends BaseViewHolder {

        private TextView mTitleTv;
        private TextView mTimeTv;
        private TextView mDetailTv;

        private CircleImageView mHeadCiv;
        private TextView mUserNameTv;

        public YueBiListHolder(View view) {
            super(view);
            if (mLayoutResId== R.layout.item_yuebi_my){
                mTitleTv= view.findViewById(R.id.item_yuebi_my_title);
                mTimeTv= view.findViewById(R.id.item_yuebi_my_time);
                mDetailTv= view.findViewById(R.id.item_yuebi_my_detail);
            }else if (mLayoutResId== R.layout.item_yuebi_fans){
                mHeadCiv= view.findViewById(R.id.item_yuebi_fans_head);
                mUserNameTv= view.findViewById(R.id.item_yuebi_fans_username);
                mTitleTv= view.findViewById(R.id.item_yuebi_fans_title);
                mTimeTv= view.findViewById(R.id.item_yuebi_fans_time);
                mDetailTv= view.findViewById(R.id.item_yuebi_fans_detail);
            }else if (mLayoutResId== R.layout.item_yuebi_tixian){
                mTitleTv= view.findViewById(R.id.item_yuebi_tixian_title);
                mTimeTv= view.findViewById(R.id.item_yuebi_tixian_time);
                mDetailTv= view.findViewById(R.id.item_yuebi_tixian_detail);
            }
        }

    }

}
