package com.xiechur.spider.base.data.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ResourceUtil {

	private static Logger logger= LoggerFactory.getLogger(ResourceUtil.class);
	private static Map<String, ResourceBundle> resourceBundles = new HashMap<String, ResourceBundle>();

	public static String getValue(String language, ItemEnum item) {
		ResourceBundle rb = getInstance(language);
		String value = rb.getString(item.getKey());
		return StringUtils.isNotBlank(value) ? value : item.defaultValue;
	}
	
	public static String getValue(String language, ItemEnum item, Object[] objs) {
		String value = getValue(language, item);
		if (StringUtils.isNotBlank(value)) {
			return MessageFormat.format(value, objs);
		} else {
			return null;
		}
	}

	/**
	 * 单例模式.
	 * @param converterType
	 * @return
	 */
	private static ResourceBundle getInstance(String language) {
		if (StringUtils.isNotBlank(language)) {
			if (resourceBundles.get(language) == null) {
				synchronized (ResourceUtil.class) {
					if (resourceBundles.get(language) == null) {
						Locale locale = new Locale(language);
						logger.info("locale language : {}, country : {}, variant : {}", locale.getLanguage(), locale.getCountry(), locale.getVariant());
						ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
						if (rb == null) {
							rb = getDefault();
						}
						resourceBundles.put(language, rb);
					}
				}
			}
			return resourceBundles.get(language);
		}

		return null;
	}
	
	private static ResourceBundle getDefault() {
		Locale locale = Locale.ENGLISH;
		return ResourceBundle.getBundle("messages", locale);
	}

	public enum ItemEnum {
		games("10000", "Games","游戏条目"), users("10001", "Friends", "用户条目"),
		
		/**
		 * 成功code
		 */
		CODE_200("200", "Request succeeded", "请求成功"),
		CODE_201("201", "Login successfully", "登陆成功"),

		/**
		 * 失败code
		 */
		CODE_400("400", "Invalid attempt", "无效尝试"),
		CODE_401("401", "No login user", "没用户登陆"),
		CODE_404("404", "can't be found", "找不到"),
		CODE_405("405", "User is banned", "用户被禁止"),
		CODE_406("406", "User's device is banned", "用户设备被禁止"),
		CODE_407("407", "Invalid request", "无效请求"),
		CODE_408("408", "Request time out", "请求超时"),
		CODE_409("409", "Login failed", "登陆失败"),
		CODE_300("300", "Request failed", "请求失败"),
		CODE_301("301", "Error request data", "请求的数据错误"),
		CODE_302("302", "Error loading", "加载错误"),
		CODE_303("303", "Invalid verify code", "校验码无效"),
		CODE_304("304", "Token is null", "token为空"),
		CODE_305("305", "Token verify fail", "登陆校验失败"),
		CODE_306("306", "Only one time", "不允许重复操作"),
		CODE_307("307", "invalid parameters", "参数无效"),
		
		CODE_500("500", "Server Error", "服务错误"),

		/**
		 * 业务code
		 */
		CODE_1001("1001", "6 characters min", "最少6个字符"),
		CODE_1002("1002", "20 characters max", "最大20个字符"),
		CODE_1003("1003", "Only numbers, letters and _", "只允许数字字母和下划线"),
		CODE_1004("1004", "User name existed", "用户名称已存在"),
		CODE_1005("1005", "you can only modify it once within latest 30 days", "30天内只能更改一次"),
		CODE_1006("1006", "You have blocked him/her. You can't follow him/her until you unblock him/her.", "你已拉黑对方，解除后才能关注。"),
		CODE_1007("1007", "You have blocked him/her.", "你已拉黑对方。"),
		CODE_1008("1008", "He/she has blocked you, and you could not follow him/her", "对方已将你拉黑，无法关注。"),
		CODE_1009("1009", "Please input nickname", "请输入昵称"),
		CODE_1010("1010", "Bao gồm ký tự đặc biệt", "包含特殊字符"),
		CODE_1011("1011", "10 characters min", "最少10个字符"),
        CODE_1012("1012", "3000 characters max", "最大3000个字符"),
        CODE_1013("1013", "You have entered sensitive content. Please edit it.", "你的输入包含敏感内容，请重新输入"),
		
		CODE_2001("2001", "Bình luận quá nhiều lần, xin chờ một lát để bình luận tiếp ", "评论太频繁，请稍候再继续"),
		CODE_2002("2002", "Số ký tự bình luận không thể vượt quá ", "评论内容字符个数不能超过"),
		CODE_2003("2003", "Số ký tự bình luận không thể ít hơn ", "评论内容字符不能少于"),
	    
	    CODE_3001("3001", "Khoảng cách gửi ý kiến phản hồi là 10 phút ", "意见反馈的发送间隔时间为10分钟");
		
		private String key;
		private String defaultValue;
		private String desc;
		
		ItemEnum(String key, String defaultValue, String desc) {
			this.key = key;
			this.defaultValue = defaultValue;
			this.desc = desc;
		}
	
		public String getKey() {
			return key;
		}
	
		public String getDesc() {
			return desc;
		}
		
		public ItemEnum find(String key) {
			ItemEnum item = null;
			for (ItemEnum i : values()) {
				if (i.key.equals(key)) {
					item = i;
					break;
				}
			}
			return item;
		}
	}
	
	public static void main(String[] args) {
		String v = getValue("en", ItemEnum.CODE_200);
		System.out.println(v);
	}
}
