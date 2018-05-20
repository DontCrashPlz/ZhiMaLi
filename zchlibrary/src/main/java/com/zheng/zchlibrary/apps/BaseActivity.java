package com.zheng.zchlibrary.apps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.zheng.zchlibrary.interfaces.IBaseView;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.ToastUtils;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Zheng on 2017/10/16.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    private final String ACTIVITY_TAG= this.getClass().getSimpleName();

    public CompositeDisposable compositeDisposable;

    //菊花弹窗
    public ProgressDialog mProgressDialog;
    //圆圈加载提示
    public ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        LogUtil.e(ACTIVITY_TAG, ACTIVITY_TAG + " was Created.");
        ActivityManager.getInstance().addActivity(this);

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        initProgress();
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().removeActivity(this);
        LogUtil.e(ACTIVITY_TAG, ACTIVITY_TAG + " was Destroyed.");
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        super.onDestroy();
    }

    @Override
    public void showLongToast(String msg) {
        if (msg!= null)
            ToastUtils.showLongToast(getRealContext(), msg);
    }

    @Override
    public void showShortToast(String msg) {
        if (msg!= null)
            ToastUtils.showShortToast(getRealContext(), msg);
    }

    @Override
    public Context getRealContext() {
        return this;
    }

    public void addNetWork(Disposable disposable){
        if (compositeDisposable== null){
            compositeDisposable= new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void clearNetWork(){
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    /**
     * 初始化菊花加载弹窗和圆环加载提示
     */
    public abstract void initProgress();

    /**
     * 弹出菊花加载弹窗
     */
    public void showProgressDialog(){
        if (mProgressDialog!= null && !mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }

    /**
     * 取消菊花加载弹窗
     */
    public void dismissProgressDialog(){
        if (mProgressDialog!= null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    /**
     * 弹出圆环加载控件
     */
    public void showProgressBar(){
        if (mProgressBar!= null && !mProgressBar.isShown()){
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 取消圆环加载控件
     */
    public void dismissProgressBar(){
        if (mProgressBar!= null && mProgressBar.isShown()){
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
