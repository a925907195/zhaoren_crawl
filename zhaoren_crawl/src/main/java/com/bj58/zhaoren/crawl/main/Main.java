package com.bj58.zhaoren.crawl.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bj58.zhaoren.crawl.service.TboqiiService;
import com.bj58.zhaoren.crawl.spider.BoqiiCrawler;

/**
 * @author fjsh123
 *抓取程序的主入口
 */
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
//		TboqiiService tboqiiService=(TboqiiService) ac.getBean("tboqiiService");
//		System.out.println(tboqiiService.selectByPrimaryKey(1l).getCompanyAddr());
//		System.out.println("hello");
		
		//start crawl boqii
		BoqiiCrawler boqiiCrawler=new BoqiiCrawler();
		boqiiCrawler.crawl();
	}

}
