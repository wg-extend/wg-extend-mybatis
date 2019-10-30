package com.wg.extend.mybatis.session.impl;

import com.wg.extend.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

public class MapperProxy implements InvocationHandler {

    private SqlSession sqlSession;
    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String statement = method.getDeclaringClass().getName()+"."+method.getName();
        //isAssignableFrom方法是判断是否为某个类的父类
        if(Collection.class.isAssignableFrom(method.getReturnType())) {
            //返回值是集合的话，那么是调用selectList
            return sqlSession.selectList(statement,null==args?null:args[0]);
        }else {
            return sqlSession.selectOne(statement,null==args?null:args[0]);
        }
    }
}
