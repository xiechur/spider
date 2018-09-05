package com.xiechur.spider.model;

import java.util.Date;



	
/**
 *
 * album 数据对象.
 *
 */

public class Album implements java.io.Serializable {

	private static final long serialVersionUID = 1535709662493L;

	//主键
	private Integer id;
	/**专辑id*/
	private String albumId;
	/**歌手id*/
	private String singerId;
	/**评论Id*/
	private String commentId;
	/**专辑图片*/
	private String picUrl;
	/**专辑名称*/
	private String name;
	/**发行日期*/
	private Date publishTime;
	/**发行公司*/
	private String publishCompany;
	/**简介*/
	private String introdution;
	
	public  Album() {
	}
	
	public  Album(Integer id) {
			this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAlbumId() {
		return this.albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getSingerId() {
		return this.singerId;
	}

	public void setSingerId(String singerId) {
		this.singerId = singerId;
	}

	public String getCommentId() {
		return this.commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getPicUrl() {
		return this.picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getPublishCompany() {
		return this.publishCompany;
	}

	public void setPublishCompany(String publishCompany) {
		this.publishCompany = publishCompany;
	}

	public String getIntrodution() {
		return this.introdution;
	}

	public void setIntrodution(String introdution) {
		this.introdution = introdution;
	}

	@Override
	public String toString() {
		return "Album{" +
				"id=" + id +
				", albumId='" + albumId + '\'' +
				", singerId='" + singerId + '\'' +
				", commentId='" + commentId + '\'' +
				", picUrl='" + picUrl + '\'' +
				", name='" + name + '\'' +
				", publishTime=" + publishTime +
				", publishCompany='" + publishCompany + '\'' +
				", introdution='" + introdution + '\'' +
				'}';
	}
}