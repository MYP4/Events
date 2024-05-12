package repositoryTest;

import data.entity.Event;
import data.entity.Specific;
import data.entity.User;
import data.entity.UserRole;
import data.exceptions.DBException;
import data.repositories.EventRepository;
import data.repositories.SpecificRepository;
import data.repositories.UserRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SpecificRepositoryTest {
    private final UserRepository userRepository = new UserRepository();
    private final EventRepository eventRepository = new EventRepository();
    private final SpecificRepository specificRepository = new SpecificRepository();

    @Test
    void getAllTest() throws DBException {
        var user = new User(null, "test", "test",
                UserRole.ADMINISTRATOR, "TEST",
                new BigDecimal(0), "test", "test",
                UUID.randomUUID());
        var event = new Event(null, "A", "A", user.getUid(), UUID.randomUUID());
        var specific1 = new Specific(null, event.getUid(), "test",
                10, new BigDecimal(100), "test", UUID.randomUUID());
        var specific2 = new Specific(null, event.getUid(), "test",
                10, new BigDecimal(100), "test", UUID.randomUUID());

        var specifics = new ArrayList<Specific>();
        specifics.add(specific1);
        specifics.add(specific2);

        userRepository.create(user);
        eventRepository.create(event);
        specificRepository.create(specific1);
        specificRepository.create(specific2);

        List<Specific> result = specificRepository.getAll();

        userRepository.delete(user.getUid());

        assertTrue(specifics.get(0).equals(result.get(result.size() - 2)) &&
                specifics.get(1).equals(result.get(result.size() - 1)));
    }

    @Test
    void GetByIdTest() throws DBException {
        var user = userRepository.create( new User(null, "test", "test",
                UserRole.ADMINISTRATOR, "TEST",
                new BigDecimal(0), "test", "test",
                UUID.randomUUID()));
        var event = eventRepository.create(new Event(null, "A", "A", user.getUid(), UUID.randomUUID()));
        var specific = specificRepository.create(new Specific(null, event.getUid(), "test",
                10, new BigDecimal(100), "test", UUID.randomUUID()));

        var result = specificRepository.getById(specific.getUid());
        userRepository.delete(user.getUid());

        assertEquals(specific, result);
    }

    @Test
    void createTest() throws DBException {
        var user = userRepository.create( new User(null, "test", "test",
                UserRole.ADMINISTRATOR, "TEST",
                new BigDecimal(0), "test", "test",
                UUID.randomUUID()));
        var event = eventRepository.create(new Event(null, "A", "A", user.getUid(), UUID.randomUUID()));
        var specific = specificRepository.create(new Specific(null, event.getUid(), "test",
                10, new BigDecimal(100), "test", UUID.randomUUID()));
        var specifics = specificRepository.getAll();
        if (specifics.isEmpty()) {
            fail();
        }
        var specific1 = specifics.get(specifics.size() - 1);
        userRepository.delete(user.getUid());

        assertEquals(specific, specific1);
    }

    @Test
    void updateTest() throws DBException {
        var user = userRepository.create( new User(null, "test", "test",
                UserRole.ADMINISTRATOR, "TEST",
                new BigDecimal(0), "test", "test",
                UUID.randomUUID()));
        var event = eventRepository.create(new Event(null, "A", "A", user.getUid(), UUID.randomUUID()));
        var specific = specificRepository.create(new Specific(null, event.getUid(), "test",
                10, new BigDecimal(100), "test", UUID.randomUUID()));

        specific.setPrice(new BigDecimal(1000));
        specificRepository.update(specific);
        var result = specificRepository.getById(specific.getUid());

        userRepository.delete(user.getUid());

        assertEquals(result, specific);
    }

    @Test
    void deleteTest() throws DBException {
        var user = userRepository.create( new User(null, "test", "test",
                UserRole.ADMINISTRATOR, "TEST",
                new BigDecimal(0), "test", "test",
                UUID.randomUUID()));
        var event = eventRepository.create(new Event(null, "A", "A", user.getUid(), UUID.randomUUID()));
        var specific = specificRepository.create(new Specific(null, event.getUid(), "test",
                10, new BigDecimal(100), "test", UUID.randomUUID()));

        var result = specificRepository.delete(specific.getUid());
        userRepository.delete(user.getUid());

        assertNull(specificRepository.getById(specific.getUid()));
    }
}
