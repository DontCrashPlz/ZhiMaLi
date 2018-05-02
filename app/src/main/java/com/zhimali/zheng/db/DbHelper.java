package com.zhimali.zheng.db;

import com.zhimali.zheng.dao.HistoryData;

import java.util.List;

/**
 * Created by Zheng on 2018/5/2.
 */

public interface DbHelper {

    /**
     * 增加历史数据
     *
     * @param data  added string
     * @return  List<HistoryData>
     */
    List<HistoryData> addHistoryData(String data);

    /**
     * Clear search history data
     */
    void clearHistoryData();

    /**
     * Load all history data
     *
     * @return List<HistoryData>
     */
    List<HistoryData> loadAllHistoryData();
}
