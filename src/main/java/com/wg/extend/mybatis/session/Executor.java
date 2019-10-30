package com.wg.extend.mybatis.session;

import com.wg.extend.mybatis.config.MapperStatement;

import java.util.List;

public interface Executor  {

    <E> List<E> query(MapperStatement ms, Object parameter);
}
