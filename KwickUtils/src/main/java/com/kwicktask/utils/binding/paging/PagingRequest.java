package com.kwicktask.utils.binding.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingRequest {
    private int draw;
    private int length;
    private int start;
    private Boolean desc;
    private String orderBy;
}