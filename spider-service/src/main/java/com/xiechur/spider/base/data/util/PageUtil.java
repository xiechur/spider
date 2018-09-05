/**
 * 
 */
package com.xiechur.spider.base.data.util;

/**
 * @author dw_wangmin
 *
 */
public class PageUtil {

    /**
     * 获取正确的页码值。
     * 
     * @param pageId
     *            页码
     * @return 正确的页码
     */
    public static int getPageId(Integer pageId) {
        return pageId == null || pageId <= 0 ? 1 : pageId;
    }

    /**
     * 获取正确的每页记录数。
     * 
     * @param pageSize
     *            每页记录数
     * @param maxSize
     *            最大的每页记录数。
     * @return 正确的每页记录数
     */
    public static int getPageSize(Integer pageSize, Integer maxSize) {
        return pageSize == null || pageSize <= 0 || pageSize > maxSize ? maxSize : pageSize;
    }

    /**
     * 获取正确的页码值。
     * 
     * @param pageId
     *            页码
     * @return 正确的页码
     */
    public static int getPageId(Integer pageId, Integer pageCount) {
        return pageId == null || pageId <= 0 ? 1 : (pageId > pageCount ? pageCount : pageId);
    }

    /**
     * 获取页开始索引。
     * 
     * @param pageId
     *            页码
     * @param size
     *            页记录数
     * @return 页开始索引
     */
    public static int getStartIndex(Integer pageId, int size) {
        pageId = getPageId(pageId);
        return (pageId - 1) * size;
    }

    /**
     * 根据总记录数和页记录数计算总页数。
     * 
     * @param totalCount
     *            总记录数
     * @param size
     *            页记录数
     * @return 总页数
     */
    public static int getPageCount(int totalCount, int size) {
        return totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
    }
}
