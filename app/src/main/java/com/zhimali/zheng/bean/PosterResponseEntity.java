package com.zhimali.zheng.bean;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/4/27.
 */

public class PosterResponseEntity {
//    {
//        "data": [
//        {
//            "url": "http://www.52zhimali.com/h5/poster.html?id=12",
//                "img": "http://local.qmhy.com/uploadfile/2018/0329/20180329114805541.jpg",
//                "title": "水电费水电费"
//        }
//    ],
//        "code": 0,
//            "msg": "操作成功"
//    }
    private ArrayList<PosterEntity> data= new ArrayList<>();
    private int code;
    private String msg;

    public ArrayList<PosterEntity> getData() {
        return data;
    }

    public void setData(ArrayList<PosterEntity> data) {
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
        return "PosterResponseEntity{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
