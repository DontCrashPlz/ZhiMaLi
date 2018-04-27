package com.zhimali.zheng.bean;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/4/27.
 */

public class NewsListEntity {
//    {
//        "id": "13396",
//            "catid": "6",
//            "title": "蓬佩奥出任美国务卿 有望坚定执行\"特氏\"外交政策",
//            "pics": [
//        "http://03.imgmini.eastday.com/mobile/20180427/20180427081953_13b6f89b9641a17ec72a0904cda20a84_1_mwpm_03200403.jpg"
//            ],
//        "url": "",
//            "inputtime": "1524810441",
//            "video_url": "",
//            "like_num": "0",
//            "view_num": "0",
//            "coin": "10",
//            "format_date": "2小时前",
//            "is_recommend": 0
//    }
    private String id;
    private String catid;
    private String title;
    private ArrayList<String> pics= new ArrayList<>();
    private String url;
    private String inputtime;
    private String video_url;
    private String like_num;
    private String view_num;
    private String coin;
    private String format_date;
    private String is_recommend;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getPics() {
        return pics;
    }

    public void setPics(ArrayList<String> pics) {
        this.pics = pics;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getLike_num() {
        return like_num;
    }

    public void setLike_num(String like_num) {
        this.like_num = like_num;
    }

    public String getView_num() {
        return view_num;
    }

    public void setView_num(String view_num) {
        this.view_num = view_num;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getFormat_date() {
        return format_date;
    }

    public void setFormat_date(String format_date) {
        this.format_date = format_date;
    }

    public String getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(String is_recommend) {
        this.is_recommend = is_recommend;
    }

    @Override
    public String toString() {
        return "NewsListEntity{" +
                "id='" + id + '\'' +
                ", catid='" + catid + '\'' +
                ", title='" + title + '\'' +
                ", pics=" + pics +
                ", url='" + url + '\'' +
                ", inputtime='" + inputtime + '\'' +
                ", video_url='" + video_url + '\'' +
                ", like_num='" + like_num + '\'' +
                ", view_num='" + view_num + '\'' +
                ", coin='" + coin + '\'' +
                ", format_date='" + format_date + '\'' +
                ", is_recommend='" + is_recommend + '\'' +
                '}';
    }
}
