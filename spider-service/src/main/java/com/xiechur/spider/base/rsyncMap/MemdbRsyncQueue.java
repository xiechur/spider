package com.xiechur.spider.base.rsyncMap;

public interface MemdbRsyncQueue {
	
	public static final String TYPE_SET = "set";
	public static final String TYPE_REMOVE = "remove";

	boolean add(String type, String key, String value, boolean isMySelf);
}
