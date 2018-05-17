package com.zheng.zchlibrary.apps;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zheng.zchlibrary.interfaces.*;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.NetworkUtil;
import com.zheng.zchlibrary.utils.ToastUtils;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Zheng on 2018/4/14.
 */

public abstract class BaseFragment extends Fragment implements IBaseView {

    private final String fragmentTag= this.getClass().getSimpleName();

    public CompositeDisposable compositeDisposable;

    //菊花弹窗
    public ProgressDialog mProgressDialog;
    //圆圈加载提示
    public ProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(fragmentTag, fragmentTag + "is Created!");
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        initProgressDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        initProgressBar(getView());
    }

    @Override
    public void onDestroy() {
        LogUtil.e(fragmentTag, fragmentTag + "is Destroyed!");
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
        return getContext();
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

    public abstract void initProgressDialog();
    public abstract void initProgressBar(View view);

    public void showProgressDialog(){
        if (mProgressDialog!= null && !mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog(){
        if (mProgressDialog!= null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    public void showProgressBar(){
        if (mProgressBar!= null && !mProgressBar.isShown()){
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void dismissProgressBar(){
        if (mProgressBar!= null && mProgressBar.isShown()){
            mProgressBar.setVisibility(View.GONE);
        }
    }

}
