package com.liushijie.cc.client;

import java.io.Serializable;

/**
 * Created by liushijie on 6/11/17.
 */
public class ConfigCacheData implements Serializable {
    private String key;
    private String value;
    private String md5;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }


    public static ConfigCacheData parse(String message) {
        return parse(message, "\n");
    }
    public static ConfigCacheData parse(String message, String split) {
        ConfigCacheData configCacheData = new ConfigCacheData();
        String[] elements = message.split(split);
        configCacheData.setValue(elements[0]);
        configCacheData.setMd5(elements[1]);

        return configCacheData;
    }
}
