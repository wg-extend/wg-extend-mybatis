package com.wg.extend.mybatis.entity.mapper;

import com.wg.extend.mybatis.entity.Demo;

import java.util.List;

public interface DemoMapper {

    Demo getById(Long id);

    List<Demo> getAll();
}
