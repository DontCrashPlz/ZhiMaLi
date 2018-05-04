package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/5/4.
 */

public class HelpDetailEntity {
//    "id": "116",
//            "title": "新手指导1",
//            "inputtime": "1523678489",
//            "format_date": "04月14日 12:01",
//            "is_recommend": 0,
//            "content": "<!doctype html>"
    private String id;
    private String title;
    private String inputtime;
    private String format_date;
    private int is_recommend;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
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

    @Override
    public String toString() {
        return "HelpDetailEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", inputtime='" + inputtime + '\'' +
                ", format_date='" + format_date + '\'' +
                ", is_recommend=" + is_recommend +
                ", content='" + content + '\'' +
                '}';
    }
}
