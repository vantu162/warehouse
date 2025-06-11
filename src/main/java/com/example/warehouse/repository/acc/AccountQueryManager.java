package com.example.warehouse.repository.acc;

import com.example.warehouse.model.dto.ApiResponse;
import com.example.warehouse.model.dto.AccountRequest;
import com.example.warehouse.model.entity.Token;
import com.example.warehouse.model.entity.User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface AccountQueryManager {
    ApiResponse<User> register(AccountRequest loginRequest);
    ApiResponse<Token> login(AccountRequest loginRequest);
    ApiResponse<User> infoUser(JwtAuthenticationToken token);

}
