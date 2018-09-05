package com.xiechur.spider.base.data.datasource.jdbc;

import java.util.List;

/**
 * dao 层通用接口 适配代码生成
 * @author Administrator
 *
 * @param <K>
 * @param <V>
 */
public interface JdbcBase <BEAN, PK>{
    public BEAN get(PK id);

    public List<BEAN> list(BEAN model, Integer skip, Integer max, String orderBy);

    boolean add(BEAN mode);

    Integer count(BEAN  model);

    boolean update(BEAN  model);
    
    boolean delete(PK id);

}
