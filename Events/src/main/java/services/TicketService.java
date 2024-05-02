package services;

import data.entity.Specific;
import data.entity.Ticket;
import data.entity.User;
import data.exceptions.CreationException;
import data.mappers.TicketModelToTicketMapper;
import data.mappers.TicketToTicketModelMapper;
import data.models.TicketModel;
import data.repositories.SpecificRepository;
import data.repositories.TicketRepository;
import data.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

public class TicketService {
    private final TicketRepository ticketRepository;
    private final SpecificRepository specificRepository;
    private final UserRepository userRepository;
    private final TicketModelToTicketMapper ticketModelToTicketMapper;
    private final TicketToTicketModelMapper ticketToTicketModelMapper;

    public TicketService(TicketRepository ticketRepository,
                         SpecificRepository specificRepository,
                         UserRepository userRepository,
                         TicketModelToTicketMapper ticketModelToTicketMapper,
                         TicketToTicketModelMapper ticketToTicketModelMapper) {
        this.ticketRepository = ticketRepository;
        this.specificRepository = specificRepository;
        this.userRepository = userRepository;
        this.ticketModelToTicketMapper = ticketModelToTicketMapper;
        this.ticketToTicketModelMapper = ticketToTicketModelMapper;
    }

    public List<TicketModel> getAll() {
        List<Ticket> tickets = ticketRepository.getAll();
        return tickets.stream().map(ticketToTicketModelMapper::map).toList();
    }

    public TicketModel getById(UUID id) {
        return ticketToTicketModelMapper.map(ticketRepository.getById(id));
    }

    public TicketModel create(TicketModel ticketModel) throws CreationException {
        try {
            User user = userRepository.getById(ticketModel.getUserId());
            if (user == null)
                throw new CreationException("Failed to create ticket: user not exists");

            Specific specific = specificRepository.getById(ticketModel.getSpecificId());
            if (specific == null)
                throw new CreationException("Failed to create ticket: specific not exists");

            Ticket ticket = ticketModelToTicketMapper.map(ticketModel);
            return ticketToTicketModelMapper.map(ticketRepository.create(ticket));

        } catch (Exception e) {
            throw new CreationException("Failed to create ticket: " + e.getMessage());
        }
    }

    public void update(TicketModel ticketModel) {
        ticketRepository.update(ticketModelToTicketMapper.map(ticketModel));
    }

    public void delete(UUID id) {
        ticketRepository.delete(id);
    }
}
