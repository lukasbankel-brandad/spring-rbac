package de.devcamp.api;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import de.devcamp.config.JwtHelper;
import de.devcamp.config.WebSecurityConfig;
import de.devcamp.model.dto.LoginRequest;
import de.devcamp.model.dto.LoginResult;
import de.devcamp.model.entity.User;
import de.devcamp.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class AuthApi {
    private final JwtHelper jwtHelper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @PostMapping(path = "login")
    public ResponseEntity<LoginResult> login(@RequestBody LoginRequest loginRequest) {

        User user;
        try {
            user = userRepo.findByUsername(loginRequest.getUsername()).get();
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            Map<String, String> claims = new HashMap<>();
            claims.put("username", loginRequest.getUsername());

            String authorities = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
            claims.put(WebSecurityConfig.AUTHORITIES_CLAIM_NAME, authorities);
            claims.put("userId", user.getId().toString());

            String jwt = jwtHelper.createJwtForClaims(loginRequest.getUsername(), claims);
            return new ResponseEntity<>(new LoginResult(jwt, loginRequest.getUsername()), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }
}