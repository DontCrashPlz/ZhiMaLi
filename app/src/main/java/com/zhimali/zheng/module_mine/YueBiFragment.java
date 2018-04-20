package com.zhimali.zheng.module_mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zheng.zchlibrary.apps.BaseFragment;
import com.zhimali.zheng.R;

/**
 * Created by Zheng on 2018/4/20.
 */

public class YueBiFragment extends BaseFragment {

    public static final int YUEBI_MY= 0;
    public static final int YUEBI_FANS= 1;
    public static final int YUEBI_TIXIAN= 2;

    public static YueBiFragment newInstance(int pageTag){
        YueBiFragment instance = new YueBiFragment();
        Bundle args = new Bundle();
        args.putInt("page_tag", pageTag);
        instance.setArguments(args);
        return instance;
    }

    private RecyclerView mRecycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.layout_recycler_only, container, false);
        mRecycler= mView.findViewById(R.id.recyclerview);

        return mView;
    }
}
