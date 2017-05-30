package com.liushijie.cc.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by liushijie on 5/30/17.
 */
public class ConfigClient {



    static {
        // 开启同步任务

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


    public void get(String group, String dataId) {


    }




}
