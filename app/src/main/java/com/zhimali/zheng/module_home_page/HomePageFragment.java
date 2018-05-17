package com.zhimali.zheng.module_home_page;

import android.content.Intent;
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
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;
import com.zhimali.zheng.R;
import com.zhimali.zheng.bean.CategoryEntity;
import com.zhimali.zheng.bean.CategoryResponseEntity;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

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
    private ArrayList<CategoryEntity> cates;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_homepage, container, false);

        initUI(mView);

        addNetWork(
                Network.getInstance().getCategory()
                        .compose(ResponseTransformer.changeThread())
                        .compose(ResponseTransformer.handleResult())
                        .subscribe(new Consumer<ArrayList<CategoryEntity>>() {
                            @Override
                            public void accept(ArrayList<CategoryEntity> categoryEntities) throws Exception {
                                dismissProgressDialog();
                                if (cates.size() > 0) {
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
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dismissProgressDialog();
                                showShortToast(throwable.toString());
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                dismissProgressDialog();
                            }
                        }, new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                showProgressDialog();
                            }
                        }));

        return mView;
    }

    private void initUI(View mView) {
        mSearchTv= mView.findViewById(R.id.toolbar_search);
        mSearchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cates== null || cates.size()< 1){
                    showShortToast("频道列表加载失败");
                    return;
                }
                String catid= cates.get(mViewPager.getCurrentItem()).getCatid();
                if (catid== null || catid.length()< 1){
                    showShortToast("频道id加载失败");
                    return;
                }
                LogUtil.d("catid", catid);
                Intent intent= new Intent(getRealContext(), SearchActivity.class);
                intent.putExtra("catid", catid);
                startActivity(intent);
            }
        });
        mTabLayout= mView.findViewById(R.id.homepage_tab);
        mViewPager= mView.findViewById(R.id.homepage_viewpager);
    }

    @Override
    public void initProgressDialog() {
        mProgressDialog = new ProgressDialog(getRealContext());
        mProgressDialog.setLabel("正在加载频道");
    }

    @Override
    public void initProgressBar(View view) {

    }
}
