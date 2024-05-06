package services;

import data.entity.Event;
import data.entity.User;
import data.exceptions.CreationException;
import data.exceptions.DBException;
import data.mappers.EventModelToEventMapper;
import data.mappers.EventToEventModelMapper;
import data.models.EventModel;
import data.repositories.EventRepository;
import data.repositories.UserRepository;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.UUID;

public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventToEventModelMapper eventToEventModelMapper;
    private final EventModelToEventMapper eventModelToEventMapper;
    private static final Logger logger = Logger.getLogger(EventService.class);

    public EventService(EventRepository eventRepository,
                        UserRepository userRepository,
                        EventToEventModelMapper eventToEventModelMapper,
                        EventModelToEventMapper eventModelToEventMapper) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventToEventModelMapper = eventToEventModelMapper;
        this.eventModelToEventMapper = eventModelToEventMapper;
    }

    public List<EventModel> getAll() throws DBException {
        try {
            List<Event> events = eventRepository.getAll();
            return events.stream().map(eventToEventModelMapper::map).toList();
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public EventModel getById(UUID id) throws DBException  {
        try {
            return eventToEventModelMapper.map(eventRepository.getById(id));
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public EventModel create(EventModel eventModel) throws DBException {
        try {
            User user = userRepository.getById(eventModel.getAdminId());
            if (user == null) {
                logger.error("Failed to create event: user not exists");
                throw new DBException("Failed to create event: user not exists");
            }
            Event event = eventModelToEventMapper.map(eventModel);
            return eventToEventModelMapper.map(eventRepository.create(event));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DBException("Failed to create event: " + e.getMessage());
        }
    }

    public void update(EventModel eventModel) throws DBException{
        try {
            eventRepository.update(eventModelToEventMapper.map(eventModel));
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public void delete(UUID id) throws DBException{
        try {
            eventRepository.delete(id);
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }
}
