package com.example.warehouse.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "month")
    private int month;

    @Column(name = "month_pay")
    private Long monthPay;

    @Column(name = "rate")
    private Double rate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_date")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd H:mm:ss")
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "status")
    private int status;

    @Column(name = "name_status")
    private String nameStatus;
}
