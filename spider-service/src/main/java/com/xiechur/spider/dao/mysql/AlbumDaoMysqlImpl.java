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

import com.xiechur.spider.dao.AlbumDao;
import com.xiechur.spider.model.Album;
import com.xiechur.spider.model.query.AlbumQuery;
import com.xiechur.spider.base.data.datasource.jdbc.DynamicQuery;
import com.xiechur.spider.base.data.datasource.jdbc.Jdbc;
import com.xiechur.spider.base.data.datasource.jdbc.Parameter;
import com.xiechur.spider.base.data.datasource.jdbc.StatementParameter;
import com.xiechur.spider.base.data.datasource.jdbc.builder.InsertBuilder;
import com.xiechur.spider.base.data.datasource.jdbc.builder.UpdateBuilder;
import com.xiechur.spider.base.data.datasource.jdbc.builder.AbstractSqlBuilder;
import com.xiechur.spider.util.DateUtil;

@Repository
public class AlbumDaoMysqlImpl implements AlbumDao {
    @Autowired
    @Qualifier(value="mainJdbc")
    private Jdbc jdbc;

    @Override
    public Album get(Integer id) {
        String sql = "SELECT * FROM album WHERE id =? ";
        StatementParameter param = new StatementParameter();
        param.setInt(id);
        return jdbc.query(sql, Album.class, param);
    }

    @Override
    public boolean add(Album album) {
        InsertBuilder builder = new InsertBuilder("album", true);
		
		builderUpdate(album, builder);
		
		Long id = jdbc.insertForLastId(builder.getSql(), builder.getParam());
        album.setId(id.intValue());
    
		return id > 0;
    }

    @Override
    public List<Album> list(Album album, Integer skip, Integer max, String orderBy) {
        StringBuilder sqlSb = new StringBuilder();
        String sql = "SELECT * FROM album WHERE TRUE  ";
        DynamicQuery query = new DynamicQuery();
        StatementParameter param = new StatementParameter();
        
    	builderQuery(album, query, param);
		
        query.setBaseSql(sql);
        sql = query.generateSql();
        sqlSb.append(sql);
        if (!StringUtils.isEmpty(orderBy)) {
            sqlSb.append(" order by ").append(orderBy);
        }
        if (skip == null || max == null) {
            return jdbc.queryForList(sqlSb.toString(), Album.class, param);
        }
        return jdbc.queryForList(sqlSb.toString(), Album.class, param, skip, max);

    }

