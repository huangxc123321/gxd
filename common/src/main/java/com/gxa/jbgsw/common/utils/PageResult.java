package com.gxa.jbgsw.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: huangxc
 * @Description： PeelingMachine-MP-Server
 * @Date： 2020/10/14 14:46
 */

@ApiModel
public class PageResult<T> extends com.gxa.jbgsw.common.utils.DataObject {
    @ApiModelProperty("总记录数")
    private long total;
    @ApiModelProperty("结果集")
    private List<T> list;
    @ApiModelProperty("当前页号")
    private int pageNum;
    @ApiModelProperty("每页的数量")
    private int pageSize;
    @ApiModelProperty("当前页的记录数量")
    private int size;
    @ApiModelProperty("总页数")
    private int pages;

    public PageResult() {
    }

    public int getPages() {
        return this.pages;
    }

    public PageResult<T> setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public long getTotal() {
        return this.total;
    }

    public PageResult<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public List<T> getList() {
        return this.list;
    }

    public PageResult<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public PageResult setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public PageResult setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getSize() {
        return this.size;
    }

    public PageResult setSize(int size) {
        this.size = size;
        return this;
    }
}
