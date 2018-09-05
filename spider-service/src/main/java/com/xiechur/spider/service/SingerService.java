package com.xiechur.spider.service;

import java.util.List;

import com.xiechur.spider.model.Singer;
import com.xiechur.spider.util.PageControl;

public interface SingerService {

    public Singer get(Integer id);

    public List<Singer> list(Singer singer, PageControl page, String orderBy);

    public List<Integer> listIds(Singer singer, PageControl page, String orderBy);

    boolean add(Singer singer);

    Integer count(Singer singer);

    boolean update(Singer singer);
    
    boolean delete(Integer id);
    
	public List<Singer> getSingersByIds(List<Integer> ids);

    boolean exist(String singerId);

    boolean updateBySingerId(Singer singer);
}
