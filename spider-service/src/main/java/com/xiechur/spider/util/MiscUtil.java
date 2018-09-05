package com.xiechur.spider.util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiscUtil {
	public static final String STRING_EMPTY = "";
	private static final String PATH_SEPARATOR = "/";
	
	public static Integer parseInt(String s){
		if(s!=null){
			try{
				return Integer.parseInt(s);
			}catch(Exception e){}
		}
		
		return null;
	}
	
	public static Integer parseInt(String s, Integer defaultValue){
		if(s!=null){
			try{
				return Integer.parseInt(s);
			}catch(Exception e){
				return defaultValue;
			}
		}
		
		return null;
	}
	public static String filter(String s){
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(s);
		return m.replaceAll("").trim();
	}
	public static Integer parseIntWithDefaultValue(String s, Integer defaultValue){
		if(s!=null){
			try{
				return Integer.parseInt(s);
			}catch(Exception e){
				return defaultValue;
			}
		}
		
		return defaultValue;
	}
	
	public static Long parseLongWithDefaultValue(String s, Long defaultValue){
		if(s!=null){
			try{
				return Long.parseLong(s);
			}catch(Exception e){
				return defaultValue;
			}
		}
		
		return defaultValue;
	}
	
	
	
	
	public static Long parseLong(String s){
		if(s!=null){
			try{
				return Long.parseLong(s);
			}catch(Exception e){}
		}
		
		return null;
	}
	
	
	public static Long parseLong(String s, Long defaultValue){
		if(s!=null){
			try{
				return Long.parseLong(s);
			}catch(Exception e){
				return defaultValue;
			}
		}
		
		return null;
	}
	
	public static Float parseFloat(String s){
		if(s!=null){
			try{
				return Float.parseFloat(s);
			}catch(Exception e){}
		}
		
		return null;
	}
	
	public static Boolean parseBoolean(String s){
		if(s!=null){
			try{
				return Boolean.parseBoolean(s);
			}catch(Exception e){}
		}
		
		return null;
	}
	
	public static String checkNull(String s){
		if(s==null){
			return STRING_EMPTY;
		}
		
		return s;
	}
	
	public static String getFileNameFromUrl(String url){
		if(StringUtils.isBlank(url)){
			return null;
		}
		
		String fn = url;
		int p = fn.indexOf("?");
		if(p>=0){
			fn = fn.substring(0,p);
		}
		
		p = fn.lastIndexOf("/");
		if(p>=0){
			fn = fn.substring(p+1,fn.length());
		}
		
		p = fn.lastIndexOf("\\");
		if(p>=0){
			fn = fn.substring(p+1,fn.length());
		}
				
		return fn;
	}
	
	public static String getFileExt(String url){
		String fn = getFileNameFromUrl(url);
		
		String ext = "tmp";
		int p = fn.lastIndexOf(".");
		if(p>=0){
			ext = fn.substring(p+1,fn.length());
		}
		return ext;
	}
	
	public static String getFileExt(String url,String defaultExt){
		String fn = getFileNameFromUrl(url);
		
		String ext = null;
		int p = fn.lastIndexOf(".");
		if(p>=0){
			ext = fn.substring(p+1,fn.length());
		}else{
			ext = defaultExt;
		}
		
		if(StringUtils.isEmpty(ext)){
			ext = "tmp";
		}
		
		return ext;
	}
	
	/**
	 * 获取本机所有IP
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> getAllLocalHostIP(){
        List<String> res=new ArrayList<String>();
        Enumeration netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration nii=ni.getInetAddresses();
                while(nii.hasMoreElements()){
                    ip = (InetAddress) nii.nextElement();
                    if (ip.getHostAddress().indexOf(":") < 0 && !ip.getHostAddress().startsWith("127")) {
                        res.add(ip.getHostAddress());
                        System.out.println("本机的ip=" + ip.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return res;
    }
	
	/**
	 * 保存输入流到文件系统
	 * @param path
	 * @param is
	 */
	public static void saveFile(String path, InputStream is) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(is);

			File file = new File(path);
			File dir = new File(file.getParent());
			if (dir != null && !dir.exists()) {
				dir.mkdirs();
			}
			if (file != null && !file.exists()) {
				file.createNewFile();
			}

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(path));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
		
	}

	public static String checkDirPath(String s) {
		if(StringUtils.isBlank(s)){
			return s;
		}
		
		if(s.endsWith("/") || s.endsWith("\\")){
			return s;
		}
		
		return s + PATH_SEPARATOR;
	}
	
	private static final String NUMBER_STRING = "0123456789";
	public static String randomNumberString(int length){
		if(length <=0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		
		for(int i=0 ; i<length ; i++){
			sb.append(NUMBER_STRING.charAt((int)(Math.random()*10)));
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args){
//		getAllLocalHostIP();
		
		/*
		String url = "http://111/222/logo.jpg?t=11122";
		
		String ext = MiscUtil.getFileExt(url);
		System.out.println(ext);
		*/
		
//		System.out.println(randomNumberString(7));
//		System.out.println(randomNumberString(3));
//		System.out.println(randomNumberString(5));
//		System.out.println(randomNumberString(4));
//		System.out.println(randomNumberString(8));
	}

}

