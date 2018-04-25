package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/4/25.
 */

public class NameSetEntity {
//    {
//        "data": null,
//            "code": 0,
//            "msg": "设置成功"
//    }
    private String data;
    private int code;
    private String msg;

    @Override
    public String toString() {
        return "NameSetEntity{" +
                "data='" + data + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
}
