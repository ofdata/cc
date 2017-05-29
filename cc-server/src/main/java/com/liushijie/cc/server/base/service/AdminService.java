package com.liushijie.cc.server.base.service;

import com.lsj.c.server.common.ResourceUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by liushijie on 17-5-26.
 */

@Service
public class AdminService {

    private static final Log log = LogFactory.getLog(AdminService.class);

    private volatile Properties properties = new Properties();

    /**
     * user.properties的路径url
     */
    private URL url;

    public AdminService() {
        loadUsers();
    }

    public void loadUsers() {
        Properties tempProperties = new Properties();
        InputStream in = null;
        try {
            url = ResourceUtils.getResourceURL("user.properties");
            in = new FileInputStream(url.getPath());
            tempProperties.load(in);
        }
        catch (IOException e) {
            log.error("加载user.properties文件失败", e);
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                    log.error("关闭user.properties文件失败", e);
                }
            }
        }
        this.properties = tempProperties;
    }

    public synchronized boolean login(String userName, String password) {
        String passwordInFile = this.properties.getProperty(userName);
        if (passwordInFile != null)
            return passwordInFile.equals(password);
        else
            return false;
    }
}
