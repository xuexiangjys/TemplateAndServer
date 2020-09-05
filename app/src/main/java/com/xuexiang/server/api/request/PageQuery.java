package com.xuexiang.server.api.request;

import com.xuexiang.xutil.net.JsonUtil;

/**
 * @author xuexiang
 * @since 2018/8/15 上午12:26
 */
public class PageQuery {

    /**
     * 第几页数
     */
    public int pageNum;
    /**
     * 每页的数量
     */
    public int pageSize;

    public PageQuery() {
    }

    public PageQuery(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public PageQuery setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageQuery setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
