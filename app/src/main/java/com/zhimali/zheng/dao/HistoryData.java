package com.zhimali.zheng.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Zheng on 2018/5/2.
 */

@Entity
public class HistoryData {
    @Id(autoincrement = true)
    private Long _id;
    @NotNull
    private String data;
    @Generated(hash = 1325181773)
    public HistoryData(Long _id, @NotNull String data) {
        this._id = _id;
        this.data = data;
    }
    @Generated(hash = 422767273)
    public HistoryData() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
}
