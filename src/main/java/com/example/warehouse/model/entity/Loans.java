package com.example.warehouse.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.util.Date;
// su dung subselect de tao bang ao ghep du lieu bang voi nhau trong dieu kien
@Data
@Immutable
@Entity
@Subselect("SELECT l.id AS id, " +
        "l.user_name AS username, " +
        "l.amount AS amount, " +
        "l.month AS month, " +
        "l.month_pay AS monthPay, " +
        "l.rate AS rate, " +
        "l.start_date AS startDate," +
        "l.end_date AS endDate, " +
        "l.status AS status, " +
        "l.name_status AS nameStatus," +
        "u.full_name AS fullName " +
        "FROM loan l " +
        "LEFT JOIN user u ON l.user_name = u.user_name")
public class Loans {
    @Id
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
