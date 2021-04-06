package com.mumu.entity;

import java.util.ArrayList;

public class PageResult<T> {

    private int currentPage;// 当前页
    private int pageSize;//页面大小

    private Long totalCount;// 总条数
    private Integer totalPage;// 总页数
    private ArrayList<T> items;// 当前页数据

    public PageResult() {
    }

    public PageResult(Long total, ArrayList<T> items) {
        this.totalCount = total;
        this.items = items;
    }


    public PageResult(Long total, ArrayList<T> items, Integer totalPage) {
        this.totalCount = total;
        this.totalPage = totalPage;
        this.items = items;
    }

    public PageResult(int currentPage, int pageSize, Long total, ArrayList<T> items, Integer totalPage) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = total;
        this.totalPage = totalPage;
        this.items = items;
    }



    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<T> getItems() {
        return items;
    }

    public void setItems(ArrayList<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", items=" + items +
                '}';
    }
}
