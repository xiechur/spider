package com.xiechur.spider.service.impl;

import java.util.Date;
import java.util.List;

import com.xiechur.spider.util.PageControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiechur.spider.dao.SongDao;
import com.xiechur.spider.model.Song;
import com.xiechur.spider.service.SongService;

/**
 *
 * song service 实现.
 *
 */
@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongDao songDao;


    @Override
    public boolean update(Song song) {
        return songDao.update(song);
    }

    @Override
    public List<Song> list(Song song, PageControl page, String orderBy) {
        if (page == null) {
            return songDao.list(song, null, null, orderBy);
        }
        return songDao.list(song, page.getFromIndex(), page.getPageSize(), orderBy);
    }

    @Override
    public List<Integer> listIds(Song song, PageControl page, String orderBy) {
        if (page == null) {
            return songDao.listIds(song, null, null, orderBy);
        }
        return songDao.listIds(song, page.getFromIndex(), page.getPageSize(), orderBy);
    }

    @Override
    public boolean add(Song song) {
        return songDao.add(song);
    }

    @Override
    public Song get(Integer id) {
    	return songDao.get(id);
    }

    @Override
    public Integer count(Song song) {
        return songDao.count(song);
    }

	@Override
	public boolean delete(Integer id) {
		return songDao.delete(id);
	}

	@Override
	public List<Song> getSongsByIds(List<Integer> ids) {
		return songDao.getSongsByIds(ids);
	}

}