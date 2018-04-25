package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/4/25.
 */

public class UserResponseEntity {
//    {
//        "data": {
//          "userid": "1",
//          "nickname": "Charles1111",
//          "avatar": "http://www.52zhimali.com/uploadfile/avatar_2018/0329/20180329014738755_thumb.png",
//          "coin": "9970",
//          "mobile": "18628086567",
//          "gender": "2",
//          "invite_code": "1111",
//          "fans": 0,
//          "parent": {
//            "avatar": ""
//          },
//          "signed": 0
//        },
//        "code": 0,
//        "msg": "操作成功"
//    }
    private UserEntity data;
    private int code;
    private String msg;

    public UserEntity getData() {
        return data;
    }

    public void setData(UserEntity data) {
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
        return "UserResponseEntity{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
