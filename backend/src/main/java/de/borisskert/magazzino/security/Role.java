package de.borisskert.magazzino.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Role implements GrantedAuthority {
    ADMIN, EMPLOYEE, CUSTOMER, ANONYMOUS;

    private static final Pattern GRANTED_AUTHORITY_ROLE_PATTERN = Pattern.compile("^ROLE_([A-Za-z]+)$");

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

    public static Optional<Role> from(GrantedAuthority authority) {
        String authorityString = authority.getAuthority();

        Matcher matcher = GRANTED_AUTHORITY_ROLE_PATTERN.matcher(authorityString);

        if (!matcher.matches()) {
            return Optional.empty();
        } else {
            String extractedRole = matcher.group(1);
            return fromString(extractedRole);
        }
    }
}
