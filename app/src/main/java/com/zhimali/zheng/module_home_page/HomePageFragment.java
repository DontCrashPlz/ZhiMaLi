package com.zhimali.zheng.module_home_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zheng.zchlibrary.apps.BaseFragment;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zhimali.zheng.R;
import com.zhimali.zheng.bean.CategoryEntity;
import com.zhimali.zheng.bean.CategoryResponseEntity;
import com.zhimali.zheng.http.Network;

import java.util.ArrayList;

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

    private TextView mSearchTv;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_homepage, container, false);

        initUI(mView);

        Network.getInstance().getCategory(new IAsyncLoadListener<CategoryResponseEntity>() {
            @Override
            public void onSuccess(CategoryResponseEntity categoryResponseEntity) {
                showShortToast(categoryResponseEntity.getMsg());
                if (categoryResponseEntity.getCode()== 0){
                    final ArrayList<CategoryEntity> cates= categoryResponseEntity.getData();
                    if (cates.size()> 0){
                        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
                            @Override
                            public Fragment getItem(int position) {
                                return HomeCategoryFragment.newInstance(cates.get(position).getCatid());
                            }

                            @Override
                            public int getCount() {
                                return cates.size();
                            }

                            @Override
                            public CharSequence getPageTitle(int position) {
                                return cates.get(position).getCatname();
                            }
                        });

                        mTabLayout.setupWithViewPager(mViewPager);
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
            }
        });

        return mView;
    }

    private void initUI(View mView) {
        mSearchTv= mView.findViewById(R.id.toolbar_search);
        mTabLayout= mView.findViewById(R.id.homepage_tab);
        mViewPager= mView.findViewById(R.id.homepage_viewpager);
    }
}
