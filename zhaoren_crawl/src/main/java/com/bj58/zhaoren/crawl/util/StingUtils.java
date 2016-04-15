package com.bj58.zhaoren.crawl.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StingUtils {
	public static String extractMobile(String string)
	{
		if(null==string||string.trim().equals(""))
		{
			return "";
		}
		String mobile="";
			Pattern p = Pattern.compile("[0-9-]{5,13}");
			Matcher m1 = p.matcher(string);
			if(m1.find())
				mobile=m1.group(0);
			return mobile;
	}
//	public static void main(String[] args) {
//		System.out.println(StingUtils.extractMobile("129437574"));
//		System.out.println(StingUtils.extractMobile("0534-3446544"));
//	}
}
