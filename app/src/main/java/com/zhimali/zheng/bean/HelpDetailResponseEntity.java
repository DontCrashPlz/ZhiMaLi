package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/5/4.
 */

public class HelpDetailResponseEntity {
    private HelpDetailEntity data;
    private int code;
    private String msg;

    public HelpDetailEntity getData() {
        return data;
    }

    public void setData(HelpDetailEntity data) {
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
        return "HelpDetailResponseEntity{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
