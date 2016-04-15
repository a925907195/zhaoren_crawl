package com.bj58.zhaoren.crawl.dao;

import static org.junit.Assert.*;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bj58.zhaoren.crawl.entity.Tboqii;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class tboqiiDaoTest {

	@Resource
	private TboqiiDao tboqiiDao;
	
	@Test
	public void testDeleteByPrimaryKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsert() {
		 Tboqii tboqii=new Tboqii();
		// tboqii.setId(2l);
		 tboqii.setCatalog("22222222");
		 tboqii.setCompanyAddr("fjkdsflsd");
		 tboqii.setCompanyName("fjsdfsld");
		 tboqii.setCreateTime(new Date());
		 tboqii.setUpdateTime(new Date());
		 tboqii.setOtherInfo("55555555");
		 tboqii.setMobile("2345345");
		 tboqii.setUrl("http://www.58.com");
		 tboqiiDao.insert(tboqii);
	}

	@Test
	public void testInsertSelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectByPrimaryKey() {
		System.out.println(tboqiiDao.selectByPrimaryKey(1l).getCompanyAddr());
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateByPrimaryKeyWithBLOBs() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateByPrimaryKey() {
		fail("Not yet implemented");
	}

}
