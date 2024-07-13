package de.borisskert.magazzino.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class KeycloakRoleMapper implements GrantedAuthoritiesMapper {
    private static final String GROUPS = "groups";
    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        var authority = authorities.iterator().next();
        boolean isOidc = authority instanceof OidcUserAuthority;

        if (isOidc) {
            var oidcUserAuthority = (OidcUserAuthority) authority;
            var userInfo = oidcUserAuthority.getUserInfo();

            // Tokens can be configured to return roles under
            // Groups or REALM ACCESS hence have to check both
            if (userInfo.hasClaim(REALM_ACCESS_CLAIM)) {
                return extractRealmAccessClaims(userInfo.getClaims());
            } else if (userInfo.hasClaim(GROUPS)) {
                Collection<String> roles = userInfo.getClaim(GROUPS);
                return generateAuthoritiesFromClaim(roles);
            }
        } else {
            var oauth2UserAuthority = (OAuth2UserAuthority) authority;
            Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

            if (userAttributes.containsKey(REALM_ACCESS_CLAIM)) {
                return extractRealmAccessClaims(userAttributes);
            }
        }

        return Set.of();
    }

    private Set<GrantedAuthority> extractRealmAccessClaims(Map<String, Object> userAttributes) {
        Map<String, Object> realmAccess = (Map<String, Object>) userAttributes.get(REALM_ACCESS_CLAIM);
        Collection<String> roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);

        return generateAuthoritiesFromClaim(roles);
    }

    private Set<GrantedAuthority> generateAuthoritiesFromClaim(Collection<String> roles) {
        return roles.stream()
                .map(Role::fromString)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Role::asAuthority)
                .collect(Collectors.toSet());
    }
}
