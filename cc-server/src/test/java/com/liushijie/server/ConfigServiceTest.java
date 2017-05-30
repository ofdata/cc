package com.liushijie.server;

import com.liushijie.cc.server.dao.entity.ConfigInfo;
import com.liushijie.cc.server.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by liushijie on 5/29/17.
 */
@ContextConfiguration(locations={"classpath*:/spring-config.xml"})
public class ConfigServiceTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private ManagerService managerService;

    @Test
    public void test() {

        String[] locations = {"classpath:/spring-config.xml"};
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext(locations);
        ManagerService service = (ManagerService) ctx.getBean("managerService");
        System.out.println(service.getConfig("test", "test").getMd5());

    }

    @Test
    public void get() {
        ConfigInfo config = managerService.getConfig("test", "test");

        System.out.println(config.getContent());
    }

    @Test
    public void insert() {
        boolean result = managerService.insertConfig("test", "test", "test");
        Assert.assertTrue(result);
    }

    @Test
    public void update() {
        boolean result = managerService.updateConfig("test", "test", "abc");
        Assert.assertTrue(result);

    }

    @Test
    public void delete() {
        boolean result = managerService.deleteConfig("test", "test");
        Assert.assertTrue(result);

    }

}
