package com.xiechur.spider.dao;

import java.util.List;

import com.xiechur.spider.base.data.datasource.jdbc.JdbcBase;
import com.xiechur.spider.model.Singer;

public interface SingerDao extends JdbcBase<Singer, Integer> {

    @Override
    public Singer get(Integer id);

    public List<Singer> list(Singer singer, Integer skip, Integer max, String orderBy);

    public List<Integer> listIds(Singer singer, Integer skip, Integer max, String orderBy);

    @Override
    boolean add(Singer singer);

    Integer count(Singer singer);

    boolean update(Singer singer);

    boolean updateBySingerId(Singer singer);

    boolean delete(Integer id);
    
    public List<Singer> getSingersByIds(List<Integer> ids);
}
