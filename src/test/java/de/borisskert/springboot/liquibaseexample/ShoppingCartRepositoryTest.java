package de.borisskert.springboot.liquibaseexample;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private PersonRepository personRepository;

    private Person person;

    @BeforeEach
    void setup() throws Exception {
        person = setupPerson();
    }

    @Test
    void shouldCreateEmptyShoppingCart() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomerId(person.getId());
        shoppingCart.setCheckedOut(false);

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        assertNotNull(savedShoppingCart);
        assertNotNull(savedShoppingCart.getId());
        assertEquals(shoppingCart.getCustomerId(), savedShoppingCart.getCustomerId());
        assertEquals(shoppingCart.getCheckedOut(), savedShoppingCart.getCheckedOut());

        ShoppingCart foundShoppingCart = shoppingCartRepository.findById(savedShoppingCart.getId()).orElseThrow();
        assertNotNull(foundShoppingCart);
        assertNotNull(foundShoppingCart.getId());
        assertEquals(shoppingCart.getCustomerId(), foundShoppingCart.getCustomerId());
        assertEquals(shoppingCart.getCheckedOut(), foundShoppingCart.getCheckedOut());
    }

    @Test
    void shouldCreateCheckedOutShoppingCart() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomerId(person.getId());
        shoppingCart.setCheckedOut(true);

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        assertNotNull(savedShoppingCart);
        assertNotNull(savedShoppingCart.getId());
        assertEquals(shoppingCart.getCustomerId(), savedShoppingCart.getCustomerId());
        assertEquals(shoppingCart.getCheckedOut(), savedShoppingCart.getCheckedOut());
    }

    @Test
    void shouldNotCreateShoppingCartForNotExistingPerson() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomerId(Long.MAX_VALUE);
        shoppingCart.setCheckedOut(false);

        assertThatThrownBy(() -> shoppingCartRepository.save(shoppingCart))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @AfterEach
    void cleanup() throws Exception {
        shoppingCartRepository.deleteAll();
        personRepository.deleteAll();
    }

    private Person setupPerson() {
        Person person = new Person();
        person.setUsername("johndoe");
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@gmail.com");
        person.setRole(Person.Role.USER);
        person.setBirthdate(LocalDate.parse("1980-11-15"));

        return personRepository.save(person);
    }
}
