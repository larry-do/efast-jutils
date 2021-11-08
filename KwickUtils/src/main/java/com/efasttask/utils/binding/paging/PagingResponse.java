package com.efasttask.utils.binding.paging;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagingResponse<T> {
    private int draw;
    private int totalDraw;
    private int recordsTotal;
    private int recordsFiltered;
    private List<T> data;
}