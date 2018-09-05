package com.xiechur.spider.dao;

import java.util.List;

import com.xiechur.spider.base.data.datasource.jdbc.JdbcBase;
import com.xiechur.spider.model.Album;
public interface AlbumDao extends JdbcBase<Album, Integer> {

    @Override
    public Album get(Integer id);

    public List<Album> list(Album album, Integer skip, Integer max, String orderBy);

    public List<Integer> listIds(Album album, Integer skip, Integer max, String orderBy);

    @Override
    boolean add(Album album);

    Integer count(Album album);

    boolean update(Album album);
    
    boolean delete(Integer id);
    
    public List<Album> getAlbumsByIds(List<Integer> ids);

    boolean exist(String albumId);

    boolean updateByAlbumId(Album album);
}
