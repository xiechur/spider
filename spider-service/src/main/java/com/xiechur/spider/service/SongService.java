package com.xiechur.spider.service;

import java.util.List;

import com.xiechur.spider.model.Song;
import com.xiechur.spider.util.PageControl;

public interface SongService {

    public Song get(Integer id);

    public List<Song> list(Song song, PageControl page, String orderBy);

    public List<Integer> listIds(Song song, PageControl page, String orderBy);

    boolean add(Song song);

    Integer count(Song song);

    boolean update(Song song);
    
    boolean delete(Integer id);
    
	public List<Song> getSongsByIds(List<Integer> ids);
}
