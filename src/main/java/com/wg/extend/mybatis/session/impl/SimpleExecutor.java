package com.wg.extend.mybatis.session.impl;

import com.wg.extend.mybatis.config.JdbcProperties;
import com.wg.extend.mybatis.config.MapperStatement;
import com.wg.extend.mybatis.session.Executor;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {

    private JdbcProperties jdbcProperties;

    public SimpleExecutor(JdbcProperties jdbcProperties) {
        this.jdbcProperties = jdbcProperties;
    }

    public <E> List<E> query(MapperStatement ms, Object parameter) {
        List<E> ret = new ArrayList<E>();
        // 具体的方法待实现
        try {
            // 加载驱动
            Class.forName(jdbcProperties.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // 获取连接
            connection = DriverManager.getConnection(jdbcProperties.getUrl(), jdbcProperties.getUsername(),
                    jdbcProperties.getPassword());
            // 预编译sql语句
            preparedStatement = connection.prepareStatement(ms.getSql());
            // 处理sql语句中的占位符
            parameterize(preparedStatement, parameter);
            // 执行sql语句
            resultSet = preparedStatement.executeQuery();
            // 处理结果
            handlerResultSet(resultSet, ret, ms.getResultType());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private void parameterize(PreparedStatement preparedStatement, Object parameter) throws SQLException {
        if (parameter instanceof String) {
            preparedStatement.setString(1, (String) parameter);
        } else if (parameter instanceof Long) {
            preparedStatement.setLong(1, (Long) parameter);
        } else if (parameter instanceof Integer) {
            preparedStatement.setInt(1, (Integer) parameter);
        }
    }

    private <E> void handlerResultSet(ResultSet resultSet, List<E> ret, String className) {
        Class<E> clazz = null;
        try {
            clazz = (Class<E>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                // 通过反射实例化对象
                Object entity = clazz.newInstance();
                // 使用反射工具将resultSet中的数据填充到entity中
                // id,name,sex,age
                // 获取实体类的所有属性，返回Field数组
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fname = field.getName();
                    Type type = field.getGenericType();
                    if (type.toString().equals("class java.lang.String")) {
                        String column = resultSet.getString(fname);
                        field.set(entity, column);
                    }else if (type.toString().equals("long")) {
                        Long column = resultSet.getLong(fname);
                        field.set(entity, column);
                    }
                }
                ret.add((E) entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
