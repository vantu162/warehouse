package com.example.warehouse.repository.loan;

import com.example.warehouse.model.dto.ApiResponse;
import com.example.warehouse.model.dto.CollateralRequest;
import com.example.warehouse.model.dto.ResponseSearch;
import com.example.warehouse.model.entity.Loan;
import com.example.warehouse.model.entity.Loans;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface LoanQueryManager {
  ApiResponse<CollateralRequest> createLoan(JwtAuthenticationToken token, CollateralRequest collateralRequest);
  ApiResponse<Loan> updateLoan(CollateralRequest collateralRequest);
  ResponseSearch<Loans> getListLoan(CollateralRequest request, JwtAuthenticationToken token);
}
