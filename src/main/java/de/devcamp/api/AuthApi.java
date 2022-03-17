package de.devcamp.api;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import de.devcamp.config.JwtHelper;
import de.devcamp.config.WebSecurityConfig;
import de.devcamp.model.dto.LoginRequest;
import de.devcamp.model.dto.LoginResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping(path = "login")
    public ResponseEntity<LoginResult> login(@RequestBody LoginRequest loginRequest) {

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        if (passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            Map<String, String> claims = new HashMap<>();
            claims.put("username", loginRequest.getUsername());

            String authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
            claims.put(WebSecurityConfig.AUTHORITIES_CLAIM_NAME, authorities);
            claims.put("userId", String.valueOf(1));

            String jwt = jwtHelper.createJwtForClaims(loginRequest.getUsername(), claims);
            return new ResponseEntity<>(new LoginResult(jwt, loginRequest.getUsername()), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }
}