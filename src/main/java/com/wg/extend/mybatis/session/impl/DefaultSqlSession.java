package com.wg.extend.mybatis.session.impl;

import com.wg.extend.mybatis.config.Configuration;
import com.wg.extend.mybatis.session.Executor;
import com.wg.extend.mybatis.session.SqlSession;

import java.lang.reflect.Proxy;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration,Executor executor) {
        this.configuration= configuration;
        this.executor = executor;
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> type) {
        T newProxyInstance = (T) Proxy.newProxyInstance(type.getClassLoader(),new Class[]{type},new MapperProxy(this));
        return newProxyInstance;
    }

    public <T> T selectOne(String statement, Object parameter) {
        List<T> list = executor.query(configuration.getMapperStatement(statement),parameter);
        if(list.size()>0) {
            return list.get(0);
        }
        return null;
    }

    public <E> List<E> selectList(String statement) {
        return executor.query(configuration.getMapperStatement(statement),null);
    }

    public <E> List<E> selectList(String statement, Object parameter) {
        return executor.query(configuration.getMapperStatement(statement), parameter);
    }
}
