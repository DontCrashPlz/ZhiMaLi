package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/4/25.
 */

public class UserEntity {
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
    private String userid;
    private String nickname;
    private String avatar;
    private String coin;
    private String mobile;
    private String gender;
    private String invite_code;
    private int fans;
    private ParentEntity parent;
    private int signed;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public ParentEntity getParent() {
        return parent;
    }

    public void setParent(ParentEntity parent) {
        this.parent = parent;
    }

    public int getSigned() {
        return signed;
    }

    public void setSigned(int signed) {
        this.signed = signed;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userid='" + userid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", coin='" + coin + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", invite_code='" + invite_code + '\'' +
                ", fans=" + fans +
                ", parent=" + parent +
                ", signed=" + signed +
                '}';
    }
}
