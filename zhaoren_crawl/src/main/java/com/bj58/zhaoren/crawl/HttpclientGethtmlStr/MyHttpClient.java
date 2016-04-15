package com.bj58.zhaoren.crawl.HttpclientGethtmlStr;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

/**
 * @Project JobsPider
 * @Package jobs.HttpclientGethtmlStr
 * @ClassName: MyHttpClient 
 * @Author fjsh
 * @Description: 通过httpclient的get方法根据url获取内容信息
 * @Date 2014年6月18日 下午4:31:43 
 */
public class MyHttpClient {
	private static String[] user_agent={"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Trident/4.0)","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0)","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0)","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0)"};
	 
	/**
	 * @Author fjsh
	 * @Title getHtml
	 * @Description 根据url返回对应的内容信息
	 * @param args url地址信息
	 * @return
	 * @Return String[]
	 * @Throws 
	 * @Date 2014年6月18日
	 */
	public  String[] getHtml(String args){
		try{		   
		   HttpClient httpclient = new DefaultHttpClient();    
	       HttpGet httpget = new HttpGet(args);  
	       httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,20000);
	       httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,20000);
  	       httpget.setHeader("User-Agent", user_agent[new Random().nextInt(5)]);  
	       httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");  
	       httpget.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");  
	       HttpResponse response = httpclient.execute(httpget);  
	       // 判断页面返回状态判断是否进行转向抓取新链接  
	       int statusCode = response.getStatusLine().getStatusCode(); 
	      // System.out.println("状态码：：：："+statusCode);  
	       HttpEntity entity = response.getEntity();  // 查看所有返回头部信息  
	       Header headers[] = response.getAllHeaders();  
	       int ii = 0;  
	       while (ii < headers.length) {  
	       //  System.out.println(headers[ii].getName() + ": " + headers[ii].getValue());  
	         ++ii;  
	       } 
	       byte[] bytes = null;
	       String charSet =null;
	       if (entity != null) {  
	    	  bytes = EntityUtils.toByteArray(entity);
	          charSet = EntityUtils.getContentCharSet(entity);  // 如果头部Content-Type中包含了编码信息，那么我们可以直接在此处获取   
	          if(charSet==null||charSet.length()<=0){  
		            String regEx = "(?=<meta).*?(?<=charset=[\\'|\\\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";  
		            Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);  
		            Matcher m = p.matcher(new String(bytes));   // 默认编码转成字符串，因为我们的匹配中无中文，所以串中可能的乱码对我们没有影响  
		            boolean result = m.find();
		            if (result) {  
		                charSet = m.group(1); 
		            }
		            else{
		            	charSet ="utf-8";
		            }
	          }  
	       }
	       httpclient.getConnectionManager().shutdown();
	       if(bytes!=null){
	    	   String[] strs = new String[2];	    	 
	    	   strs[0]=new String(bytes,charSet);
	    	   strs[1]=charSet;
	    	  // System.out.println(strs[0]);
	    	  // System.out.println(strs[1]);
	    	   return strs;
	       }
	       else{
	    	  // System.out.println("null");
	    	   return null;
	       }
	     }catch(Exception e){
	    	 e.printStackTrace();
	    	 return null;
	     }       
	}
}
