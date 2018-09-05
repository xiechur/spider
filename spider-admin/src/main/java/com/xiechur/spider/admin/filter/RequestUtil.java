package com.xiechur.spider.admin.filter;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.regex.Matcher;

public class RequestUtil {

	private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);

	public static String getRequestContextUri(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String requestURI;
		if ("/".equals(contextPath)) {
			requestURI = request.getRequestURI();
		}
		else {
			String uri = request.getRequestURI();
			requestURI = uri.substring(contextPath.length());
		}
		if (requestURI.indexOf("//") != -1) {
			requestURI = requestURI.replaceAll("/+", "/");
		}
		return requestURI;
	}

	/**
	 * 获取上传的文件.
	 * 
	 * @param request
	 *            请求
	 * @param name
	 *            文件名
	 * @return 文件
	 */
	public static MultipartFile getFile(HttpServletRequest request, String name) {
		// try {
		MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
		MultipartFile file = mrequest.getFile(name); // 发送对象
		if (file == null || file.isEmpty()) {
			return null;
		}
		return file;
	}

	/**
	 * 获取域名.
	 * 
	 * @param request
	 * @return
	 */
	public static String getDomain(HttpServletRequest request) {
		String domain = "http://" + request.getServerName();
		return domain;
	}

	/**
	 * 获取user-agent.
	 */
	public static String getUserAgent(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		return userAgent;
	}

	/**
	 * 获取代理服务器IP.
	 * 
	 * @param request
	 * @return
	 */
	public static String getProxyIp(HttpServletRequest request) {
		String proxyIp = request.getHeader("X-Real-IP");
		if (proxyIp == null) {
			proxyIp = request.getHeader("RealIP");
		}
		if (proxyIp == null) {
			proxyIp = request.getRemoteAddr();
		}
		return proxyIp;
	}

	/**
	 * 从cookie获取用户通行证.
	 * 
	 * @param request
	 * @return
	 */
//	public static String getCookieUsername(HttpServletRequest request) {
//		String username = CookieUtil.getCookie("username", request);
//		username = urlDecode(username);
//		return username;
//	}

	/**
	 * 把null或无效的页码转成1.
	 * 
	 * @param pageid
	 * @return
	 */
	public static int getPageid(Integer pageid) {
		if (pageid == null || pageid <= 0) {
			return 1;
		}
		return pageid;
	}

	/**
	 * 从请求中获取页码.
	 * 
	 * @param request
	 * @return
	 */
	public static int getPageid(HttpServletRequest request) {
		int pageid = NumberUtils.toInt(request.getParameter("page"));
		if (pageid <= 0) {
			return 1;
		}
		return pageid;
	}

	/**
	 * 打印header信息.
	 * 
	 * @param request
	 */
	public static void printHeaders(HttpServletRequest request) {
		Enumeration<String> e = request.getHeaderNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement();
			String value = request.getHeader(name);
			logger.info(name + ":" + value);
		}
	}

	private static final java.util.regex.Pattern IS_LICIT_IP_PATTERN = java.util.regex.Pattern.compile("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$");

	/**
	 * 判断IP是否合法.
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean isLicitIp(final String ip) {
		if (StringUtils.isEmpty(ip)) {
			return false;
		}
		Matcher m = IS_LICIT_IP_PATTERN.matcher(ip);
		return m.find();
		// return false;
		// }
		// return true;
	}

	/**
	 * 打印请求中的对象.
	 * 
	 * @param request
	 */
	public static void printAttributes(HttpServletRequest request) {
		Enumeration<String> e = request.getAttributeNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement();
			Object value = request.getAttribute(name);
			logger.info(name + ":" + value);
		}
	}

	/**
	 * 从Session中获取帐号.
	 * 
	 * @param request
	 * @return
	 */
	public static String getSessionUsername(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute(SessionKey.USERNAME_SESSION_NAME);
		return username;
	}

	/**
	 * 从Session中获取帐号.
	 * 
	 * @param session
	 * @return
	 */
	public static String getSessionUsername(HttpSession session) {
		String username = (String) session.getAttribute(SessionKey.USERNAME_SESSION_NAME);
		return username;
	}

	/**
	 * 获取上次访问的地址.
	 * 
	 * @param request
	 * @return
	 */
	public static String getReferer(HttpServletRequest request) {
		return request.getHeader("referer");
	}

	/**
	 * 获取请求参数的值，若不存在则返回默认值.
	 * 
	 * @param request
	 * @param parameterName
	 * @param defaultValue
	 * @return
	 */
	public static String getString(HttpServletRequest request, String parameterName, String defaultValue) {
		String value = request.getParameter(parameterName);
		return StringUtils.isEmpty(value) ? defaultValue : value;
	}

	// public static void test() {
	// logger.info("test");
	// }

	// public static void main(String[] args) {
	// String str = "192.168.0.1,";
	// int index = str.lastIndexOf(',');
	// String sss = str.substring(index + 1).trim();
	// System.out.println(index + "::" + sss);
	// }

	/**
	 * URL解码</br>
	 * 
	 * @param str
	 *            需要解码的内容
	 * @return 解码后的内容
	 */
	public static String urlDecode(String str) {
		if (str == null) {
			return null;
		}
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.info(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}


}
