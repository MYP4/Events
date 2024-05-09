package systems.servlet.specifics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.exceptions.DBException;
import data.mappers.SpecificModelToSpecificMapper;
import data.mappers.SpecificToSpecificModelMapper;
import data.models.SpecificModel;
import data.models.TicketModel;
import data.repositories.EventRepository;
import data.repositories.SpecificRepository;
import data.entity.Specific;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import services.SpecificService;
import util.JspHelper;

@WebServlet("/specifics")
public class SpecificsServlet extends HttpServlet {
    private static final org.apache.log4j.Logger logger = Logger.getLogger(SpecificsServlet.class);
    private final SpecificService specificService = new SpecificService(
        new EventRepository(),
        new SpecificRepository(),
        new SpecificModelToSpecificMapper(),
        new SpecificToSpecificModelMapper());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<SpecificModel> specifics = specificService.getAll();
            request.setAttribute("specifics", specifics);
            request.getRequestDispatcher(JspHelper.get("specifics/specifics")).forward(request, response);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            SpecificModel specific = parseJsonToSpecificModel(request);
            specificService.create(specific);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    private SpecificModel parseJsonToSpecificModel(HttpServletRequest request) {
        UUID eventId = UUID.fromString(request.getParameter("event_id"));
        String description = request.getParameter("description");
        int ticketCount = Integer.parseInt(request.getParameter("ticket_count"));
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String address = request.getParameter("address");
        UUID uid = UUID.fromString(request.getParameter("uid"));

        return new SpecificModel(eventId, description, ticketCount, price, address, uid);
    }
}
