package com.xiechur.spider.model.query;

import java.util.Date;



import com.xiechur.spider.model.Album;
	
/**
 *
 * albumQuery 数据查询对象.
 *
 */

public class AlbumQuery extends Album {

	private static final long serialVersionUID = 1535708717767L;
	
	/**发行日期*/
	private Date startPublishTime;
	private Date endPublishTime;

	public Date getStartPublishTime() {
		return this.startPublishTime;
	}

	public void setStartPublishTime(Date startPublishTime) {
		this.startPublishTime = startPublishTime;
	}
	
	public Date getEndPublishTime() {
		return this.endPublishTime;
	}

	public void setEndPublishTime(Date endPublishTime) {
		this.endPublishTime = endPublishTime;
	}
}