package com.example.warehouse.controller;

import com.example.warehouse.model.dto.ApiResponse;
import com.example.warehouse.model.dto.ResponseSearch;
import com.example.warehouse.model.dto.CollateralRequest;
import com.example.warehouse.model.entity.Loans;
import com.example.warehouse.model.entity.Loan;
import com.example.warehouse.repository.loan.LoanQueryManager;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LoanController {

    public final LoanQueryManager loanQueryManager;

    public LoanController(LoanQueryManager loanQueryManager) {
        this.loanQueryManager = loanQueryManager;
    }
    @PostMapping("/user/init")
    public ApiResponse<CollateralRequest> createLoan(JwtAuthenticationToken token, @RequestBody CollateralRequest createRequest) {
        return loanQueryManager.createLoan(token,createRequest);
    }

    @PostMapping("/admin/approve")
    public ApiResponse<Loan> updateStatusLoan(@RequestBody CollateralRequest createRequest) {
        return loanQueryManager.updateLoan(createRequest);
    }

    @PostMapping("/warehouse/search")
    public ResponseSearch<Loans> getLoans(JwtAuthenticationToken token, @RequestBody CollateralRequest collateralRequest) {
        return loanQueryManager.getListLoan(collateralRequest, token);
    }

    @PostMapping("/warehouse/search_by_entitymanager")
    public ResponseSearch<Loans> getLoansByEntityManager(JwtAuthenticationToken token, @RequestBody CollateralRequest collateralRequest) {
        return loanQueryManager.getListLoanByEntityManager(collateralRequest, token);
    }
}
