package com.zhimali.zheng.apps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
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

    @Override
    public void initProgress() {

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

    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                showShortToast("再按一次退出程序");
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                MyApplication.getInstance().AppExit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
