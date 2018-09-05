package com.xiechur.spider.dao.mysql;

import java.util.List;
import java.util.Comparator;
import java.util.Collections;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xiechur.spider.dao.SingerDao;
import com.xiechur.spider.model.Singer;
import com.xiechur.spider.model.query.SingerQuery;
import com.xiechur.spider.base.data.datasource.jdbc.DynamicQuery;
import com.xiechur.spider.base.data.datasource.jdbc.Jdbc;
import com.xiechur.spider.base.data.datasource.jdbc.Parameter;
import com.xiechur.spider.base.data.datasource.jdbc.StatementParameter;
import com.xiechur.spider.base.data.datasource.jdbc.builder.InsertBuilder;
import com.xiechur.spider.base.data.datasource.jdbc.builder.UpdateBuilder;
import com.xiechur.spider.base.data.datasource.jdbc.builder.AbstractSqlBuilder;

@Repository
public class SingerDaoMysqlImpl implements SingerDao {
    @Autowired
    @Qualifier(value="mainJdbc")
    private Jdbc jdbc;

    @Override
    public Singer get(Integer id) {
        String sql = "SELECT * FROM singer WHERE id =?  ";
        StatementParameter param = new StatementParameter();
        param.setInt(id);
        return jdbc.query(sql, Singer.class, param);
    }

    @Override
    public boolean add(Singer singer) {
        InsertBuilder builder = new InsertBuilder("singer", true);

        builderUpdate(singer, builder);

        Long id = jdbc.insertForLastId(builder.getSql(), builder.getParam());
        singer.setId(id.intValue());

        return id > 0;
    }

    @Override
    public List<Singer> list(Singer singer, Integer skip, Integer max, String orderBy) {
        StringBuilder sqlSb = new StringBuilder();
        String sql = "SELECT * FROM singer WHERE TRUE  ";
        DynamicQuery query = new DynamicQuery();
        StatementParameter param = new StatementParameter();

        builderQuery(singer, query, param);

        query.setBaseSql(sql);
        sql = query.generateSql();
        sqlSb.append(sql);
        if (!StringUtils.isEmpty(orderBy)) {
            sqlSb.append(" order by ").append(orderBy);
        }
        if (skip == null || max == null) {
            return jdbc.queryForList(sqlSb.toString(), Singer.class, param);
        }
        return jdbc.queryForList(sqlSb.toString(), Singer.class, param, skip, max);

    }

    @Override
    public List<Integer> listIds(Singer singer, Integer skip, Integer max, String orderBy) {
        StringBuilder sqlSb = new StringBuilder();
        String sql = "SELECT id FROM singer WHERE TRUE ";
        DynamicQuery query = new DynamicQuery();
        StatementParameter param = new StatementParameter();

        builderQuery(singer, query, param);

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
    public boolean update(Singer singer) {
        UpdateBuilder builder = new UpdateBuilder("singer");

        builderUpdate(singer, builder);

        Integer id = singer.getId();
        if (id != null) {
            builder.where.setInt("id", id);
        }

        return jdbc.updateForBoolean(builder);
    }


    @Override
    public boolean updateBySingerId(Singer singer) {
        UpdateBuilder builder = new UpdateBuilder("singer");

        builderUpdate(singer, builder);

        String singerId = singer.getSingerId();
        if (singerId != null) {
            builder.where.setString("singerId", singerId);
        }
        String sql  = builder.getSql();

        return jdbc.updateForBoolean(builder);
    }

    @Override
    public Integer count(Singer singer) {
        StringBuilder sqlSb = new StringBuilder();
        String sql = "SELECT count(1) FROM singer WHERE TRUE ";
        DynamicQuery query = new DynamicQuery();
        StatementParameter param = new StatementParameter();

        builderQuery(singer, query, param);

        query.setBaseSql(sql);
        sql = query.generateSql();
        sqlSb.append(sql);
        return jdbc.queryForInt(sqlSb.toString(), param);
    }

    @Override
    public boolean delete(Integer id) {


        String sql = "delete from singer  WHERE id= ? ";
        StatementParameter param = new StatementParameter();
        param.setInt(id);
        return jdbc.updateForBoolean(sql, param);

    }

    @Override
    public List<Singer> getSingersByIds(List<Integer> ids) {
        List<Singer> list = null;
        if (CollectionUtils.isNotEmpty(ids)) {
            String sql = "SELECT * FROM singer WHERE  id in ( " + StringUtils.join(ids, ",") + " );";
            list = jdbc.queryForList(sql, Singer.class);
            final List<Integer> arrayIds = ids;
            // 按传进来的id顺序排序结果
            Collections.sort(list, new Comparator<Singer>() {
                public int compare(Singer m1, Singer m2) {
                    int p1 = arrayIds.indexOf(m1.getId());
                    int p2 = arrayIds.indexOf(m2.getId());
                    return Integer.valueOf(p1).compareTo(Integer.valueOf(p2));
                }
            });
        }
        return list;
    }

    private void builderUpdate(Singer singer, AbstractSqlBuilder builder) {

        Integer id = singer.getId();
        if (id != null) {
            builder.setInt("id", id);
        }
        String singerId = singer.getSingerId();
        if (singerId != null) {
            builder.setString("singerId", singerId);
        }
        String name = singer.getName();
        if (name != null) {
            builder.setString("name", name);
        }
        String intro = singer.getIntro();
        if (intro != null) {
            builder.setString("intro", intro);
        }
        String picUrl = singer.getPicUrl();
        if (picUrl != null) {
            builder.setString("picUrl", picUrl);
        }

    }

    private void builderQuery(Singer singer, DynamicQuery query, StatementParameter param) {

        Integer id = singer.getId();
        if (id != null) {
            query.addParameter(new Parameter("id", "=", singer.getId()), param);
        }

        String singerId = singer.getSingerId();
        if (singerId != null) {
            query.addParameter(new Parameter("singerId", "=", singer.getSingerId()), param);
        }
        String name = singer.getName();
        if (StringUtils.isNotBlank(name)) {
            query.addParameter(new Parameter("name", "=", singer.getName()), param);
        }
        String intro = singer.getIntro();
        if (StringUtils.isNotBlank(intro)) {
            query.addParameter(new Parameter("intro", "=", singer.getIntro()), param);
        }
        String picUrl = singer.getPicUrl();
        if (StringUtils.isNotBlank(picUrl)) {
            query.addParameter(new Parameter("picUrl", "=", singer.getPicUrl()), param);
        }

        // 遍历查询对象的查询条件
        if (singer != null) {
            if (singer instanceof SingerQuery) {
                SingerQuery singerQuery = (SingerQuery) singer;
            }
        }

    }
}
