package com.eazitasc.paging;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagingResp<T> {
    private int draw;
    private int totalDraw;
    private int recordsTotal;
    private int recordsFiltered;
    private List<T> data;
}