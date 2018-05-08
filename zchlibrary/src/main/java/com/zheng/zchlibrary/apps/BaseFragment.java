package com.zheng.zchlibrary.apps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.zheng.zchlibrary.interfaces.*;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.ToastUtils;

/**
 * Created by Zheng on 2018/4/14.
 */

public class BaseFragment extends Fragment implements IBaseView {

    public final String fragmentTag= this.getClass().getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.e(fragmentTag, fragmentTag + "is Created!");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(fragmentTag, fragmentTag + "is Destroyed!");
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
}
