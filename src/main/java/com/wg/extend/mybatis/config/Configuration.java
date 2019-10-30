package com.wg.extend.mybatis.config;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private JdbcProperties jdbcProperties;
    private Map<String,MapperStatement> mapperMap = new HashMap<String, MapperStatement>();

    public void addMapperStatement(MapperStatement mapperStatement) {
        mapperMap.put(mapperStatement.getId(),mapperStatement);
    }

    public MapperStatement getMapperStatement(String id) {
        return mapperMap.get(id);
    }

    public JdbcProperties getJdbcProperties() {
        return jdbcProperties;
    }

    public void setJdbcProperties(JdbcProperties jdbcProperties) {
        this.jdbcProperties = jdbcProperties;
    }
}
