package com.liushijie.cc.server.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liushijie on 16-9-5.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    private static Map<String, Pojo> staticMap = new ConcurrentHashMap<>();
    @RequestMapping("gc")
    @ResponseBody
    public String gc() {
        ExecutorService service = Executors.newFixedThreadPool(40);
        for (int i = 0; i < 40; i++) {
            service.submit(() -> {
                while (true) {
                    Thread.sleep(100L);
                    Pojo pojo = new Pojo(System.currentTimeMillis());
                    staticMap.put(UUID.randomUUID().toString(), pojo);
                    // double
                    staticMap.put(UUID.randomUUID().toString(), pojo);
                    // again
                    staticMap.put(UUID.randomUUID().toString(), pojo);

                    Pojo tempPojo = new Pojo(System.currentTimeMillis());
//                    System.out.println(tempPojo.getNow());
                    tempPojo = new Pojo(System.currentTimeMillis());
//                    System.out.println(tempPojo.getNow());
                    tempPojo = new Pojo(System.currentTimeMillis());
//                    System.out.println(tempPojo.getNow());

                }
            });
        }
        return "gc task start";
    }

    private static class Pojo {

        private Pojo() {
        }

        private Pojo(Long now) {
            this.now = now;
        }

        private Long now;

        public Long getNow() {
            return now;
        }

        public void setNow(Long now) {
            this.now = now;
        }
    }

    @RequestMapping("")
    @ResponseBody
    public String index() {
        return "index";
    }

    public static void main(String[] args) {

    }
}
