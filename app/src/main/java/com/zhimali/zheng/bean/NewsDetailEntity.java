package com.zhimali.zheng.bean;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/5/3.
 */

public class NewsDetailEntity {
//    {
//        "id": "18842",
//         "catid": "17",
//         "title": "嗯哼做果汁水果里倒水就行 霍思燕一句话就教育了孩子",
//         "pics": [
//              "http://imgmini.eastday.com/video/vyule/20180503/20180503153955971725437_1_mwpm_05501609.jpg"
//          ],
//         "inputtime": "1525334101",
//         "video_url": "http://mv.eastday.com/vyule/20180503/20180503153955971725437_1_06400360.mp4",
//         "like_num": "0",
//         "view_num": "1",
//         "coin": "10",
//         "format_date": "2小时前",
//         "is_recommend": 0,
//         "content": "<!doctype html>
//          <html></html>",
//         "share_url": "http://www.52zhimali.com/h5/show.html?id=18842",
//         "share_title": "嗯哼做果汁水果里倒水就行 霍思燕一句话就教育了孩子",
//         "share_content": "嗯哼做果汁水果里倒水就行 霍思燕一句话就教育了孩子",
//         "share_img": "http://imgmini.eastday.com/video/vyule/20180503/20180503153955971725437_1_mwpm_05501609.jpg",
//         "view_id": 0
//    }
    private String id;
    private String catid;
    private String title;
    private ArrayList<String> pics= new ArrayList<>();
    private String inputtime;
    private String video_url;
    private String like_num;
    private String view_num;
    private String coin;
    private String format_date;
    private int is_recommend;
    private String content;
    private String share_url;
    private String share_title;
    private String share_content;
    private String share_img;
    private int view_id;

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

    public int getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(int is_recommend) {
        this.is_recommend = is_recommend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_content() {
        return share_content;
    }

    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }

    public String getShare_img() {
        return share_img;
    }

    public void setShare_img(String share_img) {
        this.share_img = share_img;
    }

    public int getView_id() {
        return view_id;
    }

    public void setView_id(int view_id) {
        this.view_id = view_id;
    }

    @Override
    public String toString() {
        return "NewsDetailEntity{" +
                "id='" + id + '\'' +
                ", catid='" + catid + '\'' +
                ", title='" + title + '\'' +
                ", pics=" + pics +
                ", inputtime='" + inputtime + '\'' +
                ", video_url='" + video_url + '\'' +
                ", like_num='" + like_num + '\'' +
                ", view_num='" + view_num + '\'' +
                ", coin='" + coin + '\'' +
                ", format_date='" + format_date + '\'' +
                ", is_recommend=" + is_recommend +
                ", content='" + content + '\'' +
                ", share_url='" + share_url + '\'' +
                ", share_title='" + share_title + '\'' +
                ", share_content='" + share_content + '\'' +
                ", share_img='" + share_img + '\'' +
                ", view_id=" + view_id +
                '}';
    }
}
