package de.devcamp.model.dto;

import de.devcamp.model.entity.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserResult {
    private String id;
    private String username;
    private Set<Role> authorities = new HashSet<>();
}
