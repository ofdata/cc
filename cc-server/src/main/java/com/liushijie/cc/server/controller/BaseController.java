package com.liushijie.cc.server.controller;

/**
 * Created by liushijie on 5/29/17.
 */
public abstract class BaseController {
    public String commonResult(boolean isSuccess) {
        return isSuccess ? "SUCCESS" : "FAILURE";
    }
}
