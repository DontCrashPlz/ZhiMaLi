package com.zhimali.zheng.bean;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/4/26.
 */

public class FansResponseEntity {
//    {
//        "data": [ ],
//        "code": 0,
//            "msg": "操作成功"
//    }
    private ArrayList<FansEntity> data= new ArrayList<>();
    private int code;
    private String msg;

    public ArrayList<FansEntity> getData() {
        return data;
    }

    public void setData(ArrayList<FansEntity> data) {
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
        return "FansResponseEntity{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
