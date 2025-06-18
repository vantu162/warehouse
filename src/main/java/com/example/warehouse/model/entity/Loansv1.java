package com.example.warehouse.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loansv1 {
    private Long id;
    private String username;
    private Long amount;
    private int month;
    private Long monthPay;
    private Double rate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd H:mm:ss")
    private Date endDate;

    private int status;
    private String nameStatus;
    private String fullName;
}
