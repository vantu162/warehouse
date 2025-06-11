package com.example.warehouse.repository.loan;

import com.example.warehouse.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> , JpaSpecificationExecutor<Loan> {
    Optional<Loan> findByUsernameAndId(String username, Long id);
}
