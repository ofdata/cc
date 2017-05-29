package com.liushijie.cc.server.service;

import com.liushijie.cc.server.dao.ConfigDao;
import com.liushijie.cc.server.dao.entity.ConfigInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by liushijie on 17-5-26.
 */

@Service
public class ManagerService {

    @Autowired
    private ConfigDao configDao;

    public boolean insertConfig(String group, String dataId, String content) {

        ConfigInfo configInfo = new ConfigInfo(group, dataId, content);
        configDao.insert(configInfo);
        return true;

    }

    public boolean updateConfig(String group, String dataId, String content) {

        ConfigInfo configInfo = new ConfigInfo(group, dataId, content);
        configDao.update(configInfo);

        return true;
    }

    public boolean deleteConfig(String group, String dataId) {
        configDao.delete(genParamsMap(group, dataId));
        return true;
    }

    public ConfigInfo getConfig(String group, String dataId) {

        return configDao.selectOne(genParamsMap(group, dataId));
    }

    private Map<String, Object> genParamsMap(String group, String dataId) {
        return configDao.getParam(group, dataId);
    }
}
