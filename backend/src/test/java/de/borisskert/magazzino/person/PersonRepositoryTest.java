package de.borisskert.magazzino.person;

import de.borisskert.magazzino.security.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the {@link PersonRepository} against the h2 database.
 */
@SpringBootTest
@ActiveProfiles("test")
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void shouldCreatePerson() throws Exception {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@gmail.com");
        person.setRole(Role.EMPLOYEE);

        Person savedPerson = personRepository.save(person);

        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getId()).isNotNull();
        assertThat(savedPerson.getFirstName()).isEqualTo("John");
        assertThat(savedPerson.getLastName()).isEqualTo("Doe");
        assertThat(savedPerson.getEmail()).isEqualTo("john.doe@gmail.com");
        assertThat(savedPerson.getRole()).isEqualTo(Role.EMPLOYEE);

        Person foundPerson = personRepository.findById(savedPerson.getId()).orElseThrow();
        assertThat(foundPerson).isNotNull();
        assertThat(foundPerson.getId()).isNotNull();
        assertThat(foundPerson.getFirstName()).isEqualTo("John");
        assertThat(foundPerson.getLastName()).isEqualTo("Doe");
        assertThat(foundPerson.getEmail()).isEqualTo("john.doe@gmail.com");
        assertThat(foundPerson.getRole()).isEqualTo(Role.EMPLOYEE);
    }

    @AfterEach
    void cleanup() throws Exception {
        personRepository.deleteAll();
    }

}
