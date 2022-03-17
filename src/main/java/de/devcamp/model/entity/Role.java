package de.devcamp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {

    public static final String READER = "READER";
    public static final String WRITER = "WRITER";
    public static final String ADMIN = "ADMIN";

    private String authority;
}
