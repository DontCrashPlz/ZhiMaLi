package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/5/3.
 */
@Deprecated
public class NewsDetailResponseEntity {
    private NewsDetailEntity data;
    private int code;
    private String msg;

    public NewsDetailEntity getData() {
        return data;
    }

    public void setData(NewsDetailEntity data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "NewsDetailResponseEntity{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
