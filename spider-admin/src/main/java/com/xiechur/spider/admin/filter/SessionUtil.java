package com.xiechur.spider.admin.filter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

	protected static final Logger logger = LoggerFactory.getLogger(SessionUtil.class);

	private static final int SESSION_TTL = 60 * 60 * 24 * 3;

	/**
	 * 保存参数键值到Session.
	 * 
	 * @param request
	 *            请求
	 * @param name
	 *            参数名
	 * @param value
	 *            参数值
	 */
	public static void setAttribute(HttpServletRequest request, String name, Object value) {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(SESSION_TTL);
		session.setAttribute(name, value);
	}

	public static Object getAttribute(HttpServletRequest request, String name) {
		return request.getSession().getAttribute(name);
	}

	/**
	 * 从Session中获取通行证或yyuid.
	 * 
	 * @param session
	 *            Session
	 * @param accountType
	 *            String.class表示通行证 Long.class表示yyuid
	 * @return
	 */
	public static Object getAccount(HttpSession session, Class<?> accountType) {
		if (accountType.equals(String.class)) {
			return getUsername(session);
		} else if (accountType.equals(Long.class)) {
			Long yyuid = getYyuid(session);
			return yyuid;
		} else {
			throw new IllegalArgumentException("未知数据类型[" + accountType.getName() + "].");
		}
	}

	/**
	 * 从Session获取yyuid.
	 * 
	 * @param session
	 *            Session
	 * @return
	 */
	public static Long getYyuid(HttpSession session) {
		Object value = session.getAttribute(SessionKey.YYUID_SESSION_NAME);
		if (value == null) {
			return null;
		}
		long yyuid = Long.parseLong(value.toString());
		if (yyuid <= 0) {
			return null;
		} else {
			return yyuid;
		}
	}

	/**
	 * 从Session获取通行证.
	 * 
	 * @param session
	 *            Session
	 * @return
	 */
	public static String getUsername(HttpSession session) {
		Object value = session.getAttribute(SessionKey.USERNAME_SESSION_NAME);
		if (StringUtils.isBlank(String.valueOf(value))) {
			return null;
		}
		return (String) value;
	}

	/**
	 * 获取session属性值
	 * 
	 * @param session
	 * @param key
	 * @return
	 */
	public static Object getAttribute(HttpSession session, String key) {
		Object value = session.getAttribute(key);
		if (value == null) {
			return null;
		}
		return value;
	}
}
