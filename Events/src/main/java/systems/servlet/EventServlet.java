package systems.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.mappers.EventToEventModelMapper;
import data.models.EventModel;
import data.repositories.EventRepository;
import data.entity.Event;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.JspHelper;

import static java.util.Arrays.stream;
import static util.UrlPathUtil.EVENTS;

@WebServlet(EVENTS)
public class EventServlet extends HttpServlet {

    private final EventRepository eventRepository = new EventRepository();
    private final EventToEventModelMapper eventToEventModelMapper = new EventToEventModelMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<EventModel> events = eventRepository
                .getAll()
                .stream()
                .map(eventToEventModelMapper::map)
                .toList();;
        request.setAttribute("events", events);
        request.getRequestDispatcher(JspHelper.get("events")).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Event event = parseJsonToEvent(request.getReader());
        eventRepository.create(event);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Event event = parseJsonToEvent(request.getReader());
        eventRepository.update(event);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID id = UUID.fromString(request.getParameter("id"));
        boolean deleted = eventRepository.delete(id);
        if (deleted) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
