package de.borisskert.magazzino.user;

import de.borisskert.magazzino.security.Role;

public record User(
        String authIdentifier,
        String firstName,
        String lastName,
        String email,
        Role role
) {

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String authIdentifier;
        private String firstName;
        private String lastName;
        private String email;
        private Role role;

        private UserBuilder() {
        }

        public UserBuilder authIdentifier(String authIdentifier) {
            this.authIdentifier = authIdentifier;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(authIdentifier, firstName, lastName, email, role);
        }
    }
}
