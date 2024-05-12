package repositoryTest;

import data.entity.Event;
import data.entity.Specific;
import data.entity.Ticket;
import data.entity.User;
import data.entity.UserRole;
import data.exceptions.DBException;
import data.repositories.EventRepository;
import data.repositories.SpecificRepository;
import data.repositories.TicketRepository;
import data.repositories.UserRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TicketRepositoryTest {
    private final UserRepository userRepository = new UserRepository();
    private final EventRepository eventRepository = new EventRepository();
    private final SpecificRepository specificRepository = new SpecificRepository();
    private final TicketRepository ticketRepository = new TicketRepository();

    @Test
    void getAllTest() throws DBException {
        var user = new User(null, "test", "test",
                UserRole.ADMINISTRATOR, "TEST",
                new BigDecimal(0), "test", "test",
                UUID.randomUUID());
        var event = new Event(null, "A", "A", user.getUid(), UUID.randomUUID());
        var specific = new Specific(null, event.getUid(), "test",
                10, new BigDecimal(100), "test", UUID.randomUUID());
        var ticket1 = new Ticket(null, user.getUid(), specific.getUid(), 1, UUID.randomUUID());
        var ticket2 = new Ticket(null, user.getUid(), specific.getUid(), 1, UUID.randomUUID());

        var tickets = new ArrayList<Ticket>();
        tickets.add(ticket1);
        tickets.add(ticket2);

        userRepository.create(user);
        eventRepository.create(event);
        specificRepository.create(specific);
        ticketRepository.create(ticket1);
        ticketRepository.create(ticket2);

        var result = ticketRepository.getAll();
        userRepository.delete(user.getUid());

        assertTrue(tickets.get(0).equals(result.get(result.size() - 2)) &&
                tickets.get(1).equals(result.get(result.size() - 1)));
    }

    @Test
    void getByIdTest() throws DBException {
        var user = userRepository.create(new User(null, "test", "test",
                UserRole.ADMINISTRATOR, "TEST",
                new BigDecimal(0), "test", "test",
                UUID.randomUUID()));
        var event = eventRepository.create(new Event(null, "A", "A", user.getUid(), UUID.randomUUID()));
        var specific = specificRepository.create(new Specific(null, event.getUid(), "test",
                10, new BigDecimal(100), "test", UUID.randomUUID()));
        var ticket = ticketRepository.create(new Ticket(null, user.getUid(), specific.getUid(), 1, UUID.randomUUID()));

        var result = ticketRepository.getById(ticket.getUid());
        userRepository.delete(user.getUid());

        assertEquals(ticket, result);
    }

    @Test
    void createTest() throws DBException {
        var user = userRepository.create(new User(null, "test", "test",
                UserRole.ADMINISTRATOR, "TEST",
                new BigDecimal(0), "test", "test",
                UUID.randomUUID()));
        var event = eventRepository.create(new Event(null, "A", "A", user.getUid(), UUID.randomUUID()));
        var specific = specificRepository.create(new Specific(null, event.getUid(), "test",
                10, new BigDecimal(100), "test", UUID.randomUUID()));
        var ticket = ticketRepository.create(new Ticket(null, user.getUid(), specific.getUid(), 1, UUID.randomUUID()));
        var tickets = ticketRepository.getAll();
        if (tickets.isEmpty()) {
            fail();
        }
        var ticket1 = tickets.get(tickets.size() - 1);
        userRepository.delete(user.getUid());

        assertEquals(ticket, ticket1);
    }

    @Test
    void updateTest() throws DBException {
        var user = userRepository.create(new User(null, "test", "test",
                UserRole.ADMINISTRATOR, "TEST",
                new BigDecimal(0), "test", "test",
                UUID.randomUUID()));
        var event = eventRepository.create(new Event(null, "A", "A", user.getUid(), UUID.randomUUID()));
        var specific = specificRepository.create(new Specific(null, event.getUid(), "test",
                10, new BigDecimal(100), "test", UUID.randomUUID()));
        var ticket = ticketRepository.create(new Ticket(null, user.getUid(), specific.getUid(), 1, UUID.randomUUID()));

        ticket.setStatus(0);
        ticketRepository.update(ticket);
        var result = ticketRepository.getById(ticket.getUid());

        userRepository.delete(user.getUid());
        assertEquals(result, ticket);
    }

    @Test
    void deleteTest() throws DBException {
        var user = userRepository.create(new User(null, "test", "test",
                UserRole.ADMINISTRATOR, "TEST",
                new BigDecimal(0), "test", "test",
                UUID.randomUUID()));
        var event = eventRepository.create(new Event(null, "A", "A", user.getUid(), UUID.randomUUID()));
        var specific = specificRepository.create(new Specific(null, event.getUid(), "test",
                10, new BigDecimal(100), "test", UUID.randomUUID()));
        var ticket = ticketRepository.create(new Ticket(null, user.getUid(), specific.getUid(), 1, UUID.randomUUID()));

        var result = ticketRepository.delete(ticket.getUid());
        userRepository.delete(user.getUid());

        assertNull(ticketRepository.getById(ticket.getUid()));
    }
}
