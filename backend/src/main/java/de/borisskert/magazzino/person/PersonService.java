package de.borisskert.magazzino.person;

import de.borisskert.magazzino.time.TimeProvider;
import de.borisskert.magazzino.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final TimeProvider timeProvider;

    @Autowired
    public PersonService(PersonRepository personRepository, TimeProvider timeProvider) {
        this.personRepository = personRepository;
        this.timeProvider = timeProvider;
    }

    public void save(User user) {
        Person person = Person.builder()
                .email(user.email())
                .firstName(user.firstName())
                .lastName(user.lastName())
                .role(user.role())
                .authIdentifier(user.authIdentifier())
                .lastLogin(timeProvider.now())
                .build();

        save(person);
    }

    private void save(Person person) {
        personRepository.findByAuthIdentifier(person.getAuthIdentifier())
                .ifPresentOrElse(
                        existingPerson -> {
                            existingPerson.updateFrom(person);
                            personRepository.save(existingPerson);
                        },
                        () -> personRepository.save(person)
                );
    }
}
