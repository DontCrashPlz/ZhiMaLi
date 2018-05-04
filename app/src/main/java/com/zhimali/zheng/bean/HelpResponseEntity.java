package com.zhimali.zheng.bean;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/5/4.
 */

public class HelpResponseEntity {
    private ArrayList<HelpEntity> data= new ArrayList<>();
    private int code;
    private String msg;

    public ArrayList<HelpEntity> getData() {
        return data;
    }

    public void setData(ArrayList<HelpEntity> data) {
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
        return "HelpResponseEntity{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
