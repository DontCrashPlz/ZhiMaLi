package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/5/4.
 */

public class NoticeEntity {
    private String title;
    private String addtime;
    private String hits;
    private String id;

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

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NoticeEntity{" +
                "title='" + title + '\'' +
                ", addtime='" + addtime + '\'' +
                ", hits='" + hits + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
