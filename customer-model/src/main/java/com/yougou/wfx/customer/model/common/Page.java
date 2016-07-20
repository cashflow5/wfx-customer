package com.yougou.wfx.customer.model.common;

import com.google.common.collect.Lists;
import com.yougou.wfx.dto.base.PageModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/30
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = - 8522992066906959956L;
    /**
     * 每页的记录数
     */
    private int limit = 20;

    /**
     * 当前页中存放的数据
     */
    private List<T> items = Lists.newArrayList();

    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 页数
     */
    private int pageCount;

    /**
     * 跳转页数
     */
    private int page = 1;

    /**
     * 排序字段
     */
    private String orderSort;

    public Page() {

    }

    public Page(int page, int totalCount) {
        this.page = page;
        this.totalCount = totalCount;
        this.pageCount = getTotalPageCount();
    }

    public Page(int page, int limit, int totalCount) {
        this.page = page;
        this.limit = limit;
        this.totalCount = totalCount;
        this.pageCount = getTotalPageCount();
    }

    public Page(int page, int limit, int totalCount, List<T> items) {
        this.page = page;
        this.limit = limit;
        this.totalCount = totalCount;
        this.pageCount = getTotalPageCount();
        this.items = items;
    }

    /**
     * 取总页数
     */
    private final int getTotalPageCount() {
        if (totalCount % limit == 0) {
            return totalCount / limit;
        } else {
            return totalCount / limit + 1;
        }
    }

    /**
     * 取每页数据数
     */
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 取当前页中的记录.
     */
    public Object getResult() {
        return items;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }

    @Override
    public String toString() {
        return "Page [limit=" + limit + ", totalCount=" + totalCount + ", pageCount=" + pageCount + ", page=" + page + ", orderSort=" +
               orderSort + "]";
    }

    /**
     * 转换为pageModelDto
     *
     * @since 1.0 Created by lipangeng on 16/5/5 下午3:20. Email:lipg@outlook.com.
     */
    public PageModel toPageModelDto() {
        PageModel pageModel = new PageModel();
        pageModel.setPage(this.getPage());
        pageModel.setLimit(this.getLimit());
        pageModel.setOrderSort(this.getOrderSort());
        return pageModel;
    }

    /**
     * 从PageModelDto转换为本地vo
     *
     * @since 1.0 Created by lipangeng on 16/5/5 下午3:22. Email:lipg@outlook.com.
     */
    public static <T> Page<T> valueOf(PageModel pageModel) {
        Page<T> page = new Page<>();
        if (pageModel == null) {
            return page;
        }
        page.setLimit(pageModel.getLimit());
        page.setOrderSort(pageModel.getOrderSort());
        page.setPage(pageModel.getPage());
        page.setPageCount(pageModel.getPageCount());
        page.setTotalCount(pageModel.getTotalCount());
        return page;
    }
}
