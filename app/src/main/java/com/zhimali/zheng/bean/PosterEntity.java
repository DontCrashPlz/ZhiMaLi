package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/4/27.
 */

public class PosterEntity {
//    {
//        "url": "http://www.52zhimali.com/h5/poster.html?id=12",
//            "img": "http://local.qmhy.com/uploadfile/2018/0329/20180329114805541.jpg",
//            "title": "水电费水电费"
//    }
    private String url;
    private String img;
    private String title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "PosterEntity{" +
                "url='" + url + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
