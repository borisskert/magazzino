package de.borisskert.magazzino.person;

import de.borisskert.magazzino.security.Role;
import de.borisskert.magazzino.time.TimeProvider;
import de.borisskert.magazzino.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class PersonServiceTest {
    private static final LocalDateTime NOW = LocalDateTime.of(2024, 7, 20, 20, 15, 30, 456345865);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @MockBean
    private TimeProvider timeProvider;

    @BeforeEach
    void setup() throws Exception {
        when(timeProvider.now()).thenReturn(NOW);
    }

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
                .lastLogin(NOW)
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
        assertThat(savedPerson.getLastLogin()).isCloseTo(NOW, within(1, ChronoUnit.MICROS));
    }

    @Test
    void shouldUpdateExistingPerson() throws Exception {
        Person personToSave = Person.builder()
                .firstName("Max")
                .lastName("Mustermann")
                .email("max.mustermann@example.com")
                .role(Role.EMPLOYEE)
                .authIdentifier("107b61c4-7f1f-4568-bd27-5d889b8ca2f0")
                .lastLogin(LocalDateTime.of(2023, 12, 3, 13, 37, 23, 472658294))
                .build();

        personRepository.save(personToSave);

        LocalDateTime updatedLastLogin = LocalDateTime.of(2024, 4, 29, 6, 34, 36, 876342567);

        Person personToUpdate = Person.builder()
                .firstName("Maximilian")
                .lastName("Musterfrau")
                .email("max.mustermann+123@example.com")
                .role(Role.ADMIN)
                .authIdentifier("107b61c4-7f1f-4568-bd27-5d889b8ca2f0")
                .lastLogin(updatedLastLogin)
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
        assertThat(updatedPerson.getLastLogin()).isCloseTo(updatedLastLogin, within(1, ChronoUnit.MICROS));
    }

    @Test
    void shouldCreatePersonFromUser() throws Exception {
        User user = User.builder()
                .firstName("Max")
                .lastName("Mustermann")
                .email("max.musermann@email.com")
                .role(Role.EMPLOYEE)
                .authIdentifier("107b61c4-7f1f-4568-bd27-5d889b8ca2f0")
                .build();

        personService.save(user);

        assertEquals(1, personRepository.count());

        Person savedPerson = personRepository.findAll().getFirst();
        assertThat(savedPerson.getId()).isNotNull();
        assertThat(savedPerson.getFirstName()).isEqualTo("Max");
        assertThat(savedPerson.getLastName()).isEqualTo("Mustermann");
        assertThat(savedPerson.getEmail()).isEqualTo("max.musermann@email.com");
        assertThat(savedPerson.getRole()).isEqualTo(Role.EMPLOYEE);
        assertThat(savedPerson.getAuthIdentifier()).isEqualTo("107b61c4-7f1f-4568-bd27-5d889b8ca2f0");
        assertThat(savedPerson.getLastLogin()).isCloseTo(NOW, within(1, ChronoUnit.MICROS));
    }

    @Test
    void shouldUpdatePersonFromUser() throws Exception {
        Person personToSave = Person.builder()
                .firstName("Max")
                .lastName("Mustermann")
                .email("max.mustermann@example.com")
                .role(Role.EMPLOYEE)
                .authIdentifier("107b61c4-7f1f-4568-bd27-5d889b8ca2f0")
                .lastLogin(LocalDateTime.of(2023, 12, 3, 13, 37, 23, 472658294))
                .build();

        personRepository.save(personToSave);

        User personToUpdate = User.builder()
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
        assertThat(updatedPerson.getLastLogin()).isCloseTo(NOW, within(1, ChronoUnit.MICROS));
    }

    @AfterEach
    void cleanup() throws Exception {
        personRepository.deleteAll();
    }
}
