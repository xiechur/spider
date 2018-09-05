package com.xiechur.spider.util;

import java.io.Serializable;
import java.util.ArrayList;
public class PageControl implements Serializable {

    private static final long serialVersionUID = -3915489652962373602L;

    public static final Integer DEFAULT_PAGESIZE = 20;

    private Integer count = 0; // 记录总数

    private Integer pageSize = DEFAULT_PAGESIZE; // 每页显示记录数

    private Integer pageCount = 0; // 总页数

    private Integer pageIndex = 1; // 当前页数

    private Integer pageStep = 4;

    private ArrayList<Integer> pages = null;

    public PageControl(Integer pageSize, Integer count, Integer pageIndex) {
        this.pageSize = pageSize;
        this.count = count;
        this.pageIndex = pageIndex;
        calcPageCount();
        calcPages();
    }

    public PageControl(Integer pageSize, Integer count, Integer pageIndex, Integer pageStep) {
        this.pageStep = pageStep;
        this.pageSize = pageSize;
        this.count = count;
        this.pageIndex = pageIndex;
        calcPageCount();
        calcPages();
    }

    public PageControl() {
        // this constructor just for springcloud feign Deserialize
    }

    /*
     * lhh : this constructor should not be used , because the value of count is
     * necessary
     */
    public PageControl(Integer pageSize, Integer pageIndex) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

    private void calcPages() {
        ArrayList<Integer> item_list = new ArrayList<Integer>();
        int begin_page = this.pageIndex - this.pageStep;
        if (begin_page > 1) {
            item_list.add(1);
            if (begin_page > 2) {
                item_list.add(0);
            }
        }
        if (begin_page < 1) {
            begin_page = 1;
        }
        int end_page = this.pageIndex + this.pageStep;
        if (end_page > this.pageCount) {
            end_page = this.pageCount;
        }
        for (int i = begin_page; i <= end_page; i++) {
            item_list.add(i);
        }
        if (end_page < this.pageCount) {
            if (end_page < this.pageCount - 1) {
                item_list.add(0);
            }
            item_list.add(this.pageCount);
        }
        this.pages = item_list;
    }

    public Integer getPageStep() {
        return pageStep;
    }

    public void setPageStep(Integer pageStep) {
        this.pageStep = pageStep;
    }

    public ArrayList<Integer> getPages() {
        return pages;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
        calcPageCount();
        calcPages();
    }

    public Integer getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer calcPageCount() {
        int size = count / pageSize;// 总条数/每页显示的条数=总页数
        int mod = count % pageSize;// 最后一页的条数
        if (mod != 0)
            size++;
        this.pageCount = count == 0 ? 1 : size;
        return pageCount;
    }

    public Integer getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    // 包含，起始索引为0
    public Integer getFromIndex() {
        if (pageIndex < 1) {
            pageIndex = 1;
        }

        if (pageIndex > calcPageCount()) {
            // 注释掉更新pageIndex的设置，因为在服务调用间的序列化会走到getFromIndex，如果在PageControl(Integer
            // pageSize, Integer pageIndex)
            // 传的pageIndex > 1，则这里会把pageIndex重置为1
            pageIndex = pageCount;
        }
        return (pageIndex - 1) * pageSize;
    }

    // 不包含
    public Integer getToIndex() {
        return Math.min(count, pageIndex * pageSize);
    }

    /**
     * 取得前一页页码
     * 
     * @return
     */
    public Integer getPrevious() {
        if (pageIndex > 1) {
            return pageIndex - 1;
        } else {
            return pageIndex;
        }
    }

    /**
     * 取得后一页页码
     * 
     * @return
     */
    public Integer getNext() {
        if (pageIndex < pageCount) {
            return pageIndex + 1;
        } else {
            return pageIndex;
        }
    }

    @Override
    public String toString() {
        final String TAB = "    ";
        StringBuffer retValue = new StringBuffer();
        retValue.append("PageInfo ( ").append(super.toString()).append(TAB).append("count = ").append(this.count)
                .append(TAB).append("pageSize = ").append(this.pageSize).append(TAB).append("pageCount = ")
                .append(this.pageCount).append(TAB).append("pageIndex = ").append(this.pageIndex).append(TAB)
                .append("pages = ").append(this.pages).append(TAB).append(" )");
        return retValue.toString();
    }

    public static void main(String[] args) {
        PageControl pageControl = new PageControl(4, 8);
        pageControl.setCount(125);
        ArrayList<Integer> pages = pageControl.getPages();
        System.out.println(pages);
    }
}
