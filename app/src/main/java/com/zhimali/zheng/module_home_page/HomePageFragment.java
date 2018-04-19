package com.zhimali.zheng.module_home_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zheng.zchlibrary.apps.BaseFragment;
import com.zhimali.zheng.R;

/**
 * Created by Zheng on 2018/4/19.
 */

public class HomePageFragment extends BaseFragment {

    public static HomePageFragment newInstance(String content){
        HomePageFragment instance = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_homepage, container, false);

        initUI(mView);

        return mView;
    }

    private void initUI(View mView) {

    }
}
