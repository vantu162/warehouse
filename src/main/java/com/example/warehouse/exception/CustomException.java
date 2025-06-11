package com.example.warehouse.exception;

import com.example.warehouse.model.dto.ResponseError;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomException extends RuntimeException {
    private int code;
    private String message;

    public ResponseError toErrorResponse() {
        return new ResponseError(this.code, this.message);
    }
}
