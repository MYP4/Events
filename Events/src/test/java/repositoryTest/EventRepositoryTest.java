package repositoryTest;

import data.entity.Event;
import data.entity.User;
import data.entity.UserRole;
import data.exceptions.DBException;
import data.repositories.EventRepository;
import data.repositories.UserRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
public class EventRepositoryTest {
    private final EventRepository eventRepository = new EventRepository();
    private final UserRepository userRepository = new UserRepository();
    @Test
    void getAllTest() throws DBException {
        var user = new User(null, "test", "test",
                UserRole.ADMINISTRATOR, "TEST",
                new BigDecimal(0), "test", "test",
                UUID.randomUUID());
        var event1 = new Event(null, "A", "A", user.getUid(), UUID.randomUUID());
        var event2 = new Event(null, "B", "B", user.getUid(), UUID.randomUUID());
        var events = new ArrayList<Event>();
        events.add(event1);
        events.add(event2);
        userRepository.create(user);
        eventRepository.create(event1);
        eventRepository.create(event2);
        List<Event> result = eventRepository.getAll();

        events.get(0).setId(result.get(result.size() - 2).getId());
        events.get(1).setId(result.get(result.size() - 1).getId());
        assertTrue(events.get(0).equals(result.get(0)) &&
                events.get(1).equals(result.get(1)));
        for (Event event : events) {
            eventRepository.delete(event.getUid());
        }
        userRepository.delete(user.getUid());
    }
    @Test
    void getByIdTest() throws DBException{
        var user = userRepository.create(new User(null, "test", "test", UserRole.ADMINISTRATOR, "TEST", new BigDecimal(0), "test", "test", UUID.randomUUID()));
        var event = eventRepository.create(new Event(null, "A", "A", user.getUid(), UUID.randomUUID()));
        var result = eventRepository.getById(event.getUid());
        assertEquals(event, result);
        eventRepository.delete(event.getUid());
        userRepository.delete(user.getUid());
    }
    @Test
    void createTest() throws DBException {
        var user = userRepository.create(new User(null, "test", "test", UserRole.ADMINISTRATOR, "TEST", new BigDecimal(0), "test", "test", UUID.randomUUID()));
        var event = eventRepository.create(new Event(null, "A", "A", user.getUid(), UUID.randomUUID()));
        var events = eventRepository.getAll();
        if (events.isEmpty()) {
            fail();
        }
        var event1 = events.get(events.size() - 1);
        event.setId(event1.getId());
        assertEquals(event, event1);

        eventRepository.delete(event.getUid());
        userRepository.delete(user.getUid());
    }
    @Test
    void updateTest() throws DBException {
        var user = userRepository.create(new User(null, "test", "test", UserRole.ADMINISTRATOR, "TEST", new BigDecimal(0), "test", "test", UUID.randomUUID()));
        var eventToUpdate = new Event(null, "A", "A", user.getUid(), UUID.randomUUID());
        var event = eventRepository.create(eventToUpdate);

        eventToUpdate.setId(event.getId());
        eventToUpdate.setName("BBB");
        eventRepository.update(eventToUpdate);

        var result = eventRepository.getById(event.getUid());
        assertEquals(result, eventToUpdate);

        eventRepository.delete(event.getUid());
        userRepository.delete(user.getUid());
    }
    @Test
    void deleteTest() throws DBException {
        var user = userRepository.create(new User(null, "test", "test", UserRole.ADMINISTRATOR, "TEST", new BigDecimal(0), "test", "test", UUID.randomUUID()));
        var event = eventRepository.create(new Event(null, "A", "A", user.getUid(), UUID.randomUUID()));

        eventRepository.delete(event.getUid());

        assertNull(eventRepository.getById(event.getUid()));
        userRepository.delete(user.getUid());
    }
}
