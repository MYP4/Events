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

@WebServlet("/delete-specific")
public class DeleteSpecificServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DeleteSpecificServlet.class);
    private final SpecificService specificService = new SpecificService(
        new EventRepository(),
        new SpecificRepository(),
        new SpecificModelToSpecificMapper(),
        new SpecificToSpecificModelMapper());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JspHelper.get("specifics/deleteSpecific")).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            specificService.delete(UUID.fromString(request.getParameter("uid")));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.sendRedirect(request.getContextPath() + "/specifics");
        } catch (DBException e) {
            logger.error(e.getMessage());
            response.sendRedirect(request.getContextPath() + "/specifics");
        }
    }

}
