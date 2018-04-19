package com.zhimali.zheng.apps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.zheng.zchlibrary.apps.BaseActivity;
import com.zhimali.zheng.R;
import com.zhimali.zheng.module_home_page.HomePageFragment;
import com.zhimali.zheng.module_mine.HomeMineFragment;
import com.zhimali.zheng.module_video.HomeVideoFragment;

/**
 * Created by Zheng on 2018/4/19.
 */

public class HomeActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private RadioButton mHomePageRbtn;
    private RadioButton mVideoRbtn;
    private RadioButton mMineRbtn;

    private FragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();

        mHomePageRbtn.setChecked(true);
    }

    private void initUI() {

        manager= getSupportFragmentManager();

        mHomePageRbtn= findViewById(R.id.home_rbtn_homepage);
        mHomePageRbtn.setOnCheckedChangeListener(this);
        mVideoRbtn= findViewById(R.id.home_rbtn_video);
        mVideoRbtn.setOnCheckedChangeListener(this);
        mMineRbtn= findViewById(R.id.home_rbtn_mine);
        mMineRbtn.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int vId= buttonView.getId();
        switch (vId){
            case R.id.home_rbtn_homepage:
                if (isChecked){
                    manager.beginTransaction()
                            .replace(R.id.home_fly_fragment, HomePageFragment.newInstance(""))
                            .commit();
                }
                break;
            case R.id.home_rbtn_video:
                if (isChecked){
                    manager.beginTransaction()
                            .replace(R.id.home_fly_fragment, HomeVideoFragment.newInstance(""))
                            .commit();
                }
                break;
            case R.id.home_rbtn_mine:
                if (isChecked){
                    manager.beginTransaction()
                            .replace(R.id.home_fly_fragment, HomeMineFragment.newInstance(""))
                            .commit();
                }
                break;
            default:
                showShortToast("无效的导航条切换");
                break;
        }
    }
}
