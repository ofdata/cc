package com.liushijie.cc.server.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liushijie on 16-9-5.
 */
@Controller
@RequestMapping("/")
public class TestController {
    @RequestMapping("test")
    @ResponseBody
    public String test() {
        return "test";
    }

    @RequestMapping("")
    @ResponseBody
    public String index() {
        return "index";
    }
}
