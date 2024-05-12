package servicesTests;

import data.entity.Event;
import data.entity.User;
import data.exceptions.DBException;
import data.mappers.EventModelToEventMapper;
import data.mappers.EventToEventModelMapper;
import data.models.EventModel;
import data.repositories.EventRepository;
import data.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.EventService;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    EventRepository eventRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    EventToEventModelMapper eventToEventModelMapper;
    @Mock
    EventModelToEventMapper eventModelToEventMapper;
    @InjectMocks
    EventService eventService;

    @Test
    void getAll() throws DBException {
        Event event = new Event();
        event.setUid(UUID.randomUUID());
        EventModel eventModel = new EventModel();
        eventModel.setUid(event.getUid());

        when(eventRepository.getAll()).thenReturn(List.of(event));
        when(eventToEventModelMapper.map(event)).thenReturn(eventModel);

        List<EventModel> result = eventService.getAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUid()).isEqualTo(eventModel.getUid());
    }

    @Test
    void getById() throws DBException {
        UUID id = UUID.randomUUID();
        Event event = new Event();
        event.setUid(id);
        EventModel eventModel = new EventModel();
        eventModel.setUid(id);

        when(eventRepository.getById(id)).thenReturn(event);
        when(eventToEventModelMapper.map(event)).thenReturn(eventModel);

        EventModel result = eventService.getById(id);
        assertThat(result).isEqualTo(eventModel);
    }

//    @Test
//    void create() throws DBException {
//        UUID uid = UUID.randomUUID();
//        UUID adminId = UUID.randomUUID();
//        User user = new User();
//        user.setUid(adminId);
//        EventModel eventModel = new EventModel();
//        eventModel.setAdminId(adminId);
//        eventModel.setUid(uid);
//        eventModel.setName("A");
//        eventModel.setDescription("A");
//
//        when(userRepository.getById(adminId)).thenReturn(user);
//        when(eventModelToEventMapper.map(eventModel)).thenReturn(new Event());
//        when(eventRepository.create(any(Event.class))).thenReturn(new Event());
//
//        EventModel result = eventService.create(eventModel);
//        assertThat(result).isNotNull();
//    }

    @Test
    void update() throws DBException {
        UUID id = UUID.randomUUID();
        Event event = new Event();
        event.setUid(id);
        EventModel eventModel = new EventModel();
        eventModel.setUid(id);

        when(eventModelToEventMapper.map(eventModel)).thenReturn(event);

        eventService.update(eventModel);

        verify(eventRepository).update(event);
    }

    @Test
    void delete() throws DBException {
        UUID id = UUID.randomUUID();
        when(eventRepository.delete(id)).thenReturn(true);

        eventService.delete(id);

        verify(eventRepository).delete(id);
    }
}
