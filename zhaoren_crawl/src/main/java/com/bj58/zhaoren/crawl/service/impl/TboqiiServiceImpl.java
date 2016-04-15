package com.bj58.zhaoren.crawl.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bj58.zhaoren.crawl.dao.TboqiiDao;
import com.bj58.zhaoren.crawl.entity.Tboqii;
import com.bj58.zhaoren.crawl.service.TboqiiService;

@Service
public class TboqiiServiceImpl implements TboqiiService{

	@Resource
	private TboqiiDao tboqiiDao;
	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return tboqiiDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Tboqii record) {
		// TODO Auto-generated method stub
		return tboqiiDao.insert(record);
	}

	@Override
	public int insertSelective(Tboqii record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Tboqii selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return tboqiiDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Tboqii record) {
		// TODO Auto-generated method stub
		return tboqiiDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Tboqii record) {
		// TODO Auto-generated method stub
		return tboqiiDao.updateByPrimaryKey(record);
	}

}
