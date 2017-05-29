package com.liushijie.cc.server.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liushijie on 17-5-26.
 */
@Controller
@RequestMapping("/config")
public class ConfigController {

    @RequestMapping("get")
    @ResponseBody
    public String get(String group, String dataId) {
        // TODO
        return "/config/get/test";
    }








}
