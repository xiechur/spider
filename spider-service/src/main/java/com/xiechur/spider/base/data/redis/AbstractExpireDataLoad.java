package com.xiechur.spider.base.data.redis;

/**
 * 缓存加载抽象类，用来规范缓存加载行为
 * @author zhangjinghua
 *
 */
public abstract class AbstractExpireDataLoad {
	/**
	 * 统计加载数据条数
	 */
	protected int count = 0;
	/**
	 * 数据库读取分页大小
	 */
	protected Integer page_size = 500;

	/**
	 * 设置redis缓存时长，只需要设置一次
	 */
	protected abstract void setTTL();
	/**
	 * 对于set/hash/list需要进行缓存清理，再加载数据
	 */
	protected abstract void cleanRedisData();
	/**
	 * 如果count为零，需要设置默认值。避免频繁读取数据
	 */
	protected abstract void ifCountIsZeroSetNullData();
	/**
	 * 通过springcontext获取bean，避免调用方传递太多参数
	 */
	protected abstract void initBean();
	/**
	 * 检查bean是否加载成功
	 * @throws RuntimeException
	 */
	protected abstract void checkBean() throws RuntimeException;
}
