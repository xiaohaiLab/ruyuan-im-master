package com.ruyuan2020.im.common.core.domain;

import java.io.Serializable;
import java.util.List;

public class BasePage<T> implements Serializable {

    public BasePage() {
    }

    public BasePage(List<T> list) {
        this.list = list;
    }

    public BasePage(List<T> list, long total, long pageSize, long current) {
        this.pagination = new Pagination(total, pageSize, current);
        this.list = list;
    }

    public BasePage(List<T> list, Pagination pagination) {
        this.pagination = pagination;
        this.list = list;
    }

    private Pagination pagination;

    private List<T> list;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
