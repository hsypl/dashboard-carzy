package com.crazy.code.web;

import java.util.List;
import java.util.Map;

/**
 * Created by huangshuoying on 2018/6/30.
 */
public class DataTables<E> {

    private Integer draw ;
    private E data;
    private Pagination pagination;
    private Integer code;
    private Map<String,Object> filter;

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Map<String, Object> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }
}
