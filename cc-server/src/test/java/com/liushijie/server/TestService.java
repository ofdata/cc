package com.liushijie.server;

import org.springframework.stereotype.Service;

/**
 * Created by liushijie on 5/29/17.
 */
@Service(value = "testService")
public class TestService {

    public void test() {
        System.out.println("1234");
    }
}
