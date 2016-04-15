package com.bj58.zhaoren.crawl.service;

import com.bj58.zhaoren.crawl.entity.Tboqii;

public interface TboqiiService {
	 int deleteByPrimaryKey(Long id);

	    int insert(Tboqii record);

	    int insertSelective(Tboqii record);

	    Tboqii selectByPrimaryKey(Long id);

	    int updateByPrimaryKeySelective(Tboqii record);

	    int updateByPrimaryKey(Tboqii record);
}
