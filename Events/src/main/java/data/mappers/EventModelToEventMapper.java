package data.mappers;

import data.entity.Event;
import data.models.EventModel;

public class EventModelToEventMapper implements Mapper<EventModel, Event> {

    @Override
    public Event map(EventModel eventModel) {
        if (eventModel == null) {
            return null;
        }
        Event event = new Event();
        event.setName(eventModel.getName());
        event.setDescription(eventModel.getDescription());
        event.setUid(eventModel.getUid());
        event.setAdminId(eventModel.getAdminId());

        return event;
    }
}