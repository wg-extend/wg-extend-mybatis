package com.wg.extend.mybatis.session;

import java.util.List;

public interface SqlSession {
    <T> T getMapper(Class<T> type);

    <T> T selectOne(String statement, Object parameter);
    <E> List<E> selectList(String statement);
    <E> List<E> selectList(String statement, Object parameter);

}
