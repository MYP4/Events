package systems.servlet.specifics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entity.Specific;
import data.exceptions.DBException;
import data.mappers.SpecificModelToSpecificMapper;
import data.mappers.SpecificToSpecificModelMapper;
import data.models.SpecificModel;
import data.repositories.EventRepository;
import data.repositories.SpecificRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import services.SpecificService;
import util.JspHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@WebServlet("/update-specific")
public class UpdateSpecificServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateSpecificServlet.class);
    private final SpecificService specificService = new SpecificService(
        new EventRepository(),
        new SpecificRepository(),
        new SpecificModelToSpecificMapper(),
        new SpecificToSpecificModelMapper());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JspHelper.get("specifics/updateSpecific")).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            SpecificModel specific = parseJsonToSpecificModel(request);
            specificService.update(specific);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.sendRedirect(request.getContextPath() + "/specifics");
        } catch (DBException e) {
            logger.error(e.getMessage());
            response.sendRedirect(request.getContextPath() + "/specifics");
        }
    }

    private SpecificModel parseJsonToSpecificModel(HttpServletRequest request) {
        UUID eventId = UUID.fromString(request.getParameter("eventId"));
        String description = request.getParameter("description");
        int ticketCount = Integer.parseInt(request.getParameter("ticketCount"));
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String address = request.getParameter("address");
        UUID uid = UUID.fromString(request.getParameter("uid"));

        return new SpecificModel(eventId, description, ticketCount, price, address, uid);
    }
}
