package com.example.warehouse.repository;

import com.example.warehouse.model.entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LoansRepository extends JpaRepository<Loans, Long>, JpaSpecificationExecutor<Loans> {
}
