package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/4/26.
 */

public class YueBiEntity {
//    {
//        "id": "23",
//            "userid": "1",
//            "coin": "2",
//            "create_time": "2018-04-20 17:42:27",
//            "title": "易烊千玺“睁眼说瞎话”，明明在楼下却骗妈妈说在机场？",
//            "nickname": "Charles1111",
//            "avatar": "http://www.52zhimali.com/uploadfile/avatar_2018/0329/20180329014738755_thumb.png",
//            "amount": "+2"
//    }
    private String id;
    private String userid;
    private String coin;
    private String create_time;
    private String title;
    private String nickname;
    private String avatar;
    private String amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "YueBiEntity{" +
                "id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                ", coin='" + coin + '\'' +
                ", create_time='" + create_time + '\'' +
                ", title='" + title + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
