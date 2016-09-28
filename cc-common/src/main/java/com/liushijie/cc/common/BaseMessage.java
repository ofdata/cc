package com.liushijie.cc.common;

import java.io.Serializable;

/**
 * Created by liushijie on 16-9-28.
 */
public class BaseMessage<T> implements Serializable {

    public BaseMessage() {
    }

    public BaseMessage(DataType type) {
        this(null, type);
    }

    public BaseMessage(T data, DataType type) {
        this.data = data;
        this.type = type;
    }

    private T data;
    private DataType type;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }
}