    @Override
    public List<Integer> listIds(Album album, Integer skip, Integer max, String orderBy) {
        StringBuilder sqlSb = new StringBuilder();
        String sql = "SELECT id FROM album WHERE TRUE  ";
        DynamicQuery query = new DynamicQuery();
        StatementParameter param = new StatementParameter();
        
    	builderQuery(album, query, param);
		
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
    public boolean update(Album album) {
        UpdateBuilder builder = new UpdateBuilder("album");
		
		builderUpdate(album, builder);
		
    	Integer id = album.getId();
        if (id != null) {
             builder.where.setInt("id", id);
        }

        return jdbc.updateForBoolean(builder);
    }

    @Override
    public Integer count(Album album) {
    	StringBuilder sqlSb = new StringBuilder();
        String sql = "SELECT count(1) FROM album WHERE TRUE ";
        DynamicQuery query = new DynamicQuery();
        StatementParameter param = new StatementParameter();
        
    	builderQuery(album, query, param);
		
        query.setBaseSql(sql);
        sql = query.generateSql();
        sqlSb.append(sql);
        return jdbc.queryForInt(sqlSb.toString(), param);
    }

	@Override
	public boolean delete(Integer id) {
//		String sql = "DELETE FROM album WHERE id=? ";
//		StatementParameter param = new StatementParameter();
//		param.setInt(id);
//		return jdbc.updateForBoolean(sql, param);
		
		
		String sql = "update album set isDelete = 1 WHERE id= ? ";
		StatementParameter param = new StatementParameter();
		param.setInt(id);
		return jdbc.updateForBoolean(sql, param);
		
	}

	@Override
	public List<Album> getAlbumsByIds(List<Integer> ids) {
		List<Album> list = null;
		if (CollectionUtils.isNotEmpty(ids)) {
	        String sql = "SELECT * FROM album WHERE TRUE AND id in ( " + StringUtils.join(ids, ",") + " );";
	        list = jdbc.queryForList(sql, Album.class);
			final List<Integer> arrayIds = ids;
			// 按传进来的id顺序排序结果
			Collections.sort(list, new Comparator<Album>() {
				public int compare(Album m1, Album m2) {
					int p1 = arrayIds.indexOf(m1.getId());
					int p2 = arrayIds.indexOf(m2.getId());
					return Integer.valueOf(p1).compareTo(Integer.valueOf(p2));
				}
			});
		}
		return list;
	}

    @Override
    public boolean exist(String albumId) {
        Album query = new Album();
        query.setAlbumId(albumId);
        return count(query) > 0;
    }

    @Override
    public boolean updateByAlbumId(Album album) {
        UpdateBuilder builder = new UpdateBuilder("album");

        builderUpdate(album, builder);

        String id = album.getAlbumId();
        if (id != null) {
            builder.where.setString("albumId", id);
        }

        return jdbc.updateForBoolean(builder);
    }

    private void builderUpdate(Album album, AbstractSqlBuilder builder) {

        String singerId = album.getSingerId();
        if (singerId != null) {
            builder.setString("singerId", singerId);
        }
        String albumId = album.getAlbumId();
        if (albumId != null) {
            builder.setString("albumId", albumId);
        }
        String commentId = album.getCommentId();
        if (commentId != null) {
            builder.setString("commentId", commentId);
        }
		String picUrl = album.getPicUrl();
        if (picUrl != null) {
            builder.setString("picUrl", picUrl);
        }
		String name = album.getName();
        if (name != null) {
            builder.setString("name", name);
        }
		Date publishTime = album.getPublishTime();
        if (publishTime != null) {
            builder.setDate("publishTime", publishTime);
        }
		String publishCompany = album.getPublishCompany();
        if (publishCompany != null) {
            builder.setString("publishCompany", publishCompany);
        }
		String introdution = album.getIntrodution();
        if (introdution != null) {
            builder.setString("introdution", introdution);
        }
	
	}

	private void builderQuery(Album album, DynamicQuery query, StatementParameter param) {
        
    	Integer id = album.getId();
        if (id != null) {
    		query.addParameter(new Parameter("id", "=", album.getId()), param);
        }
        String albumId = album.getAlbumId();
        if (StringUtils.isNotBlank(albumId)) {
            query.addParameter(new Parameter("albumId", "=", album.getAlbumId()), param);
        }

        String singerId = album.getSingerId();
	    if (singerId != null) {
	    	query.addParameter(new Parameter("singerId", "=", album.getSingerId()), param);
		}
        String commentId = album.getCommentId();
        if (StringUtils.isNotBlank(commentId)) {
            query.addParameter(new Parameter("commentId", "=", album.getCommentId()), param);
        }
        String picUrl = album.getPicUrl();
        if (StringUtils.isNotBlank(picUrl)) {
	    	query.addParameter(new Parameter("picUrl", "=", album.getPicUrl()), param);
		}
        String name = album.getName();
        if (StringUtils.isNotBlank(name)) {
	    	query.addParameter(new Parameter("name", "=", album.getName()), param);
		}
        Date publishTime = album.getPublishTime();
	    if (publishTime != null) {
	    	query.addParameter(new Parameter("publishTime", "=", album.getPublishTime()), param);
		}
        String publishCompany = album.getPublishCompany();
        if (StringUtils.isNotBlank(publishCompany)) {
	    	query.addParameter(new Parameter("publishCompany", "=", album.getPublishCompany()), param);
		}
        String introdution = album.getIntrodution();
        if (StringUtils.isNotBlank(introdution)) {
	    	query.addParameter(new Parameter("introdution", "=", album.getIntrodution()), param);
		}
		
		// 遍历查询对象的查询条件
		if (album != null) {
			if (album instanceof AlbumQuery) {
				AlbumQuery albumQuery = (AlbumQuery) album;
				 Date startPublishTime = albumQuery.getStartPublishTime();
				 if (startPublishTime != null) {
				 	query.addParameter(new Parameter("publishTime", ">=", DateUtil.getTime(albumQuery.getStartPublishTime()) ), param);
				 }
				 
				 Date endPublishTime = albumQuery.getEndPublishTime();
				 if (endPublishTime != null) {
				 	query.addParameter(new Parameter("publishTime", "<=", DateUtil.getTime(albumQuery.getEndPublishTime()) ), param);
				 }
			}
		}
		
	}
}
