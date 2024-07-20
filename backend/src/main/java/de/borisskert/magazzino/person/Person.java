package de.borisskert.magazzino.person;

import de.borisskert.magazzino.security.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255)")
    private Role role;

    private String authIdentifier;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAuthIdentifier() {
        return authIdentifier;
    }

    public void setAuthIdentifier(String authIdentifier) {
        this.authIdentifier = authIdentifier;
    }

    public void updateFrom(Person person) {
        this.firstName = person.firstName;
        this.lastName = person.lastName;
        this.email = person.email;
        this.role = person.role;
        this.authIdentifier = person.authIdentifier;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Person person = new Person();

        private Builder() {
        }

        public Builder id(Long id) {
            person.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            person.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            person.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            person.email = email;
            return this;
        }

        public Builder role(Role role) {
            person.role = role;
            return this;
        }

        public Builder authIdentifier(String authIdentifier) {
            person.authIdentifier = authIdentifier;
            return this;
        }

        public Person build() {
            return person;
        }
    }
}
