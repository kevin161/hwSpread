package com.leisurely.spread.framework.base;

import java.util.List;

/**
 * 数组对象
 * @author xcl
 * create at 2015/12/2 16:14
 */
public class BaseListBean<T> {
    private String count;
    private String currentPage;
    private String pageSize;
    private List<T> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
