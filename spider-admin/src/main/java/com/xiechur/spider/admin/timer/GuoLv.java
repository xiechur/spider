package com.xiechur.spider.admin.timer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuoLv {

	public static void main(String[] args) {

		String a = "@#￥…20140913×（&！";
		filter1(a);
		filter2(a);
	}
	
	public static void  filter1(String s){
		String regEx  = "[0-9]+" ;
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(s);
		while(m.find()){
	     System.out.println( m.group());
		}
	}
	
	public static void filter2(String s){
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(s);
		System.out.println(m.replaceAll("").trim());
	}
}