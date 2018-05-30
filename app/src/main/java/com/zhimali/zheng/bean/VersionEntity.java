package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/5/23.
 */

public class VersionEntity {
    private AndroidVersionEntity android;
    private AndroidVersionEntity ios;

    public AndroidVersionEntity getAndroid() {
        return android;
    }

    public void setAndroid(AndroidVersionEntity android) {
        this.android = android;
    }

    public AndroidVersionEntity getIos() {
        return ios;
    }

    public void setIos(AndroidVersionEntity ios) {
        this.ios = ios;
    }

    @Override
    public String toString() {
        return "VersionEntity{" +
                "android=" + android +
                ", ios=" + ios +
                '}';
    }
}
