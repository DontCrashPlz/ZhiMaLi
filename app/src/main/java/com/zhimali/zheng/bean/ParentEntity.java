package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/4/25.
 */

public class ParentEntity {
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "ParentEntity{" +
                "avatar='" + avatar + '\'' +
                '}';
    }
}
