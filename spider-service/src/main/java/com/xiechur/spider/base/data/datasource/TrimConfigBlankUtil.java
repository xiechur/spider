package com.xiechur.spider.base.data.datasource;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class TrimConfigBlankUtil {
	
	public static void removeBlank(Map<String, Object> configProperties){
		if(configProperties == null){
			return;
		}
		for(Map.Entry<String, Object> entry : configProperties.entrySet()){
			if(entry.getValue() != null && entry.getValue().getClass() == String.class){
				configProperties.put(entry.getKey(), StringUtils.trim(entry.getValue().toString()));
			}
		}
	}

}
