package com.example.warehouse.repository.loan;

import com.example.warehouse.model.entity.Collateral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollateralRepository extends JpaRepository<Collateral, Long> {
    Optional<Collateral> findByUsername(String username);
}
