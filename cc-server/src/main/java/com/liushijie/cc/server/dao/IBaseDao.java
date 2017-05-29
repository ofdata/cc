package com.liushijie.cc.server.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by liushijie on 17-1-3.
 */
public interface IBaseDao<T,PK> extends IDao {

    T selectOne(Map<String, Object> params);

    T selectOne(PK pk);

    void delete(Map<String, Object> params);

    void delete(PK pk);

    void update(T t);

    void insert(T t);

    void insertOrUpdate(T t);

    List<T> selectList(Map<String, Object> params);

}
