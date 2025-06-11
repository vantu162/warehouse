package com.example.warehouse.services;

import com.example.warehouse.exception.CustomException;
import com.example.warehouse.model.dto.ApiResponse;
import com.example.warehouse.model.dto.AccountRequest;
import com.example.warehouse.model.entity.Token;
import com.example.warehouse.repository.acc.AccountQueryManager;
import com.example.warehouse.model.entity.User;
import com.example.warehouse.repository.acc.UserRepository;
import com.example.warehouse.util.Contants;
import com.example.warehouse.util.Code;
import com.example.warehouse.util.Validate;
import com.google.gson.Gson;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class AccountManager implements AccountQueryManager {

    public final KeycloakService keycloakService;
    public final UserRepository userRepository;

    public AccountManager(KeycloakService keycloakService,UserRepository userRepository) {
        this.keycloakService = keycloakService;
        this.userRepository = userRepository;
    }

    // xu ly logic dang ky tai khoan
    @Override
    public ApiResponse<User> register(AccountRequest loginRequest) {

        registerValidate(loginRequest);

        Keycloak keycloak = keycloakService.getInstance();
        UserRepresentation user = new UserRepresentation();
        user.setUsername(loginRequest.getUsername());
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setEmail(loginRequest.getEmail());
        user.setFirstName(loginRequest.getFirstName());
        user.setLastName(loginRequest.getLastName());

        Response response = keycloak.realm(Contants.REALM).users().create(user);

        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(loginRequest.getPassword());
        credential.setTemporary(false);

        keycloak.realm(Contants.REALM).users().get(userId).resetPassword(credential);
        keycloakService.assignClientRole(userId, Contants.ROLE_NAME_USER);
        User userInfo = createUserInfo(loginRequest);

        return ApiResponse.<User>builder()
                .statusCode(Code.CREATED.getCode())
                .message("Account created successfully")
                .data(userInfo)
                .build();
    }

    // kiem tra du lieu dang ky co hop le khong
    private void registerValidate(AccountRequest loginRequest){

        if(!Validate.isValidUsername(loginRequest.getUsername())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_USERNAME);
        }

        if(!Validate.isValidStr(loginRequest.getPassword())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_PASSWORD);
        }

        if(!Validate.isValidEmail(loginRequest.getEmail())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_EMAIL);
        }

        if(!Validate.isValidStr(loginRequest.getFirstName())){
            throw new CustomException(Code.BAD_REQUEST.getCode(),Contants.EXCEPTION_INVALID_FIRST_NAME);
        }

        if(!Validate.isValidStr(loginRequest.getLastName())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_LAST_NAME);
        }

        User user = loginRequest.getUser();

        if(!Validate.isValidStr(user.getPhone())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_PHONE);
        }

        if(!Validate.isValidStr(user.getNationalId())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_NATIONAL_ID);
        }

        keycloakService.isUsernameExists(loginRequest.username);
    }

    // tao thong tin user info
    public User createUserInfo(AccountRequest loginRequest) {
        return userRepository.save(loginRequest.user);
    }

    // xu ly logic dang nhap
    @Override
    public ApiResponse<Token> login(AccountRequest loginRequest) {
        loginValidate(loginRequest);
        RestTemplate restTemplate = new RestTemplate();
        String url = Contants.TOKEN_URL;

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add(Contants.TEXT_CLIENT_ID, Contants.CLIENT_ID);
        formData.add(Contants.TEXT_CLIENT_SECRET, Contants.CLIENT_SECRET);
        formData.add(Contants.TEXT_GRANT_TYPE, Contants.PASSWORD);
        formData.add(Contants.TEXT_USERNAME, loginRequest.getUsername());
        formData.add(Contants.TEXT_PASSWORD, loginRequest.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        ResponseEntity<String> response = null;
        try {
             response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        }catch (Exception e){
            throw new CustomException(Code.BAD_REQUEST.getCode(),Contants.EXCEPTION_INVALID_USER_EXISTS);
        }

        Token token = new Gson().fromJson(response.getBody(), Token.class);

        return ApiResponse.<Token>builder()
                .statusCode(Code.SUCCESS.getCode())
                .message("Token retrieved successfully")
                .data(token)
                .build();
    }

    // kiem tra du lieu dang co hop le khong
    private void loginValidate(AccountRequest loginRequest){

        if(!Validate.isValidStr(loginRequest.getUsername())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_USERNAME);
        }
        if(!Validate.isValidStr(loginRequest.getPassword())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_PASSWORD);
        }
    }

    // xu ly tra ve thong tin user
    @Override
    public ApiResponse<User> infoUser(JwtAuthenticationToken token) {
        String username = token.getToken().getClaim(Contants.TEXT_PREFERRED_USER_NAME);
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = null;
        if (userOptional.isPresent()) {
             user = userOptional.get();
        }
        return ApiResponse.<User>builder()
                .statusCode(Code.SUCCESS.getCode())
                .message("Successfully")
                .data(user)
                .build();
    }
}



