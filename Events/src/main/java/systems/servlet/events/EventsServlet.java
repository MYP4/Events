package systems.servlet.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entity.Event;
import data.exceptions.DBException;
import data.mappers.CreateUserModelToUserMapper;
import data.mappers.EventModelToEventMapper;
import data.mappers.EventToEventModelMapper;
import data.mappers.UserModelToUserMapper;
import data.mappers.UserToUserModelMapper;
import data.models.EventModel;
import data.repositories.EventRepository;
import data.repositories.UserRepository;
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
public class EventsServlet extends HttpServlet {
    private static final org.apache.log4j.Logger logger = Logger.getLogger(EventsServlet.class);
    private final EventService eventService = new EventService(
        new EventRepository(),
        new UserRepository(),
        new EventToEventModelMapper(),
        new EventModelToEventMapper());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<EventModel> events = eventService.getAll();
            request.setAttribute("events", events);
            request.getRequestDispatcher(JspHelper.get("events/events")).forward(request, response);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            EventModel event = parseJsonToEventModel(request);
            eventService.create(event);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    private EventModel parseJsonToEventModel(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        UUID adminId = UUID.fromString(request.getParameter("admin_id"));
        UUID uid = UUID.fromString(request.getParameter("uid"));

        return new EventModel(name, description, adminId, uid);
    }
}
