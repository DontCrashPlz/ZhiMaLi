package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/5/4.
 */

public class NoticeDetailEntity {
    private String title;
    private String addtime;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NoticeDetailEntity{" +
                "title='" + title + '\'' +
                ", addtime='" + addtime + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
