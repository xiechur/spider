package com.xiechur.spider.dao;

import java.util.List;

import com.xiechur.spider.base.data.datasource.jdbc.JdbcBase;
import com.xiechur.spider.model.Song;

public interface SongDao extends JdbcBase<Song, Integer> {

    @Override
    public Song get(Integer id);

    public List<Song> list(Song song, Integer skip, Integer max, String orderBy);

    public List<Integer> listIds(Song song, Integer skip, Integer max, String orderBy);

    @Override
    boolean add(Song song);

    Integer count(Song song);

    boolean update(Song song);
    
    boolean delete(Integer id);
    
    public List<Song> getSongsByIds(List<Integer> ids);
}
