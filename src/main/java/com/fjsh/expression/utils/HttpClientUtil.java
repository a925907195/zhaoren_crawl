package com.fjsh.expression.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Author: <fujiansheng@meituan.com>
 * @Description：
 * @Date: Created in :16/6/2018 9:42 AM
 * @Modified by:
 */
public class HttpClientUtil {
    public static void testGet(String url)  {
        // TODO Auto-generated constructor stub
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        try {
            HttpResponse response = httpclient.execute(httpget);
            if(response!=null)
            {
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity,"UTF-8");
                System.out.println(strResult);
                EntityUtils.consume(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        httpget.abort();
    }

    public static void main(String[] args) {
        testGet("https://www.1400df.com/pic/2/index_31.html");
    }
}
