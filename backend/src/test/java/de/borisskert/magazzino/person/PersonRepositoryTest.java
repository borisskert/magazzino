package de.borisskert.magazzino.person;

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
        person.setUsername("johndoe");
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@gmail.com");
        person.setRole(Person.Role.USER);
        person.setBirthdate(LocalDate.parse("1980-11-15"));

        Person savedPerson = personRepository.save(person);

        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getId()).isNotNull();
        assertThat(savedPerson.getUsername()).isEqualTo("johndoe");
        assertThat(savedPerson.getFirstName()).isEqualTo("John");
        assertThat(savedPerson.getLastName()).isEqualTo("Doe");
        assertThat(savedPerson.getEmail()).isEqualTo("john.doe@gmail.com");
        assertThat(savedPerson.getRole()).isEqualTo(Person.Role.USER);
        assertThat(savedPerson.getBirthdate()).isEqualTo(LocalDate.parse("1980-11-15"));

        Person foundPerson = personRepository.findById(savedPerson.getId()).orElseThrow();
        assertThat(foundPerson).isNotNull();
        assertThat(foundPerson.getId()).isNotNull();
        assertThat(foundPerson.getUsername()).isEqualTo("johndoe");
        assertThat(foundPerson.getFirstName()).isEqualTo("John");
        assertThat(foundPerson.getLastName()).isEqualTo("Doe");
        assertThat(foundPerson.getEmail()).isEqualTo("john.doe@gmail.com");
        assertThat(foundPerson.getRole()).isEqualTo(Person.Role.USER);
        assertThat(foundPerson.getBirthdate()).isEqualTo(LocalDate.parse("1980-11-15"));
    }

    @AfterEach
    void cleanup() throws Exception {
        personRepository.deleteAll();
    }

}
