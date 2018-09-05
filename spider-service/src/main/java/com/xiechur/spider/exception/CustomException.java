package com.xiechur.spider.exception;

/**
 * 自定义异常
 */
public class CustomException extends RuntimeException {
	
	private static final long serialVersionUID = 4675141924739446981L;

	/**
	 * 错误码
	 */
	protected int code;

	/**
	 * 错误信息
	 */
	protected String msg;

	public CustomException(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public CustomException(int code, String msg, Throwable t) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public CustomException(String message) {
		super(message);
		this.msg = message;
	}

	public CustomException(String message, Throwable t) {
		super(message, t);
		this.msg = message;
	}

	public CustomException() {
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "CustomException [code=" + code + ", msg=" + msg + "]";
	}
}
