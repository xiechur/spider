package com.xiechur.spider.admin.constants;

public class Constant {
	public static final Integer PAGE_SIZE = 15;


	public static enum RESULT_CODE_MSG {
		CODE_200(200, "请求处理成功"), CODE_201(201, "用户首次登录成功"),
		CODE_400(400, "用户操作错误"), CODE_401(401, "没有用户登录"), CODE_404(404, "找不到用户"), CODE_405(405, "用户被禁止"), CODE_406(406, "用户设备被禁止"),
		CODE_300(300, "请求处理失败"), CODE_301(301, "请求传递的参数错误"), CODE_302(302, "程序运行错误"), CODE_303(303, "验证码错误");
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
