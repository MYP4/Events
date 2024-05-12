package serviceTest;

import data.entity.Specific;
import data.entity.Ticket;
import data.entity.User;
import data.exceptions.DBException;
import data.mappers.TicketModelToTicketMapper;
import data.mappers.TicketToTicketModelMapper;
import data.models.TicketModel;
import data.repositories.SpecificRepository;
import data.repositories.TicketRepository;
import data.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.TicketService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private SpecificRepository specificRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TicketModelToTicketMapper ticketModelToTicketMapper;

    @Mock
    private TicketToTicketModelMapper ticketToTicketModelMapper;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void testGetAll() throws DBException {
        // Setup
        List<Ticket> tickets = List.of(new Ticket(), new Ticket());
        when(ticketRepository.getAll()).thenReturn(tickets);
        when(ticketToTicketModelMapper.map(any(Ticket.class))).thenReturn(new TicketModel());

        // Execute
        List<TicketModel> result = ticketService.getAll();

        // Verify
        verify(ticketRepository, times(1)).getAll();
        verify(ticketToTicketModelMapper, times(tickets.size())).map(any(Ticket.class));
        assertEquals(tickets.size(), result.size());
    }

    @Test
    void testGetById() throws DBException {
        // Setup
        UUID id = UUID.randomUUID();
        Ticket ticket = new Ticket();
        ticket.setUid(id);
        when(ticketRepository.getById(id)).thenReturn(ticket);
        when(ticketToTicketModelMapper.map(ticket)).thenReturn(new TicketModel());

        // Execute
        TicketModel result = ticketService.getById(id);

        // Verify
        verify(ticketRepository, times(1)).getById(id);
        verify(ticketToTicketModelMapper, times(1)).map(ticket);
        assertNotNull(result);
    }

//    @Test
//    void testCreate() throws DBException {
//        // Setup
//        UUID userId = UUID.randomUUID();
//        UUID specificId = UUID.randomUUID();
//        TicketModel ticketModel = new TicketModel();
//        ticketModel.setUserId(userId);
//        ticketModel.setSpecificId(specificId);
//        User user = new User();
//        user.setUid(userId);
//        Specific specific = new Specific();
//        specific.setUid(specificId);
//        when(userRepository.getById(userId)).thenReturn(user);
//        when(specificRepository.getById(specificId)).thenReturn(specific);
//        when(ticketRepository.getSpecificAll(specificId)).thenReturn(List.of());
//        when(ticketRepository.create(any(Ticket.class))).thenReturn(new Ticket());
//        when(ticketModelToTicketMapper.map(ticketModel)).thenReturn(new Ticket());
//        when(ticketToTicketModelMapper.map(any(Ticket.class))).thenReturn(ticketModel);
//
//        TicketModel result = ticketService.create(ticketModel);
//
//
//        verify(userRepository, times(1)).getById(userId);
//        verify(specificRepository, times(1)).getById(specificId);
//        verify(ticketRepository, times(1)).getSpecificAll(specificId);
//        verify(ticketRepository, times(1)).create(any(Ticket.class));
//        verify(ticketModelToTicketMapper, times(1)).map(ticketModel);
//        verify(ticketToTicketModelMapper, times(1)).map(any(Ticket.class));
//        assertNotNull(result);
//    }

    @Test
    void testDelete() throws DBException {
        // Setup
        UUID id = UUID.randomUUID();
        when(ticketRepository.delete(id)).thenReturn(true);

        // Execute
        ticketService.delete(id);

        // Verify
        verify(ticketRepository, times(1)).delete(id);
    }
}