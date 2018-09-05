package com.xiechur.spider.service.impl;

import com.xiechur.spider.util.PageControl;
import com.xiechur.spider.dao.SingerDao;
import com.xiechur.spider.model.Singer;
import com.xiechur.spider.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * singer service 实现.
 *
 */
@Service
public class SingerServiceImpl implements SingerService {

    @Autowired
    private SingerDao singerDao;


    @Override
    public boolean update(Singer singer) {
        return singerDao.update(singer);
    }

    @Override
    public List<Singer> list(Singer singer, PageControl page, String orderBy) {
        if (page == null) {
            return singerDao.list(singer, null, null, orderBy);
        }
        return singerDao.list(singer, page.getFromIndex(), page.getPageSize(), orderBy);
    }

    @Override
    public List<Integer> listIds(Singer singer, PageControl page, String orderBy) {
        if (page == null) {
            return singerDao.listIds(singer, null, null, orderBy);
        }
        return singerDao.listIds(singer, page.getFromIndex(), page.getPageSize(), orderBy);
    }

    @Override
    public boolean add(Singer singer) {
        return singerDao.add(singer);
    }

    @Override
    public Singer get(Integer id) {
    	return singerDao.get(id);
    }

    @Override
    public Integer count(Singer singer) {
        return singerDao.count(singer);
    }

	@Override
	public boolean delete(Integer id) {
		return singerDao.delete(id);
	}

	@Override
	public List<Singer> getSingersByIds(List<Integer> ids) {
		return singerDao.getSingersByIds(ids);
	}

    @Override
    public boolean exist(String singerId) {
        Singer query = new Singer();
        query.setSingerId(singerId);
        return count(query) > 0;
    }

    @Override
    public boolean updateBySingerId(Singer singer) {
        return singerDao.updateBySingerId(singer);
    }

}