package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/4/23.
 */

public class RegisterEntity {
//    {
//        "data": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPVUx4QS5TSElKSUVkZCIsImF1ZCI6Ik9VTHhBLlNISUpJRWRkIiwiaWF0IjoxNTI0NDczNzAyLCJuYmYiOjE1MjQ0NzM3MDIsInVpZCI6OCwibW9iaWxlIjoiMTM1MjMwMDU1MjkifQ.uIV5lchF0uiIOuJytbcGX1qLCQFvq5qxd6SuoQY3XKA",
//            "code": 0,
//            "msg": "注册成功"
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
        return "RegisterEntity{" +
                "data='" + data + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
