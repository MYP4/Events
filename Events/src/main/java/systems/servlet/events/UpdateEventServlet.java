package systems.servlet.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entity.Event;
import data.exceptions.DBException;
import data.mappers.EventModelToEventMapper;
import data.mappers.EventToEventModelMapper;
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


@WebServlet("/update-event")
public class UpdateEventServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateEventServlet.class);
    private final EventService eventService = new EventService(
        new EventRepository(),
        new UserRepository(),
        new EventToEventModelMapper(),
        new EventModelToEventMapper());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JspHelper.get("events/updateEvent")).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            EventModel event = parseJsonToEventModel(request);
            eventService.update(event);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.sendRedirect(request.getContextPath() + "/events");
        } catch (DBException e) {
            logger.error(e.getMessage());
            response.sendRedirect(request.getContextPath() + "/events");
        }
    }

    private EventModel parseJsonToEventModel(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        UUID admin =UUID.fromString(request.getParameter("adminId"));
        UUID uid = UUID.fromString(request.getParameter("uid"));

        return new EventModel(name, description, admin, uid);
    }
}
