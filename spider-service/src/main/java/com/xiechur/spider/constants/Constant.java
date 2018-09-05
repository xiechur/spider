package com.xiechur.spider.constants;

public class Constant {
	public static final String MQ_SINGER_ID = "mq_singer_id";
	public static final String SINGER_DETAIL ="https://music.163.com/artist/desc?id=%s";
	public static final String ALBUM_LIST ="https://music.163.com/artist/album?id=%s&limit=100&offset=0";
	public static final String ALBUM_DETAIL ="https://music.163.com/album?id=%s";
	public static final String SONG_DETAIL = "https://music.163.com/song?id=%s";



	public static final String API_URL_PREFIX = "/api";
	
	public static final String API_VERSION_V1 = "v1";
	public static final String API_VERSION_V2 = "v2";
	public static final String API_VERSION_V3 = "v3";
	public static final String API_VERSION_V4 = "v4";
	public static final String API_VERSION_V5 = "v5";
	public static final String API_VERSION_V6 = "v6";

	
	// 请求返回编码统一定义管理
	public static enum RESULT_CODE_MSG {
		
		/*CODE_200(200, "请求处理成功"), CODE_201(201, "用户首次登录成功"), 
		CODE_400(400, "用户操作错误"), CODE_401(401, "没有用户登录，请重新登录"), CODE_404(404, "找不到用户"), CODE_405(405, "用户被禁止"), CODE_406(406, "用户设备被禁止"),
		CODE_407(407, "请求不合法"), CODE_408(408, "请求超过时限"), CODE_409(409, "请求重复"), CODE_500(500, "服务不可用"), CODE_5000(5000, "第三方服务不可用"), 
		CODE_300(300, "请求处理失败"), CODE_301(301, "请求传递的参数错误"), CODE_302(302, "程序运行错误"), CODE_303(303, "验证码错误"), CODE_304(304, "参数转换错误"), CODE_305(305, "业务处理失败");*/

		/**
		 * 成功code
		 */
		CODE_200(200, "Request succeeded"),
		CODE_201(201, "Request succeeded. It's a new one"),

		/**
		 * 失败code
		 */
		CODE_400(400, "Invalid attempt"),
		CODE_401(401, "No login user"),
		CODE_404(404, "can't be found"),
		CODE_405(405, "User is banned"),
		CODE_406(406, "User's device is banned"),
		CODE_407(407, "Invalid request"),
		CODE_408(408, "Request time out"),
		CODE_409(409, "Login failed"),
		CODE_300(300, "Request failed"),
		CODE_301(301, "Error request data"),
		CODE_302(302, "Error loading"),
		CODE_303(303, "Invalid verify code"),
		CODE_304(304, "Token is null"),
		CODE_305(305, "Token verify fail"),
		CODE_306(306, "Only one time"),
		CODE_307(307, "invalid parameters"),
		
		CODE_500(500, "Server Error"),

		/**
		 * 业务code
		 */
		CODE_1001(1001, "6 characters min"),
		CODE_1002(1002, "20 characters max"),
		CODE_1003(1003, "Only numbers, letters and _"),
		CODE_1004(1004, "User name existed"),
		CODE_1005(1005, "you can only modify it once within latest 30 days"),
		CODE_1006(1006, "You have blocked him/her. You can't follow him/her until you unblock him/her."), // 你已拉黑对方，解除后才能关注。
		CODE_1007(1007, "You have blocked him/her."), // 你已拉黑对方。
		CODE_1008(1008, "He/she has blocked you, and you could not follow him/her"), // 对方已将你拉黑，无法关注。
		CODE_1009(1009, "Please input nickname"),
		CODE_1010(1010, "Bao gồm ký tự đặc biệt"), // 包含特殊字符	 Contains special characters
		CODE_1011(1011, "10 characters min"),
        CODE_1012(1012, "3000 characters max"),
        CODE_1013(1013, "You have entered sensitive content. Please edit it."),
		
		CODE_2001(2001, "Bình luận quá nhiều lần, xin chờ một lát để bình luận tiếp "), //Comment too frequently. Please continue later 评论太频繁，请稍候再继续
		CODE_2002(2002, "Số ký tự bình luận không thể vượt quá "), // 评论内容字符个数不能超过 Comment characters can not be more than
		CODE_2003(2003, "Số ký tự bình luận không thể ít hơn "), // 评论内容字符不能少于 Comment characters can not be less than
		
		CODE_3001(3001, "Khoảng cách gửi ý kiến phản hồi là 10 phút "), // 意见反馈的发送间隔时间为10分钟 Entered the feedback too many times. Try again 10 minutes later
		
		CODE_999(999, "");
		
		private Integer code;
		private String msg;
		
		RESULT_CODE_MSG (Integer code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public Integer getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}
	}
}
