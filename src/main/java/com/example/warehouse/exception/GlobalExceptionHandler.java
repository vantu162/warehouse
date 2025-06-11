package com.example.warehouse.exception;

import com.example.warehouse.model.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
// @ExceptionHandler(MethodArgumentNotValidException.class)
//→ Xác định rằng phương thức này sẽ xử lý các ngoại lệ loại MethodArgumentNotValidException,
// xảy ra khi dữ liệu đầu vào không hợp lệ (thường dùng với @Valid trong Spring Boot).
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

  //  tat cac ca Exception khong duoc khai bao se xu ly tai day
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return buildErrorResponse("Lỗi: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // tao response tra ve
    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
//  tuy chinh xu ly ngoai le (response tra ve)
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseError> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(ex.toErrorResponse(), HttpStatus.valueOf(ex.getCode()));
    }

}