package com.liushijie.cc.client;

import java.util.concurrent.*;

/**
 * Created by liushijie on 5/30/17.
 */
public class ConfigClient {

    private final ConcurrentHashMap<String, String> localCache = new ConcurrentHashMap<String, String>();


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
        service.scheduleAtFixedRate(() -> {
            // TODO
        }, initialDelay, period, unit);
    }


    /**
     * 获取全量的缓存数据
     * @param key
     * @return
     */
    public String getConfigInfo(String key) {
        String value = null;
        if( localCache.containsKey(key)) {
            value = localCache.get(key);
            if (value == null) {
                value = loadFromRemote(key);
                if (value == null) {
                    value = loadFromSnap(key);
                }
            }
        }

        return value;
    }


    /**
     * 从远程网络获取信息
     * @param key
     * @return
     */
    private String loadFromRemote(String key) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 从快照获取配置信息
     * @param key
     * @return
     */
    private String loadFromSnap(String key) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }





}
