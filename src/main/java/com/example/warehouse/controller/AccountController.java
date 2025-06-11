package com.example.warehouse.controller;

import com.example.warehouse.model.dto.ApiResponse;
import com.example.warehouse.model.dto.AccountRequest;
import com.example.warehouse.model.entity.Token;
import com.example.warehouse.repository.acc.AccountQueryManager;
import com.example.warehouse.model.entity.User;
import com.example.warehouse.services.KeycloakService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warehouse")
public class AccountController {

  public final AccountQueryManager accountQueryManager;
  public final KeycloakService keycloakService;

  public AccountController(KeycloakService keycloakService, AccountQueryManager accountQueryManager) {
    this.keycloakService = keycloakService;
    this.accountQueryManager = accountQueryManager;
  }

   @PostMapping("/register")
   public ApiResponse<User> register(@RequestBody AccountRequest loginRequest){
      return accountQueryManager.register(loginRequest);
   }

   @PostMapping("/login")
   public ApiResponse<Token> login(@RequestBody AccountRequest loginRequest){
      return accountQueryManager.login(loginRequest);
   }
    @PostMapping("/info")
    public ApiResponse<User> getInfoUser(JwtAuthenticationToken token){
        return accountQueryManager.infoUser(token);
    }

}
