package com.zhimali.zheng.bean;

/**
 * Created by Zheng on 2018/4/27.
 */

public class CategoryEntity {
//    {
//        "catid": "6",
//            "catname": "推荐",
//            "coin": "10"
//    }
    private String catid;
    private String catname;
    private String coin;

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "catid='" + catid + '\'' +
                ", catname='" + catname + '\'' +
                ", coin='" + coin + '\'' +
                '}';
    }
}
