package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/4/23.
 */
@Deprecated
public class LoginEntity {
//    {
//        "data": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPVUx4QS5TSElKSUVkZCIsImF1ZCI6Ik9VTHhBLlNISUpJRWRkIiwiaWF0IjoxNTI0NDczOTczLCJuYmYiOjE1MjQ0NzM5NzMsInVpZCI6IjgiLCJtb2JpbGUiOiIxMzUyMzAwNTUyOSJ9.5z8ph9htRnym0kRV7BGEVZDbKtKqhjmw2tr1xTvtksM",
//            "code": 0,
//            "msg": "登录成功"
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
        return "LoginEntity{" +
                "data='" + data + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
