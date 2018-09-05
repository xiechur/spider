package com.xiechur.spider.base.data.redis;

import com.xiechur.spider.util.PageControl;

/**
 * 对redis缓存记录长度进行限制，所以需要能够即使加载数据的方法
 * @author zhangjinghua
 *
 */
public abstract class ATrimCacheDataLoad {

	public static final int cache_page_size = 100;
	
	/**
	 * 返回数据的记录总数。可以通过数据库，也可以在redis中保存。若是在redis中需要保持与数据库的一致
	 * @return
	 */
	protected abstract int getDatabaseCount();
	
	/**
	 * 返回需要从数据读取的分页信息，如果返回为空。那么不需要从数据库加载
	 * @param redisDataLength  redis中记录长度
	 * @param redisKey	redis的KEY值
	 * @param pageIndex   获取第几页
	 * @param pageSize    返回的每页大小
	 * @return
	 */
	public PageControl checkCacheData(Long redisDataLength, Integer pageIndex, Integer pageSize){
		//根据redis已经缓存数据，生成一个pageControl。以便后续代码判断，应该从哪里开始缓存
		PageControl cachePage = new PageControl(pageSize,redisDataLength.intValue(),1);
		int cachePageCount = cachePage.getPageCount();
		PageControl page = null;
		/**确定从哪个位置加载分几种场景。并且加载数是按照redis记录数翻倍加载。例如redis有10条，那么从数据加载10~20。redis有16条，那么从数据加载17~48。要避免加载数据过多，那么控制最大长度
		 * 1)前端请求的页数小于redis最后一页，那么不需要从数据库加载
		 * 2）前端请求的页数等于redis最后一页，那么看是否数据库记录数大于redis记录数。如果大于，就需要加载。
		 * 3）前端请求的页数超过了redis最后一页，那么加载数据从redis最后一页开始，不是从前端请求的页数开始。
		**/
		if(pageIndex>=cachePageCount&&pageIndex*pageSize>=redisDataLength){
			Integer database_data_count = getDatabaseCount();
			if(database_data_count>redisDataLength){
				int loadPageSize = cache_page_size;
				int loadPageIndex = 1;
				if(redisDataLength>0){
					loadPageSize=redisDataLength.intValue();
					loadPageIndex = 2;
				}
				page = new PageControl(loadPageSize,database_data_count,loadPageIndex);
			}
		}
		return page;
	}
	/**
	 * 返回需要从数据读取的分页信息，如果返回为空。那么不需要从数据库加载
	 * @param redisDataLength
	 * @param pageIndex
	 * @param pageSize
	 * @return  int[2]的数组，下标0是limit的记录行的偏移量，下标1是记录行的最大数目。如果数据为空，那么不需要加载数据
	 */
	public int[] checkCacheDataV2(Long redisDataLength,Integer pageIndex,Integer pageSize){
		int[] pageInfo = null;
		if(pageIndex*pageSize>=redisDataLength){
			Integer database_data_count = getDatabaseCount();
			if(database_data_count>redisDataLength){
				pageInfo = new int[2];
				pageInfo[0] = redisDataLength.intValue();
				pageInfo[1] = cache_page_size;
				if(pageInfo[1]<(pageIndex*pageSize-redisDataLength.intValue())){
					pageInfo[1] = pageIndex*pageSize-redisDataLength.intValue();
				}
				if(pageInfo[1]>(database_data_count-redisDataLength.intValue())){
					pageInfo[1] = database_data_count-redisDataLength.intValue();
				}
			}
		}
		return pageInfo;
	}
	
	/**
	 * 更新redis中记录总数，保持与数据库一致
	 * @param countKey   
	 * @param num
	 */
//	public abstract void setDataCount(String countKey,long num);
}
