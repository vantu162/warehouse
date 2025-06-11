package com.example.warehouse.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class ApiResponse<T> {
    public String message;
    public int statusCode;
    public T data;
}

