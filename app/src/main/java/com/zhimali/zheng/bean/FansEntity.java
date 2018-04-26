package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/4/26.
 */

public class FansEntity {
    private String userid;
    private String nickname;
    private String gender;
    private String regdate;
    private String coin;
    private String avatar;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "FansEntity{" +
                "userid='" + userid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", regdate='" + regdate + '\'' +
                ", coin='" + coin + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
