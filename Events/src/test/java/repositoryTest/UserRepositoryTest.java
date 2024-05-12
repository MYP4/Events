package repositoryTest;

import data.entity.User;
import data.entity.UserRole;
import data.exceptions.DBException;
import data.repositories.UserRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class UserRepositoryTest {
    private final UserRepository userRepository = new UserRepository();

    @Test
    void getAllTest() throws DBException {
        var user1 = new User(null, "test1", "test1",
                UserRole.ADMINISTRATOR, "TEST1",
                new BigDecimal(0), "test1", "test1",
                UUID.randomUUID());
        var user2 = new User(null, "test2", "test2",
                UserRole.ADMINISTRATOR, "TEST2",
                new BigDecimal(0), "test2", "test2",
                UUID.randomUUID());
        var users = new ArrayList<User>();

        users.add(user1);
        users.add(user2);
        userRepository.create(user1);
        userRepository.create(user2);
        var result = userRepository.getAll();

        userRepository.delete(user1.getUid());
        userRepository.delete(user2.getUid());

        var test1 = users.get(0).equals(result.get(result.size() - 2));
        var test2 = users.get(1).equals(result.get(result.size() - 1));

        assertTrue(users.get(0).equals(result.get(result.size() - 2)) &&
                users.get(1).equals(result.get(result.size() - 1)));
    }

    @Test
    void getById() throws DBException {
        var user = userRepository.create(new User(null, "test1", "test1",
                UserRole.ADMINISTRATOR, "TEST1",
                new BigDecimal(0), "test1", "test1",
                UUID.randomUUID()));
        var result = userRepository.getById(user.getUid());

        userRepository.delete(user.getUid());
        assertEquals(user, result);
    }

    @Test
    void updateTest() throws DBException {
        var user = userRepository.create(new User(null, "test1", "test1",
                UserRole.ADMINISTRATOR, "TEST1",
                new BigDecimal(0), "test1", "test1",
                UUID.randomUUID()));
        user.setRole(UserRole.REGULAR);
        userRepository.update(user);
        var result = userRepository.getById(user.getUid());

        userRepository.delete(user.getUid());
        assertEquals(result, user);
    }

    @Test
    void deleteTest() throws DBException {
        var user = userRepository.create(new User(null, "test1", "test1",
                UserRole.ADMINISTRATOR, "TEST1",
                new BigDecimal(0), "test1", "test1",
                UUID.randomUUID()));
        userRepository.delete(user.getUid());

        assertNull(userRepository.getById(user.getUid()));
    }
}
