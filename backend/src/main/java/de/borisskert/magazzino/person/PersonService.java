package de.borisskert.magazzino.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void save(Person person) {
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
