package com.example.warehouse.services;

import com.example.warehouse.exception.CustomException;
import com.example.warehouse.util.Contants;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakService {
    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(Contants.SERVER_URL)
                .realm(Contants.REALM)
                .clientId(Contants.CLIENT_ID)
                .clientSecret(Contants.CLIENT_SECRET)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }

    public String refreshAccessToken(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = Contants.REFRESH_TOKEN_URL;

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(Contants.TEXT_CLIENT_ID, Contants.CLIENT_ID);
        body.add(Contants.TEXT_GRANT_TYPE, Contants.REFRESH_TOKEN);
        body.add(Contants.TEXT_REFRESH_TOKEN, refreshToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        return response.getBody().get("access_token").toString();
    }

    public String getAccessToken() {
        return getInstance().tokenManager().getAccessToken().getToken();
    }

    public void assignClientRole(String userId, String roleName) {
        Keycloak keycloak = getInstance();
        RealmResource realmResource = keycloak.realm(Contants.REALM);

        // Lay client UUID tu clientId
        List<ClientRepresentation> clients = realmResource.clients().findByClientId(Contants.CLIENT_ID);
        if (clients.isEmpty()) {
            throw new RuntimeException("Client not found: " + Contants.CLIENT_ID);
        }
        String clientUUID = clients.get(0).getId();

        // Lay user
        UserResource userResource = realmResource.users().get(userId);
        // lay role tu client
        RoleRepresentation clientRole = realmResource.clients()
                .get(clientUUID)
                .roles()
                .get(roleName)
                .toRepresentation();
        // gan role cho user
        userResource.roles()
                .clientLevel(clientUUID)
                .add(Collections.singletonList(clientRole));
    }

    public void isUsernameExists(String username) {
        Keycloak keycloak = getInstance();
        List<UserRepresentation> users = keycloak.realm(Contants.REALM)
                .users()
                .search(username, true);
        if(!users.isEmpty()){
            throw new CustomException(401,"Người dùng đã tồn tại!");
        }
    }
}
