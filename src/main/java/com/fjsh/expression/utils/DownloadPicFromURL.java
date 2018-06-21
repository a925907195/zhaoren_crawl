package com.fjsh.expression.utils;

import com.fjsh.expression.constant.ManageConstant;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author: <fujiansheng@meituan.com>
 * @Description：
 * @Date: Created in :9/6/2018 2:45 PM
 * @Modified by:
 */
public class DownloadPicFromURL {
    public static void main(String[] args) {
        String url = "https://www.xphonecdn.com/pic/2/2018-05-26/ed560b3645a9ad53e50c2ff3275977c8.jpg";
        String path= ManageConstant.basicPath+"/pic.jpg";
        downloadPicture(url,path);
    }
    //链接url下载图片
    public static void downloadPicture(final String urlList,final String path) {
        URL url = null;
        int retry=1;
        Boolean success=false;
        while (retry<3&&(!success)) {
            try {
                url = new URL(urlList);
                DataInputStream dataInputStream = new DataInputStream(url.openStream());

                FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
                ByteArrayOutputStream output = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int length;

                while ((length = dataInputStream.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                fileOutputStream.write(output.toByteArray());
                dataInputStream.close();
                fileOutputStream.close();
                success=true;
            } catch (FileNotFoundException e){
                break;
            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            retry++;
        }
    }
}
