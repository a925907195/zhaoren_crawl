package com.bj58.zhaoren.crawl.dao;

import com.bj58.zhaoren.crawl.entity.Tboqii;

public interface TboqiiDao {
    int deleteByPrimaryKey(Long id);

    int insert(Tboqii record);

    int insertSelective(Tboqii record);

    Tboqii selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Tboqii record);

    int updateByPrimaryKey(Tboqii record);
}