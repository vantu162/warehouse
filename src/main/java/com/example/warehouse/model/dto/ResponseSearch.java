package com.example.warehouse.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ResponseSearch<T> {
    public List<T> data;
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private long totalPages;
}
