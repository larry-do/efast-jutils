package com.eazitasc.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingReq {
    private int draw;
    private int length;
    private int start;
    private Boolean desc;
    private String orderBy;
}