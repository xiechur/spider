package com.xiechur.spider.service.impl;

import java.util.Date;
import java.util.List;

import com.xiechur.spider.util.PageControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiechur.spider.dao.AlbumDao;
import com.xiechur.spider.model.Album;
import com.xiechur.spider.service.AlbumService;

/**
 *
 * album service 实现.
 *
 */
@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;


    @Override
    public boolean update(Album album) {
        return albumDao.update(album);
    }

    @Override
    public List<Album> list(Album album, PageControl page, String orderBy) {
        if (page == null) {
            return albumDao.list(album, null, null, orderBy);
        }
        return albumDao.list(album, page.getFromIndex(), page.getPageSize(), orderBy);
    }

    @Override
    public List<Integer> listIds(Album album, PageControl page, String orderBy) {
        if (page == null) {
            return albumDao.listIds(album, null, null, orderBy);
        }
        return albumDao.listIds(album, page.getFromIndex(), page.getPageSize(), orderBy);
    }

    @Override
    public boolean add(Album album) {
        return albumDao.add(album);
    }

    @Override
    public Album get(Integer id) {
    	return albumDao.get(id);
    }

    @Override
    public Integer count(Album album) {
        return albumDao.count(album);
    }

	@Override
	public boolean delete(Integer id) {
		return albumDao.delete(id);
	}

	@Override
	public List<Album> getAlbumsByIds(List<Integer> ids) {
		return albumDao.getAlbumsByIds(ids);
	}

    @Override
    public boolean exist(String albumId) {
        return albumDao.exist(albumId);
    }

    @Override
    public boolean updateByAlbumId(Album album) {
        return albumDao.updateByAlbumId(album);
    }


}