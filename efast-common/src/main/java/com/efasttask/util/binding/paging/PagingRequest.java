package com.efasttask.util.binding.paging;

public class PagingRequest {
    private int draw;
    private int length;
    private int start;
    private Boolean desc;
    private String orderBy;

    public PagingRequest() {
    }

    public PagingRequest(int draw, int length, int start, Boolean desc, String orderBy) {
        this.draw = draw;
        this.length = length;
        this.start = start;
        this.desc = desc;
        this.orderBy = orderBy;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public Boolean getDesc() {
        return desc;
    }

    public void setDesc(Boolean desc) {
        this.desc = desc;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}