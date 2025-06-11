package com.example.warehouse.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String username; // username

    @Column(name = "full_name")
    private String fullName; // Họ và tên

    @Column(name = "national_id")
    private String nationalId; // Số CMND/CCCD

    @Column(name = "phone")
    private String phone; // Số điện thoại

    @Column(name = "email")
    private String email; // Email người dùng

    @Column(name = "address")
    private String address; // Địa chỉ
}
