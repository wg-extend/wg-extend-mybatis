package com.wg.extend.mybatis.config;

public class MapperStatement {

    private String id;//由二位坐标构成的ID=namespave.id
    private String sql;//sql语句
    private String resultType;//返回类型
    private String queryType;//查询语句类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }
}
