package com.xiechur.spider.model;




	
/**
 *
 * singer 数据对象.
 *
 */

public class Singer implements java.io.Serializable {

	private static final long serialVersionUID = 1535709662695L;

	//主键
	private Integer id;
	/***/
	private String singerId;
	/***/
	private String name;
	/***/
	private String intro;
	/***/
	private String picUrl;
	
	public  Singer() {
	}
	
	public  Singer(Integer id) {
			this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSingerId() {
		return this.singerId;
	}

	public void setSingerId(String singerId) {
		this.singerId = singerId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getPicUrl() {
		return this.picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Override
	public String toString() {
		return "Singer{" +
				"id=" + id +
				", singerId='" + singerId + '\'' +
				", name='" + name + '\'' +
				", intro='" + intro + '\'' +
				", picUrl='" + picUrl + '\'' +
				'}';
	}
}