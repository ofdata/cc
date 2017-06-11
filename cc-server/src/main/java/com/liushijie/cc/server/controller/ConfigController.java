package com.liushijie.cc.server.controller;

import com.liushijie.cc.server.dao.entity.ConfigInfo;
import com.liushijie.cc.server.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liushijie on 17-5-26.
 */
@Controller
@RequestMapping("/config")
public class ConfigController extends BaseController{

    @Autowired
    private ManagerService managerService;

    @RequestMapping("get")
    @ResponseBody
    public String get(String group, String dataId/*, HttpServletResponse response*/) {

        ConfigInfo configInfo = managerService.getConfig(group, dataId);
        if (configInfo != null) {
            return configInfo.getContent() + "\n" + configInfo.getMd5();
        }
        return "error";
    }








}
