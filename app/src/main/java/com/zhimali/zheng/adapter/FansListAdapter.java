package com.zhimali.zheng.adapter;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhimali.zheng.R;
import com.zhimali.zheng.bean.FansEntity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zheng on 2018/4/26.
 */

public class FansListAdapter extends BaseQuickAdapter<FansEntity, FansListAdapter.FansListHolder> {

    public FansListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(FansListHolder helper, FansEntity item) {
        Glide.with(mContext)
                .load(item.getAvatar())
                .asBitmap()
                .placeholder(R.mipmap.yonghu)
                .error(R.mipmap.yonghu)
                .into(helper.mHeadCiv);
        helper.mUserNameTv.setText(item.getNickname());
        helper.mRegTimeTv.setText(item.getRegdate());
        helper.mYueBiTv.setText(item.getCoin());
    }

    class FansListHolder extends BaseViewHolder{

        private CircleImageView mHeadCiv;
        private TextView mUserNameTv;
        private TextView mRegTimeTv;
        private TextView mYueBiTv;

        public FansListHolder(View view) {
            super(view);
            mHeadCiv= view.findViewById(R.id.item_fans_civ_head);
            mUserNameTv= view.findViewById(R.id.item_fans_tv_username);
            mRegTimeTv= view.findViewById(R.id.item_fans_tv_time);
            mYueBiTv= view.findViewById(R.id.item_fans_tv_yb);
        }

    }

}
