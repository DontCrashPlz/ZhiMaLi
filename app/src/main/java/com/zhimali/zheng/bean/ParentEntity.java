package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/4/25.
 */

public class ParentEntity {
//    "userid": "1",
//    "nickname": "123",
//    "avatar": "http://www.52zhimali.com/uploadfile/avatar_2018/0329/20180329014738755_thumb.png",
//    "invite_code": "1111"
    private String userid;
    private String nickname;
    private String avatar;
    private String invite_code;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "ParentEntity{" +
                "userid='" + userid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", invite_code='" + invite_code + '\'' +
                '}';
    }
}
