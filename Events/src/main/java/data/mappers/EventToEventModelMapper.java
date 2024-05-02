package data.mappers;

import data.entity.Event;
import data.models.EventModel;

public class EventToEventModelMapper implements Mapper<Event, EventModel> {

    @Override
    public EventModel map(Event event) {
        if (event == null) {
            return null;
        }

        EventModel eventModel = new EventModel();
        eventModel.setName(event.getName());
        eventModel.setDescription(event.getDescription());
        eventModel.setAdminId(event.getAdminId());
        eventModel.setUid(event.getUid());

        return eventModel;
    }
}