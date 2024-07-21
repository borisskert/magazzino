package de.borisskert.magazzino.security;

import de.borisskert.magazzino.user.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class AuthenticationUserAdapter {

    private final Authentication authentication;

    private AuthenticationUserAdapter(Authentication authentication) {
        this.authentication = authentication;
    }

    public User toUser() {
        if (authentication instanceof AnonymousAuthenticationToken anonymousAuthenticationToken) {
            return User.builder()
                    .authIdentifier(String.valueOf(anonymousAuthenticationToken.getKeyHash()))
                    .role(extractRole(anonymousAuthenticationToken))
                    .build();
        } else if (authentication instanceof OAuth2AuthenticationToken abstractAuthenticationToken) {
            OAuth2User principal = abstractAuthenticationToken.getPrincipal();

            return User.builder()
                    .authIdentifier(principal.getAttribute("sub"))
                    .email(principal.getAttribute("email"))
                    .firstName(principal.getAttribute("given_name"))
                    .lastName(principal.getAttribute("family_name"))
                    .role(extractRole(abstractAuthenticationToken))
                    .build();
        }

        throw new IllegalArgumentException("Unsupported authentication type: " + authentication.getClass().getName());
    }

    private static Role extractRole(AbstractAuthenticationToken oAuth2User) {
        return oAuth2User.getAuthorities()
                .stream()
                .findFirst()
                .flatMap(Role::from)
                .orElse(Role.ANONYMOUS);
    }

    public static AuthenticationUserAdapter from(Authentication authentication) {
        return new AuthenticationUserAdapter(authentication);
    }
}
