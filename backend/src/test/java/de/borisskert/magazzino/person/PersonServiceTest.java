package de.borisskert.magazzino.person;

import de.borisskert.magazzino.security.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class PersonServiceTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Test
    void shouldNotContainAnyPersons() throws Exception {
        assertEquals(0, personRepository.count());
    }

    @Test
    void shouldCreateNewPerson() throws Exception {
        Person personToSave = Person.builder()
                .firstName("Max")
                .lastName("Mustermann")
                .email("max.mustermann@example.com")
                .role(Role.EMPLOYEE)
                .authIdentifier("0bc226b8-3f0c-4dc2-9812-9aa91e56ff6d")
                .build();

        personService.save(personToSave);

        assertEquals(1, personRepository.count());

        Person savedPerson = personRepository.findAll().getFirst();
        assertThat(savedPerson.getId()).isNotNull();
        assertThat(savedPerson.getFirstName()).isEqualTo("Max");
        assertThat(savedPerson.getLastName()).isEqualTo("Mustermann");
        assertThat(savedPerson.getEmail()).isEqualTo("max.mustermann@example.com");
        assertThat(savedPerson.getRole()).isEqualTo(Role.EMPLOYEE);
        assertThat(savedPerson.getAuthIdentifier()).isEqualTo("0bc226b8-3f0c-4dc2-9812-9aa91e56ff6d");
    }

    @Test
    void shouldUpdateExistingPerson() throws Exception {
        Person personToSave = Person.builder()
                .firstName("Max")
                .lastName("Mustermann")
                .email("max.mustermann@example.com")
                .role(Role.EMPLOYEE)
                .authIdentifier("107b61c4-7f1f-4568-bd27-5d889b8ca2f0")
                .build();

        personRepository.save(personToSave);

        Person personToUpdate = Person.builder()
                .firstName("Maximilian")
                .lastName("Musterfrau")
                .email("max.mustermann+123@example.com")
                .role(Role.ADMIN)
                .authIdentifier("107b61c4-7f1f-4568-bd27-5d889b8ca2f0")
                .build();

        personService.save(personToUpdate);

        assertEquals(1, personRepository.count());

        Person updatedPerson = personRepository.findAll().getFirst();
        assertThat(updatedPerson.getId()).isNotNull();
        assertThat(updatedPerson.getFirstName()).isEqualTo("Maximilian");
        assertThat(updatedPerson.getLastName()).isEqualTo("Musterfrau");
        assertThat(updatedPerson.getEmail()).isEqualTo("max.mustermann+123@example.com");
        assertThat(updatedPerson.getRole()).isEqualTo(Role.ADMIN);
        assertThat(updatedPerson.getAuthIdentifier()).isEqualTo("107b61c4-7f1f-4568-bd27-5d889b8ca2f0");
    }

    @AfterEach
    void cleanup() throws Exception {
        personRepository.deleteAll();
    }
}
