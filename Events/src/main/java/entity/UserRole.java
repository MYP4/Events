package entity;

import java.util.Arrays;
import java.util.Optional;

public enum UserRole {
    ADMINISTRATOR,
    REGULAR;

    public static Optional<UserRole> find(final String role) {
        return Arrays
                .stream(UserRole.values())
                .filter(userRole -> userRole.name().equals(role))
                .findFirst();
    }
}
