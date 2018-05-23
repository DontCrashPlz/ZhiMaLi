package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/5/23.
 */

public class AndroidVersionEntity {
    private int version_code;
    private String url;
    private String info;

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "AndroidVersionEntity{" +
                "version_code=" + version_code +
                ", url='" + url + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
