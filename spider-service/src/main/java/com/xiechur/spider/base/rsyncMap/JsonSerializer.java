package com.xiechur.spider.base.rsyncMap;

import com.xiechur.spider.util.ClassUtil;
import com.xiechur.spider.util.JsonUtil;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonSerializer {

	private static final String SPLIT = ".class,";

	public static Object serialize(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof String) {
			return object;
		}
		if (object instanceof Number) {
			return object;
		}
		if (object instanceof Date) {
			return object;
		}
		return JsonSerializer.toJson(object);
	}

	public static String toJson(Object obj) {
		String className = obj.getClass().getName();
		String json = JsonUtil.toJson(obj);
		String str = className + SPLIT + json;
		return str;
	}

	public static Object unserialize(Object object) {
		if (object == null) {
			return null;
		}
		if (!(object instanceof String)) {
			return object;
		}
		String jsonSerialize = (String) object;
		if (isSerialize(jsonSerialize)) {
			return toObject(jsonSerialize);
		}
		else {
			return object;
		}
	}

	private static boolean isSerialize(String jsonSerialize) {
		int index = jsonSerialize.indexOf(SPLIT);
		return index > -1;
	}

	public static Object toObject(String jsonSerialize) {
		if (StringUtils.isEmpty(jsonSerialize)) {
			return null;
		}
		int index = jsonSerialize.indexOf(SPLIT);
		String className = jsonSerialize.substring(0, index);
		String json = jsonSerialize.substring(index + SPLIT.length());
		// System.out.println("className:" + className);
		// System.out.println("json:" + json);
		Class<?> clazz = getClass(className);
		return JsonUtil.toObject(json, clazz);
	}

	private static Map<String, Class<?>> clazzCache = new ConcurrentHashMap<String, Class<?>>();

	protected static Class<?> getClass(String className) {
		Class<?> clazz = clazzCache.get(className);
		if (clazz != null) {
			return clazz;
		}
		clazz = ClassUtil.forName(className);
		clazzCache.put(className, clazz);
		return clazz;
	}
}
