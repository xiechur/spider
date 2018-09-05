package com.xiechur.spider.dao.mysql;

import java.util.Date;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xiechur.spider.dao.SongDao;
import com.xiechur.spider.model.Song;
import com.xiechur.spider.model.query.SongQuery;
import com.xiechur.spider.base.data.datasource.jdbc.DynamicQuery;
import com.xiechur.spider.base.data.datasource.jdbc.Jdbc;
import com.xiechur.spider.base.data.datasource.jdbc.Parameter;
import com.xiechur.spider.base.data.datasource.jdbc.StatementParameter;
import com.xiechur.spider.base.data.datasource.jdbc.builder.InsertBuilder;
import com.xiechur.spider.base.data.datasource.jdbc.builder.UpdateBuilder;
import com.xiechur.spider.base.data.datasource.jdbc.builder.AbstractSqlBuilder;
import com.xiechur.spider.util.DateUtil;

@Repository
public class SongDaoMysqlImpl implements SongDao {
    @Autowired
    @Qualifier(value="mainJdbc")
    private Jdbc jdbc;

    @Override
    public Song get(Integer id) {
        String sql = "SELECT * FROM song WHERE id =? AND TRUE ";
        StatementParameter param = new StatementParameter();
        param.setInt(id);
        return jdbc.query(sql, Song.class, param);
    }

    @Override
    public boolean add(Song song) {
        InsertBuilder builder = new InsertBuilder("song", true);
		
		builderUpdate(song, builder);
		
		Long id = jdbc.insertForLastId(builder.getSql(), builder.getParam());
        song.setId(id.intValue());
    
		return id > 0;
    }

    @Override
    public List<Song> list(Song song, Integer skip, Integer max, String orderBy) {
        StringBuilder sqlSb = new StringBuilder();
        String sql = "SELECT * FROM song WHERE TRUE  ";
        DynamicQuery query = new DynamicQuery();
        StatementParameter param = new StatementParameter();
        
    	builderQuery(song, query, param);
		
        query.setBaseSql(sql);
        sql = query.generateSql();
        sqlSb.append(sql);
        if (!StringUtils.isEmpty(orderBy)) {
            sqlSb.append(" order by ").append(orderBy);
        }
        if (skip == null || max == null) {
            return jdbc.queryForList(sqlSb.toString(), Song.class, param);
        }
        return jdbc.queryForList(sqlSb.toString(), Song.class, param, skip, max);

    }

    @Override
    public List<Integer> listIds(Song song, Integer skip, Integer max, String orderBy) {
        StringBuilder sqlSb = new StringBuilder();
        String sql = "SELECT id FROM song WHERE TRUE  ";
        DynamicQuery query = new DynamicQuery();
        StatementParameter param = new StatementParameter();
        
    	builderQuery(song, query, param);
		
        query.setBaseSql(sql);
        sql = query.generateSql();
        sqlSb.append(sql);
        if (!StringUtils.isEmpty(orderBy)) {
            sqlSb.append(" order by ").append(orderBy);
        }
        if (skip == null || max == null) {
            return jdbc.queryForInts(sqlSb.toString(), param);
        }
        return jdbc.queryForInts(sqlSb.toString(), param, skip, max);

    }

    @Override
    public boolean update(Song song) {
        UpdateBuilder builder = new UpdateBuilder("song");
		
		builderUpdate(song, builder);
		
    	Integer id = song.getId();
        if (id != null) {
             builder.where.setInt("id", id);
        }

        return jdbc.updateForBoolean(builder);
    }

    @Override
    public Integer count(Song song) {
    	StringBuilder sqlSb = new StringBuilder();
        String sql = "SELECT count(1) FROM song WHERE TRUE ";
        DynamicQuery query = new DynamicQuery();
        StatementParameter param = new StatementParameter();
        
    	builderQuery(song, query, param);
		
        query.setBaseSql(sql);
        sql = query.generateSql();
        sqlSb.append(sql);
        return jdbc.queryForInt(sqlSb.toString(), param);
    }

	@Override
	public boolean delete(Integer id) {
//		String sql = "DELETE FROM song WHERE id=? ";
//		StatementParameter param = new StatementParameter();
//		param.setInt(id);
//		return jdbc.updateForBoolean(sql, param);
		
		
		String sql = "update song set isDelete = 1 WHERE id= ? ";
		StatementParameter param = new StatementParameter();
		param.setInt(id);
		return jdbc.updateForBoolean(sql, param);
		
	}

	@Override
	public List<Song> getSongsByIds(List<Integer> ids) {
		List<Song> list = null;
		if (CollectionUtils.isNotEmpty(ids)) {
	        String sql = "SELECT * FROM song WHERE TRUE AND id in ( " + StringUtils.join(ids, ",") + " );";
	        list = jdbc.queryForList(sql, Song.class);
			final List<Integer> arrayIds = ids;
			// 按传进来的id顺序排序结果
			Collections.sort(list, new Comparator<Song>() {
				public int compare(Song m1, Song m2) {
					int p1 = arrayIds.indexOf(m1.getId());
					int p2 = arrayIds.indexOf(m2.getId());
					return Integer.valueOf(p1).compareTo(Integer.valueOf(p2));
				}
			});
		}
		return list;
	}

	private void builderUpdate(Song song, AbstractSqlBuilder builder) {
        
		String singerId = song.getSingerId();
        if (singerId != null) {
            builder.setString("singerId", singerId);
        }
		String songId = song.getSongId();
        if (songId != null) {
            builder.setString("songId", songId);
        }
		String albumId = song.getAlbumId();
        if (albumId != null) {
            builder.setString("albumId", albumId);
        }
		String commentId = song.getCommentId();
        if (commentId != null) {
            builder.setString("commentId", commentId);
        }
		String mp3Url = song.getMp3Url();
        if (mp3Url != null) {
            builder.setString("mp3Url", mp3Url);
        }
	
	}

	private void builderQuery(Song song, DynamicQuery query, StatementParameter param) {
        
    	Integer id = song.getId();
        if (id != null) {
    		query.addParameter(new Parameter("id", "=", song.getId()), param);
        }

        String singerId = song.getSingerId();
	    if (singerId != null) {
	    	query.addParameter(new Parameter("singerId", "=", song.getSingerId()), param);
		}
        String songId = song.getSongId();
        if (StringUtils.isNotBlank(songId)) {
	    	query.addParameter(new Parameter("songId", "=", song.getSongId()), param);
		}
        String albumId = song.getAlbumId();
        if (StringUtils.isNotBlank(albumId)) {
	    	query.addParameter(new Parameter("albumId", "=", song.getAlbumId()), param);
		}
        String commentId = song.getCommentId();
        if (StringUtils.isNotBlank(commentId)) {
	    	query.addParameter(new Parameter("commentId", "=", song.getCommentId()), param);
		}
        String mp3Url = song.getMp3Url();
        if (StringUtils.isNotBlank(mp3Url)) {
	    	query.addParameter(new Parameter("mp3Url", "=", song.getMp3Url()), param);
		}
		
		// 遍历查询对象的查询条件
		if (song != null) {
			if (song instanceof SongQuery) {
				SongQuery songQuery = (SongQuery) song;
			}
		}
		
	}
}
