package com.wg.extend.mybatis.session;

import com.wg.extend.mybatis.config.Configuration;
import com.wg.extend.mybatis.config.JdbcProperties;
import com.wg.extend.mybatis.config.MapperStatement;
import com.wg.extend.mybatis.session.impl.DefaultSqlSessionFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) {

        SAXReader reader = new SAXReader();
        Document document=null;
        Configuration configuration = new Configuration();
        try {
            document = reader.read(inputStream);
            Element configEle = document.getRootElement();

            //读取jdbc的配置文件，然后加载jdbc配置信息
            Element propertiesEle = configEle.element("properties");
            String jdbcPropertiesPath = propertiesEle.attributeValue("resource");
            loadProperties(configuration,jdbcPropertiesPath);

            //mapper的读取
            Element mappersEle = configEle.element("mappers");
            List<Element> mapperEleList = mappersEle.elements("mapper");
            for(Element ele:mapperEleList) {
                String mapperPath = ele.attributeValue("resource");
                loadMapperStatement(configuration,mapperPath);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new DefaultSqlSessionFactory(configuration);
    }

    /**
     * 加载配置信息
     * @param configuration
     */
    private void loadProperties(Configuration configuration,String jdbcPropertiesPath) {
        InputStream inputStream =  this.getClass().getClassLoader().getResourceAsStream(jdbcPropertiesPath);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JdbcProperties jdbcProperties = new JdbcProperties();
        jdbcProperties.setUrl(properties.getProperty("url"));
        jdbcProperties.setDriver(properties.getProperty("driver"));
        jdbcProperties.setPassword(properties.getProperty("password"));
        jdbcProperties.setUsername(properties.getProperty("username"));

        configuration.setJdbcProperties(jdbcProperties);
    }

    /**
     * 加载mapper信息
     * @param configuration
     * @param mapperPath
     */
    private void loadMapperStatement(Configuration configuration, String mapperPath) {
        MapperStatement mapperStatement = null;

        SAXReader reader = new SAXReader();
        Document document=null;
        try {
            InputStream inputStream =  this.getClass().getClassLoader().getResourceAsStream(mapperPath);
            document = reader.read(inputStream);
            Element rootEle = document.getRootElement();

            //获取命名空间
            String namespace = rootEle.attributeValue("namespace");

            for(Element ele:rootEle.elements()) {
                mapperStatement = new MapperStatement();
                mapperStatement.setQueryType(ele.getName());
                mapperStatement.setId(namespace+"."+ele.attributeValue("id"));
                mapperStatement.setResultType(ele.attributeValue("resultType"));
                mapperStatement.setSql(ele.getText());
                configuration.addMapperStatement(mapperStatement);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
