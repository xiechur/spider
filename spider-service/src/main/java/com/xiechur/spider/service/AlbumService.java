package com.xiechur.spider.service;

import com.xiechur.spider.model.Album;
import com.xiechur.spider.util.PageControl;

import java.util.List;

public interface AlbumService {

    public Album get(Integer id);

    public List<Album> list(Album album, PageControl page, String orderBy);

    public List<Integer> listIds(Album album, PageControl page, String orderBy);

    boolean add(Album album);

    Integer count(Album album);

    boolean update(Album album);
    
    boolean delete(Integer id);
    
	public List<Album> getAlbumsByIds(List<Integer> ids);

	boolean exist(String albumId);

	boolean updateByAlbumId(Album album);
}
