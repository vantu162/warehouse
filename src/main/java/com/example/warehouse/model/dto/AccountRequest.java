package com.example.warehouse.model.dto;

import com.example.warehouse.model.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {
    public String username;
    public String password;
    public String email;
    public String firstName;
    public String lastName;
    public User user;
}
