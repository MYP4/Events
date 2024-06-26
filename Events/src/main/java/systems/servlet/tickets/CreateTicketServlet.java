package systems.servlet.tickets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entity.Ticket;
import data.exceptions.CreationException;
import data.exceptions.DBException;
import data.mappers.TicketModelToTicketMapper;
import data.mappers.TicketToTicketModelMapper;
import data.models.TicketModel;
import data.repositories.SpecificRepository;
import data.repositories.TicketRepository;
import data.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import services.TicketService;
import util.JspHelper;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/create-ticket")
public class CreateTicketServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CreateTicketServlet.class);
    private final TicketService ticketService = new TicketService(
        new TicketRepository(),
        new SpecificRepository(),
        new UserRepository(),
        new TicketModelToTicketMapper(),
        new TicketToTicketModelMapper());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JspHelper.get("tickets/createTicket")).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            TicketModel ticket = parseJsonToTicketModel(request);
            ticketService.create(ticket);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.sendRedirect(request.getContextPath() + "/tickets");
        } catch (DBException e) {
            logger.error(e.getMessage());
            response.sendRedirect(request.getContextPath() + "/tickets");
        }
    }

    private TicketModel parseJsonToTicketModel(HttpServletRequest request) {
        UUID userId = UUID.fromString(request.getParameter("userId"));
        UUID specificId = UUID.fromString(request.getParameter("specificId"));

        return new TicketModel(userId, specificId, 0, null);
    }
}
