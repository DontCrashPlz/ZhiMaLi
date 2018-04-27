package com.zhimali.zheng.bean;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/4/27.
 */

public class CategoryResponseEntity {
    private ArrayList<CategoryEntity> data= new ArrayList<>();
    private int code;
    private String msg;

    public ArrayList<CategoryEntity> getData() {
        return data;
    }

    public void setData(ArrayList<CategoryEntity> data) {
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
        return "CategoryResponseEntity{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
