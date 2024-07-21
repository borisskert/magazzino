package de.borisskert.magazzino.user;

import de.borisskert.magazzino.security.AuthenticationUserAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        return AuthenticationUserAdapter.from(authentication)
                .toUser();
    }
}
