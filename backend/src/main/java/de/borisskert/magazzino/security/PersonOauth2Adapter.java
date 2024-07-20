package de.borisskert.magazzino.security;

import de.borisskert.magazzino.person.Person;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class PersonOauth2Adapter {

    private final OAuth2AuthenticationToken oAuth2User;

    private PersonOauth2Adapter(OAuth2AuthenticationToken oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    public Person toPerson() {
        OAuth2User principal = oAuth2User.getPrincipal();

        return Person.builder()
                .email(principal.getAttribute("email"))
                .firstName(principal.getAttribute("given_name"))
                .lastName(principal.getAttribute("family_name"))
                .role(extractRole())
                .authIdentifier(principal.getAttribute("sub"))
                .build();
    }

    private Role extractRole() {
        return oAuth2User.getAuthorities()
                .stream()
                .findFirst()
                .flatMap(Role::from)
                .orElse(Role.GUEST);
    }

    public static PersonOauth2Adapter from(OAuth2AuthenticationToken principal) {
        return new PersonOauth2Adapter(principal);
    }
}
