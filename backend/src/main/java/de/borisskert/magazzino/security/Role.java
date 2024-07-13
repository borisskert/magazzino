package de.borisskert.magazzino.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Optional;

public enum Role implements GrantedAuthority {
    ADMIN, EMPLOYEE, CUSTOMER, GUEST;

    @Override
    public String getAuthority() {
        return "ROLE_" + name().toLowerCase();
    }

    public GrantedAuthority asAuthority() {
        return new SimpleGrantedAuthority(getAuthority());
    }

    public static Optional<Role> fromString(String role) {
        try {
            Role foundRole = Role.valueOf(role.toUpperCase());
            return Optional.of(foundRole);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
