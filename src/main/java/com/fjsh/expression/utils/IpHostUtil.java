package com.fjsh.expression.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: <fujiansheng@meituan.com> @Description：
 * @Date: Created in :16/11/2017 12:46 PM
 * @Modified by:
 */
public class IpHostUtil {
	/**
	 *简单ip地址提取，数据来源于程序从远程自动获取的ip以及机器名称
	 * @param content
	 * @return
	 */
	public static String getHostIp(String content) {
        String regex = "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";
        Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		if (m.find()) {
			return m.group(0);
		}
		return content;

	}
}
