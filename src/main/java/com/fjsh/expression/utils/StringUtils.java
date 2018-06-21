package com.fjsh.expression.utils;

import org.htmlparser.tags.LinkTag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
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
	public static String deleteTime(String value){
		Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}[ ][0-9]{1,2}[:][0-9]{1,2}[:][0-9]{1,2}");
		Matcher matcher = pattern.matcher(value);

		String dateStr = null;
		if(matcher.find()){
			dateStr = matcher.group(0);
		}
		if(null!=dateStr){
			value=value.replaceAll(dateStr,"");
		}
		return value;
	}
	public static void main(String[] args) {
//		System.out.println(deleteTime("2016-11-22 12:32:23我是地球人"));
		System.out.println(deleteTime("下一頁"));
	}
}
