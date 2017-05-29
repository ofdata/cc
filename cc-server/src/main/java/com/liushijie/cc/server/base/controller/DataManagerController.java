package com.liushijie.cc.server.base.controller;

import com.lsj.c.server.base.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liushijie on 17-5-26.
 */

@Controller
@RequestMapping("/manager")
public class DataManagerController {

    @Autowired
    private ManagerService managerService;

    @RequestMapping("add")
    @ResponseBody
    public String insertConfig(String group, String dataId, String content) {
        return updateConfig(group, dataId, content);
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String updateConfig(String group, String dataId, String content) {

        return "/manager/update";
    }

    @RequestMapping("delete")
    @ResponseBody
    public String deleteConfig(String group, String dataId) {

        return "/manager/delete";
    }














}
