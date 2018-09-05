package com.xiechur.spider.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewUtil {
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_XML = "application/xml";
	
	public static String page(HttpServletRequest request, String page) {
		return page;
	}
	
	public static String redirect(HttpServletRequest request, String url) {
		return "redirect:" + url;
	}

	public static String forward(HttpServletRequest request, String url) {
		return "forward:" + url;
	}
	
	public static String sendContent(HttpServletRequest request, HttpServletResponse response, String content) throws IOException  {
		return sendContent(request,response,content,"text/html");
	}
	
	public static String sendContent(HttpServletRequest request, HttpServletResponse response,
			String content,String contentType) throws IOException  {
		response.setContentType(contentType);
		response.setCharacterEncoding("UTF-8");
		
		response.getWriter().write(content);
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	public static String sendJSON(HttpServletRequest request, HttpServletResponse response,
			String content) throws IOException  {
		
		return sendContent(request,response,content,CONTENT_TYPE_JSON);
	}
	
	public static String sendXML(HttpServletRequest request, HttpServletResponse response,
			String content) throws IOException  {
		
		return sendContent(request,response,content,CONTENT_TYPE_XML);
	}
	
}
