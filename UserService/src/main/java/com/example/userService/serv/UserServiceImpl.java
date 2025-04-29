package com.example.userService.serv;

import com.example.userService.Entity.User;
import com.example.userService.Repository.UserRepository;
import com.example.userService.dto.CreateUserRequest;
import com.example.userService.dto.LoginRequest;
import com.example.userService.dto.LoginResponse;
import com.example.userService.dto.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    public UserServiceImpl(UserRepository repo, BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public UserResponse register(CreateUserRequest req) {
        createUserInKeycloak(req);

        String hashedPassword = encoder.encode(req.getPassword());
        User savedUser = repo.save(new User(req.getUsername(), req.getEmail(), hashedPassword));

        return new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest req) {
        Map<String, Object> tokenResponse = authenticateUserWithKeycloak(req);

        return new LoginResponse(
                tokenResponse.get("access_token").toString(),
                tokenResponse.get("refresh_token").toString(),
                (int) tokenResponse.get("expires_in")
        );
    }

    @Override
    public UserResponse getById(String id) {
        User user = repo.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found"));

        // Optionally verify from Keycloak by querying /users/{id} if needed

        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    private void createUserInKeycloak(CreateUserRequest req) {
        String url = keycloakServerUrl + "/admin/realms/" + realm + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getAdminAccessToken());

        Map<String, Object> payload = new HashMap<>();
        payload.put("username", req.getUsername());
        payload.put("email", req.getEmail());
        payload.put("enabled", true);

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", req.getPassword());
        credentials.put("temporary", false);

        payload.put("credentials", new Object[]{ credentials });

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to register user in Keycloak: " + response.getBody());
        }
    }

    private Map<String, Object> authenticateUserWithKeycloak(LoginRequest req) {
        String url = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        body.add("username", req.getUsername());
        body.add("password", req.getPassword());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null || !response.getBody().containsKey("access_token")) {
                throw new RuntimeException("Invalid username or password");
            }

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    private String getAdminAccessToken() {
        String url = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", "admin-cli");
        body.add("username", adminUsername);
        body.add("password", adminPassword);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Failed to get admin access token");
        }

        return response.getBody().get("access_token").toString();
    }
}
