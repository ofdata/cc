package com.liushijie.cc.server.dao;


import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;
import java.util.Map;

/**
 * 公用基类
 * Created by liushijie on 16-12-12.
 */
public abstract class BaseDao<T, PK> extends SqlSessionDaoSupport implements IBaseDao<T, PK> {

    private String className;

    public BaseDao(Class clazz) {
        if (clazz == null) {
            throw new NullPointerException("Paramter clazz never can not be null!");
        }
        this.className = clazz.getName();
    }

    private String getStmtId(String opType) {
        return className + "." +  opType;
    }

    @Override
    public T selectOne(Map<String, Object> params) {
        return getSqlSession().selectOne(getStmtId("select"), params);
    }

    @Override
    public T selectOne(PK pk) {
        return getSqlSession().selectOne(getStmtId("select"), pk);
    }

    @Override
    public void delete(Map<String, Object> params) {
        getSqlSession().delete(getStmtId("delete"), params);
    }

    @Override
    public void delete(PK pk) {
        getSqlSession().delete(getStmtId("delete"), pk);
    }


    @Override
    public void update(T t) {
        getSqlSession().update(getStmtId("update"), t);
    }

    @Override
    public void insert(T t) {
        getSqlSession().insert(getStmtId("insert"), t);
    }

    @Override
    public void insertOrUpdate(T t) {
        throw new UnsupportedOperationException("Human,please do it by yourself!");
    }


    @Override
    public List<T> selectList(Map<String, Object> params) {
        return getSqlSession().selectList(getStmtId("selectList"), params);
    }

}
