package com.zhimali.zheng.bean;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/5/23.
 */

public class AppBaseEntity {
    private String register_protocol;
    private VersionEntity version;
    private ArrayList<Integer> withdraw_level;
    private int coin_ratio;

    public String getRegister_protocol() {
        return register_protocol;
    }

    public void setRegister_protocol(String register_protocol) {
        this.register_protocol = register_protocol;
    }

    public VersionEntity getVersion() {
        return version;
    }

    public void setVersion(VersionEntity version) {
        this.version = version;
    }

    public ArrayList<Integer> getWithdraw_level() {
        return withdraw_level;
    }

    public void setWithdraw_level(ArrayList<Integer> withdraw_level) {
        this.withdraw_level = withdraw_level;
    }

    public int getCoin_ratio() {
        return coin_ratio;
    }

    public void setCoin_ratio(int coin_ratio) {
        this.coin_ratio = coin_ratio;
    }

    @Override
    public String toString() {
        return "AppBaseEntity{" +
                "register_protocol='" + register_protocol + '\'' +
                ", version=" + version +
                ", withdraw_level=" + withdraw_level +
                ", coin_ratio=" + coin_ratio +
                '}';
    }
}
