package com.example.newestlinen.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseListObj<T> {
    private List<T> data;
    private Integer page;
    private Integer totalPage;
    private Long totalElements;
}
