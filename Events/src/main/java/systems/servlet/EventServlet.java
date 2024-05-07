package systems.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entity.Event;
import data.exceptions.DBException;
import data.mappers.EventToEventModelMapper;
import data.models.EventModel;
import data.repositories.EventRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import services.EventService;
import util.JspHelper;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@WebServlet("/events")
public class EventServlet extends HttpServlet {
    private static final org.apache.log4j.Logger logger = Logger.getLogger(EventServlet.class);
    private final EventRepository eventRepository = new EventRepository();
    private final EventToEventModelMapper eventToEventModelMapper = new EventToEventModelMapper();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        try {
            List<EventModel> events = eventRepository
                    .getAll()
                    .stream()
                    .map(eventToEventModelMapper::map)
                    .toList();;
            request.setAttribute("events", events);
            request.getRequestDispatcher(JspHelper.get("events")).forward(request, response);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        try {
            Event event = parseJsonToEvent(request.getReader());
            eventRepository.create(event);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        try {
            Event event = parseJsonToEvent(request.getReader());
            eventRepository.update(event);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        try {
            UUID id = UUID.fromString(request.getParameter("id"));
            boolean deleted = eventRepository.delete(id);
            if (deleted) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    private String convertEventsToJson(List<Event> events) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(events);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting events to JSON", e);
        }
    }

    private Event parseJsonToEvent(java.io.BufferedReader reader) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(reader, Event.class);
        } catch (JsonProcessingException e) {
            throw new IOException("Error parsing JSON to Event", e);
        }
    }
}
