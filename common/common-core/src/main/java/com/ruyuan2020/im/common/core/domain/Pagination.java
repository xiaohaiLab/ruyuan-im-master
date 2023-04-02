package com.ruyuan2020.im.common.core.domain;

import java.io.Serializable;

/**
 * 分页信息
 */
public class Pagination implements Serializable {

    public Pagination(long total, long pageSize, long current) {
        this.total = total;
        this.pageSize = pageSize;
        this.current = current;
    }

    /**
     * 总条目数
     */
    private long total;

    /**
     * 每页数量
     */
    private long pageSize;

    /**
     * 当前页
     */
    private long current;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }
}
