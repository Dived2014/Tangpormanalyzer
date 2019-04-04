package com.Dived2014.tanganalyze.crawler.Common;/*
 *国伟
 *2019/3/17
 *10:36
 *用于存储清洗的数据
 *
 *
 */

import java.util.HashMap;
import java.util.Map;

public class DataSet {
    private Map<String, Object> data = new HashMap<>();

    public void putData(String key, Object value) {
        this.data.put(key, value);
    }

    public Map<String, Object> getData() {
        return new HashMap<>(this.data);
    }

    public Object getData(String key) {
        return this.data.get(key);
    }
}
