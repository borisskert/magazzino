package de.borisskert.magazzino.shoppingcart;

import de.borisskert.magazzino.person.Person;
import de.borisskert.magazzino.person.PersonRepository;
import de.borisskert.magazzino.security.Role;
import de.borisskert.magazzino.shoppingcart.persistence.ShoppingCart;
import de.borisskert.magazzino.shoppingcart.persistence.ShoppingCartRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

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
        shoppingCart.setCustomer(person);
        shoppingCart.setCheckedOut(false);

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        assertThat(savedShoppingCart).isNotNull();
        assertThat(savedShoppingCart.getId()).isNotNull();
        assertThat(savedShoppingCart)
                .extracting(ShoppingCart::getCustomer)
                .extracting(Person::getId)
                .isEqualTo(person.getId());
        assertThat(savedShoppingCart.isCheckedOut()).isEqualTo(shoppingCart.isCheckedOut());

        ShoppingCart foundShoppingCart = shoppingCartRepository.findById(savedShoppingCart.getId()).orElseThrow();

        assertThat(foundShoppingCart).isNotNull();
        assertThat(foundShoppingCart.getId()).isEqualTo(savedShoppingCart.getId());
        assertThat(foundShoppingCart)
                .extracting(ShoppingCart::getCustomer)
                .extracting(Person::getId)
                .isEqualTo(person.getId());
        assertThat(foundShoppingCart.isCheckedOut()).isEqualTo(savedShoppingCart.isCheckedOut());
    }

    @Test
    void shouldCreateCheckedOutShoppingCart() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(person);
        shoppingCart.setCheckedOut(true);

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        assertThat(savedShoppingCart).isNotNull();
        assertThat(savedShoppingCart.getId()).isNotNull();
        assertThat(savedShoppingCart)
                .extracting(ShoppingCart::getCustomer)
                .extracting(Person::getId)
                .isEqualTo(person.getId());
        assertThat(savedShoppingCart.isCheckedOut()).isEqualTo(shoppingCart.isCheckedOut());
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
        person.setRole(Role.EMPLOYEE);
        person.setBirthdate(LocalDate.parse("1980-11-15"));

        return personRepository.save(person);
    }
}
