package com.crazy.code.web;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by developer2 on 2017/7/21.
 */
public class QueryFilter {

    /**
     * 参数map集合
     */
    private Map<String, Object> filter = new LinkedHashMap<>();

    /**
     * 当前的request请求
     */
    private HttpServletRequest request;

    private Pagination pagination;



    public QueryFilter() {
        this.filter = new LinkedHashMap();
        this.pagination = new Pagination();
    }

    /**
     * 从request请求中解析排序参数和翻页参数
     *
     * @param request        HttpServletRequest
     * @param needPagination boolean 是否需要翻页 true-需要，false-不需要
     */
    public void init(HttpServletRequest request) {
        this.request = request;
        try {
            pagination.parse(request.getRequestURI());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Map<String, Object> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
