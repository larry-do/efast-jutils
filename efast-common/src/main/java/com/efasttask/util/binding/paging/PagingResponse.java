package com.efasttask.util.binding.paging;

import java.util.List;

public class PagingResponse<T> {
    private int draw;
    private int totalDraw;
    private int recordsTotal;
    private int recordsFiltered;
    private List<T> data;

    public PagingResponse() {
    }

    public PagingResponse(int draw, int totalDraw, int recordsTotal, int recordsFiltered, List<T> data) {
        this.draw = draw;
        this.totalDraw = totalDraw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getTotalDraw() {
        return totalDraw;
    }

    public void setTotalDraw(int totalDraw) {
        this.totalDraw = totalDraw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}