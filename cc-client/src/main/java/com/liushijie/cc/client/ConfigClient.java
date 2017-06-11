package com.liushijie.cc.client;

import com.liushijie.cc.common.HttpUtil;

import java.util.concurrent.*;

/**
 * Created by liushijie on 5/30/17.
 */
public class ConfigClient {

    private final ConcurrentHashMap<String, ConfigCacheData> localCache = new ConcurrentHashMap<String, ConfigCacheData>();


    static {
        // 开启同步任务
        init();
    }

    private static void init() {
        // TODO 待抽象
        long initialDelay = 0;
        long period = 1;
        TimeUnit unit = TimeUnit.SECONDS;
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // TODO
            }
        }, initialDelay, period, unit);
    }


    /**
     * 获取全量的缓存数据
     * @param key
     * @return
     */
    public String getConfigInfo(String key) {
        ConfigCacheData cacheData;
        if( localCache.containsKey(key)) {
            cacheData = localCache.get(key);
            if (cacheData != null) {
                return cacheData.getValue();
            }
        }
        try {
            cacheData = loadFromRemote(getUrlStr(key));

            if (cacheData != null) {
                cacheData.setKey(key);
                saveToDisk(cacheData);
            } else {
                deleteFromDisk(key);
            }

        } catch (Exception e) {
            cacheData = loadFromSnap(key);
        }

        localCache.put(key, cacheData);

        return cacheData.getValue();
    }

    private void deleteFromDisk(String key) {
        // TODO
        throw new UnsupportedOperationException();
    }

    private void saveToDisk(ConfigCacheData onfigInfo) {
        // TODO 定义好存储格式
        throw new UnsupportedOperationException();
    }


    /**
     * 从远程网络获取信息
     * @param url
     * @return
     */
    private ConfigCacheData loadFromRemote(String url) {
        String message = HttpUtil.invokeHttp(url);
        return ConfigCacheData.parse(message);

    }

    private String getUrlStr(String key) {
        // TODO 使用host方式调用
        throw new UnsupportedOperationException();
    }

    /**
     * 从快照获取配置信息
     * @param key
     * @return
     */
    private ConfigCacheData loadFromSnap(String key) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

}
