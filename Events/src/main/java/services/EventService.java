package services;

import data.entity.Event;
import data.entity.User;
import data.exceptions.CreationException;
import data.mappers.EventModelToEventMapper;
import data.mappers.EventToEventModelMapper;
import data.models.EventModel;
import data.repositories.EventRepository;
import data.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventToEventModelMapper eventToEventModelMapper;
    private final EventModelToEventMapper eventModelToEventMapper;

    public EventService(EventRepository eventRepository,
                        UserRepository userRepository,
                        EventToEventModelMapper eventToEventModelMapper,
                        EventModelToEventMapper eventModelToEventMapper) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventToEventModelMapper = eventToEventModelMapper;
        this.eventModelToEventMapper = eventModelToEventMapper;
    }

    public List<EventModel> getAll() {
        List<Event> events = eventRepository.getAll();
        return events.stream().map(eventToEventModelMapper::map).toList();
    }

    public EventModel getById(UUID id) {
        return eventToEventModelMapper.map(eventRepository.getById(id));
    }

    public EventModel create(EventModel eventModel) throws CreationException {
        try {
            User user = userRepository.getById(eventModel.getAdminId());
            if (user == null)
                throw new CreationException("Failed to create event: user not exists");

            Event event = eventModelToEventMapper.map(eventModel);
            return eventToEventModelMapper.map(eventRepository.create(event));
        } catch (Exception e) {
            throw new CreationException("Failed to create event: " + e.getMessage());
        }
    }

    public void update(EventModel eventModel) {
        eventRepository.update(eventModelToEventMapper.map(eventModel));
    }

    public void delete(UUID id) {
        eventRepository.delete(id);
    }
}
