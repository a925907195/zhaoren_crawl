package com.fjsh.expression.HttpclientGethtmlStr;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Project JobsPider
 * @Package jobs.HttpclientGethtmlStr
 * @ClassName: GethtmlStrPools 
 * @Author fjsh
 * @Description: 初始化myhttpclientpool并实现随机获取对象处理
 * @Date 2014年6月18日 下午4:41:21 
 */


public class HttpClientPools {
	public static Queue<MyHttpClient> myHttpClientsQueue=new LinkedList<>();//定义队列存储myhttpclient对象
	public static int initMyhttpclientNum=10;//初始化myhttpclient对象的个数

	
	/**
	 * @Description: TODO
	 * @param initMyhttpclientNum 记录初始化时初始的连接池中httpclient数量
	 * @throws
	 */
	public HttpClientPools(int initMyhttpclientNum) {
		this.initMyhttpclientPool();//初始化httpclient数据池
		// TODO Auto-generated constructor stub
	}
	/**
	 * @Author fjsh
	 * @Title gethttClientbypool
	 * @Description 同步从队列中取出一个myhttpclient对象，如果没有就新建一个myhttpclient
	 * @return
	 * @Return MyHttpClient
	 * @Throws 
	 * @Date 2014年6月18日
	 */
	public MyHttpClient GethttpClientbypool()
	{
		synchronized (this) {
			if (myHttpClientsQueue.size() > 0) {
				return myHttpClientsQueue.poll();
			}
			else
			{
				return new MyHttpClient();
			}
		}		
	}
	public void ReleaseHttpClient(MyHttpClient myHttpClientitem)
	{
		synchronized (this) {
			myHttpClientsQueue.offer(myHttpClientitem);
		}
	}
	public int GetCurrentHttpclientnum()
	{
		synchronized (this) {
			return myHttpClientsQueue.size();
		}
	}
	/**
	 * @Author fjsh
	 * @Title initMyhttpclientPool
	 * @Description 初始化myhttpclientpool对象个数
	 * @Return void
	 * @Throws 
	 * @Date 2014年6月18日
	 */
	private  void initMyhttpclientPool()
	{
		for(int i=0;i<initMyhttpclientNum;i++)
		{
			MyHttpClient itemClient=new MyHttpClient();
			myHttpClientsQueue.offer(itemClient);
		}
	}
}
