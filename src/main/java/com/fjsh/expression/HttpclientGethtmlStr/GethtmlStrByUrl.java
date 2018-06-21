package com.fjsh.expression.HttpclientGethtmlStr;

/**
 * @Project JobsPider
 * @Package jobs.HttpclientGethtmlStr
 * @ClassName: GethtmlStrByUrl 
 * @Author fjsh
 * @Description: 操作httpclient及httpclient连接池的接口，直接实现动态访问
 * @Date 2014年6月18日 下午5:13:14 
 */
public class GethtmlStrByUrl {
  public static HttpClientPools Pcp=new HttpClientPools(10);//初始化连接池
  
  /**
 * @Author fjsh
 * @Title GetHtmlStr
 * @Description 通过url地址获取网页文本信息，已经进行同步处理，此类实例化后可以直接使用
 * 返回的数据，第一个是网页内容，第二个参数是网页编码方式
 * @param Url
 * @return
 * @Return String[]
 * @Throws 
 * @Date 2014年6月19日
 */
public   String[] GetHtmlStr(String Url)
  {
	  MyHttpClient tempHttpClient=Pcp.GethttpClientbypool();	 
	  String[] tempStrs= tempHttpClient.getHtml(Url);
	  Pcp.ReleaseHttpClient(tempHttpClient);	
	  return tempStrs;
  }
}
