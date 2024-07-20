package de.borisskert.magazzino.security;

import de.borisskert.magazzino.person.Person;
import de.borisskert.magazzino.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class KeycloakAuthClientService implements OAuth2AuthorizedClientService {

    private final PersonService personService;

    @Autowired
    public KeycloakAuthClientService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        return null;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        if (principal instanceof OAuth2AuthenticationToken) {
            saveOrUpdateUser((OAuth2AuthenticationToken) principal);
        }
    }

    private void saveOrUpdateUser(OAuth2AuthenticationToken principal) {
        Person person = PersonOauth2Adapter.from(principal)
                .toPerson();

        personService.save(person);
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        return;
    }
}
