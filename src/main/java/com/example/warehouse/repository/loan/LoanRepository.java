package com.example.warehouse.repository.loan;

import com.example.warehouse.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> , JpaSpecificationExecutor<Loan> {
    Optional<Loan> findByUsernameAndId(String username, Long id);
    @Query("SELECT l FROM Loan l WHERE l.username = :username")
    Loan findLoansByUsername(@Param("username") String username);
}
