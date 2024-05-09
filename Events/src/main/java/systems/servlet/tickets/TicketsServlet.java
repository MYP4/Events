package systems.servlet.tickets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.exceptions.DBException;
import data.mappers.TicketModelToTicketMapper;
import data.mappers.TicketToTicketModelMapper;
import data.models.TicketModel;
import data.repositories.SpecificRepository;
import data.repositories.TicketRepository;
import data.entity.Ticket;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import data.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import services.TicketService;
import util.JspHelper;

@WebServlet("/tickets")
public class TicketsServlet extends HttpServlet {
    private static final org.apache.log4j.Logger logger = Logger.getLogger(TicketsServlet.class);
    private final TicketService ticketService = new TicketService(
      new TicketRepository(),
      new SpecificRepository(),
      new UserRepository(),
      new TicketModelToTicketMapper(),
      new TicketToTicketModelMapper());


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<TicketModel> tickets = ticketService.getAll();
            request.setAttribute("tickets", tickets);
            request.getRequestDispatcher(JspHelper.get("tickets")).forward(request, response);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Ticket ticket = parseJsonToTicket(request.getReader());
            ticketService.create(ticket);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Ticket ticket = parseJsonToTicket(request.getReader());
            ticketService.update(ticket);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UUID id = UUID.fromString(request.getParameter("id"));
            ticketService.delete(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }
}
