package com.liushijie.cc.server.controller;

import com.liushijie.cc.server.dao.entity.ConfigInfo;
import com.liushijie.cc.server.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ConfigInfo get(String group, String dataId) {
        ConfigInfo configInfo = managerService.getConfig(group, dataId);
        if (configInfo != null) {
            configInfo.setCreateTime(null);
            configInfo.setDataId(null);
            configInfo.setGroup(null);
            configInfo.setModifyTime(null);
        }
        return configInfo;
    }








}
