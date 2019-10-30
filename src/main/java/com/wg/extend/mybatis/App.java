package com.wg.extend.mybatis;

import com.wg.extend.mybatis.entity.Demo;
import com.wg.extend.mybatis.entity.mapper.DemoMapper;
import com.wg.extend.mybatis.session.SqlSession;
import com.wg.extend.mybatis.session.SqlSessionFactory;
import com.wg.extend.mybatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class App {
    public static void main(String[] args) {
        String resource = "mybatis-config.xml";
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        System.out.println(sqlSessionFactory);
        System.out.println(sqlSessionFactory.getConfiguration().getJdbcProperties().getUrl());

        SqlSession sqlSession = sqlSessionFactory.openSession();
        Demo demo = null;
        List<Demo> demos = null;

        demo = sqlSession.selectOne("com.wg.extend.mybatis.entity.mapper.DemoMapper.getById",1L);
        System.out.println(demo);
        demos = sqlSession.selectList("com.wg.extend.mybatis.entity.mapper.DemoMapper.getAll");
        System.out.println(demos);

        //使用Mapper
        DemoMapper demoMapper = sqlSession.getMapper(DemoMapper.class);
        demo = demoMapper.getById(1);
        System.out.println(demo);
        demos = demoMapper.getAll();
        System.out.println(demos);
    }
}
