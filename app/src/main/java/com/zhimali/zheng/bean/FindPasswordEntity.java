package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/4/25.
 */

public class FindPasswordEntity {
//    {
//        "data": null,
//            "code": 0,
//            "msg": "更新成功,请重新登录"
//    }
    private String data;
    private int code;
    private String msg;

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

    @Override
    public String toString() {
        return "FindPasswordEntity{" +
                "data='" + data + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
