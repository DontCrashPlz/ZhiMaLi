package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/5/23.
 */

public class VersionEntity {
    private AndroidVersionEntity android;

    public AndroidVersionEntity getAndroid() {
        return android;
    }

    public void setAndroid(AndroidVersionEntity android) {
        this.android = android;
    }

    @Override
    public String toString() {
        return "VersionEntity{" +
                "android=" + android +
                '}';
    }
}
