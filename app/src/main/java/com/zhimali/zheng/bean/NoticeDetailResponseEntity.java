package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/5/4.
 */

public class NoticeDetailResponseEntity {
    private NoticeDetailEntity data;
    private int code;
    private String msg;

    public NoticeDetailEntity getData() {
        return data;
    }

    public void setData(NoticeDetailEntity data) {
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
        return "NoticeDetailResponseEntity{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}