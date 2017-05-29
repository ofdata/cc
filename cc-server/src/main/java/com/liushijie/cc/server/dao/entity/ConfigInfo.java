package com.liushijie.cc.server.dao.entity;

import com.liushijie.cc.common.MD5;

import java.io.Serializable;

/**
 * Created by liushijie on 5/29/17.
 */
public class ConfigInfo implements Serializable {

    public ConfigInfo() {
        super();
    }

    public ConfigInfo(String dataId, String group, String content) {
        super();
        this.dataId = dataId;
        this.content = content;
        this.group = group;
        if (this.content != null) {
            this.md5 = MD5.getInstance().getMD5String(this.content);
        }
    }

    private String group;

    private String dataId;

    private String content;

    private String md5;

    private String createTime;

    private String modifyTime;


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
