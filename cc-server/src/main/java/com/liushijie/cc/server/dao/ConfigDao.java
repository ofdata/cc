package com.liushijie.cc.server.dao;

import com.liushijie.cc.server.dao.entity.ConfigInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liushijie on 5/29/17.
 */
@Service
public class ConfigDao extends BaseDao<ConfigInfo, String> {


    public ConfigDao() {
        super(ConfigDao.class);
    }


    public Map<String, Object> getParam(String group, String dataId) {
        Map<String, Object> params = new HashMap<>();
        params.put("group", group);
        params.put("dataId", dataId);

        return params;
    }

}
