package com.liushijie.cc.server.controller;

import com.liushijie.cc.server.dao.entity.ConfigInfo;
import com.liushijie.cc.server.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public Map<String, Object> get(String group, String dataId) {

        Map<String, Object> result = new LinkedHashMap<>(2);

        ConfigInfo configInfo = managerService.getConfig(group, dataId);
        if (configInfo != null) {
            result.put("content", configInfo.getContent());
            result.put("md5", configInfo.getMd5());
        }
        return result;
    }








}
