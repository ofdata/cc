package com.liushijie.cc.server.controller;

import com.liushijie.cc.server.service.ManagerService;
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
public class DataManagerController extends BaseController {

    @Autowired
    private ManagerService managerService;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String insertConfig(String group, String dataId, String content) {
        boolean success = managerService.insertConfig(group, dataId, content);
        return commonResult(success);
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String updateConfig(String group, String dataId, String content) {
        boolean success = managerService.updateConfig(group, dataId, content);
        return commonResult(success);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteConfig(String group, String dataId) {
        boolean success = managerService.deleteConfig(group, dataId);
        return commonResult(success);
    }

}
