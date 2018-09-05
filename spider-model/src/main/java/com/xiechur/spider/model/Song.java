package com.xiechur.spider.model;




	
/**
 *
 * song 数据对象.
 *
 */

public class Song implements java.io.Serializable {

	private static final long serialVersionUID = 1535709662707L;

	//主键
	private Integer id;
	/**歌手id*/
	private String singerId;
	/**歌曲id*/
	private String songId;
	/**专辑id*/
	private String albumId;
	/**评论Id*/
	private String commentId;
	/**mp3路径*/
	private String mp3Url;
	
	public  Song() {
	}
	
	public  Song(Integer id) {
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

	public String getSongId() {
		return this.songId;
	}

	public void setSongId(String songId) {
		this.songId = songId;
	}

	public String getAlbumId() {
		return this.albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getCommentId() {
		return this.commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getMp3Url() {
		return this.mp3Url;
	}

	public void setMp3Url(String mp3Url) {
		this.mp3Url = mp3Url;
	}
}