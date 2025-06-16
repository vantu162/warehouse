package com.example.notify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

@JsonDeserialize
@Getter
@Setter
public class Token {
    public String accessToken;
    public int expiresIn;
    public int refreshExpiresIn;
    public String refreshToken;
    public String tokenType;
    public int notBeforePolicy;
    public String sessionState;
    public String scope;
}
