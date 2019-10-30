package com.wg.extend.mybatis.session.impl;

import com.wg.extend.mybatis.config.Configuration;
import com.wg.extend.mybatis.session.Executor;
import com.wg.extend.mybatis.session.SqlSession;
import com.wg.extend.mybatis.session.SqlSessionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public SqlSession openSession() {
        Executor executor = new SimpleExecutor(configuration.getJdbcProperties());
        SqlSession sqlSession = new DefaultSqlSession(configuration, executor);
        return sqlSession;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
