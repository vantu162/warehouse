package com.example.warehouse.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Collateral")
public class Collateral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "vehicle_name")
    private String vehicleName;

    @Column(name = "color")
    private String color;

    @Column(name = "vehicle_type") //  0 xe may,1 xe ô tô
    private int vehicleType;

    @Column(name = "type") // xe ô tô, xe may
    private String type;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "manufacture_Year")
    private String manufactureYear;

    @Column(name = "license_plate")
    private String licensePlate;
}
