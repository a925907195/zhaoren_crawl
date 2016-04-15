package com.bj58.zhaoren.crawl.HttpclientGethtmlStr;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class httpclentPoolUnitTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	/**
	 * @Author fjsh
	 * @Title Getinfo
	 * @Description 简单测试看是否能通过
	 * @Return void
	 * @Throws 
	 * @Date 2014年6月18日
	 */
	@Test
	public void Getinfo() {
		GethtmlStrByUrl ghsu=new GethtmlStrByUrl();
	 String[] strings=	ghsu.GetHtmlStr("http://sou.zhaopin.com/Jobs/SearchResult.ashx?pd=1&jl=%E5%8C%97%E4%BA%AC");
	 for(int i=0;i<strings.length;i++)
	 {
		 System.out.println(strings[i]);
	 }
	}
	
	/**
	 * @Author fjsh
	 * @Title Getlots
	 * @Description 极限测试，测试此连接池是否可以经得起压力
	 * @Return void
	 * @Throws 
	 * @Date 2014年6月18日
	 */
	/*@Test 
	public void Getlots(){
		GethtmlStrByUrl ghsu=new GethtmlStrByUrl();
		for(int i=0;i<100;i++)
		{
		ghsu.GetHtmlStr("http://search.51job.com/jobsearch/search_result.php?fromJs=1&jobarea=010000%2C00&district=000000&funtype=0000&industrytype=00&issuedate=1&providesalary=99&keywordtype=1&curr_page="+i+"&lang=c&stype=2&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=01&lonlat=0%2C0&radius=-1&ord_field=0&list_type=0&fromType=14");
		System.out.println( HttpClientPools.myHttpClientsQueue.size());
		}
	}*/

}
