package services;

import data.entity.Specific;
import data.entity.Ticket;
import data.entity.User;
import data.exceptions.CreationException;
import data.exceptions.DBException;
import data.mappers.TicketModelToTicketMapper;
import data.mappers.TicketToTicketModelMapper;
import data.models.TicketModel;
import data.repositories.SpecificRepository;
import data.repositories.TicketRepository;
import data.repositories.UserRepository;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.UUID;

public class TicketService {
    private final TicketRepository ticketRepository;
    private final SpecificRepository specificRepository;
    private final UserRepository userRepository;
    private final TicketModelToTicketMapper ticketModelToTicketMapper;
    private final TicketToTicketModelMapper ticketToTicketModelMapper;
    private static final Logger logger = Logger.getLogger(TicketService.class);

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

    public List<TicketModel> getAll() throws DBException {
        try {
            List<Ticket> tickets = ticketRepository.getAll();
            return tickets.stream().map(ticketToTicketModelMapper::map).toList();
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public TicketModel getById(UUID id) throws DBException {
        try {
            return ticketToTicketModelMapper.map(ticketRepository.getById(id));
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public TicketModel create(TicketModel ticketModel) throws DBException {
        try {
            User user = userRepository.getById(ticketModel.getUserId());
            if (user == null) {
                logger.error("Failed to create ticket: user not exists");
                throw new DBException("Failed to create ticket: user not exists");
            }
            Specific specific = specificRepository.getById(ticketModel.getSpecificId());
            if (specific == null) {
                logger.error("Failed to create ticket: specific not exists");
                throw new DBException("Failed to create ticket: specific not exists");
            }
            Ticket ticket = ticketModelToTicketMapper.map(ticketModel);
            return ticketToTicketModelMapper.map(ticketRepository.create(ticket));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DBException("Failed to create ticket: " + e.getMessage());
        }
    }

    public void update(TicketModel ticketModel)  throws DBException{
        try {
            ticketRepository.update(ticketModelToTicketMapper.map(ticketModel));
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public void delete(UUID id) throws DBException {
        try {
            ticketRepository.delete(id);
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }
}
