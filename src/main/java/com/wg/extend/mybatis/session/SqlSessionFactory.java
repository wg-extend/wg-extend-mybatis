package com.wg.extend.mybatis.session;

import com.wg.extend.mybatis.config.Configuration;

public interface SqlSessionFactory {

    Configuration getConfiguration();

    SqlSession openSession();
}
