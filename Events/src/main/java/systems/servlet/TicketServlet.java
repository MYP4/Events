package systems.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.exceptions.DBException;
import data.repositories.TicketRepository;
import data.entity.Ticket;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet("/tickets")
public class TicketServlet extends HttpServlet {
    private static final org.apache.log4j.Logger logger = Logger.getLogger(TicketServlet.class);
    private final TicketRepository ticketRepository = new TicketRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Ticket> tickets = ticketRepository.getAll();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(convertTicketsToJson(tickets));
            out.flush();
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Ticket ticket = parseJsonToTicket(request.getReader());
            ticketRepository.create(ticket);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Ticket ticket = parseJsonToTicket(request.getReader());
            ticketRepository.update(ticket);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UUID id = UUID.fromString(request.getParameter("id"));
            boolean deleted = ticketRepository.delete(id);
            if (deleted) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    private String convertTicketsToJson(List<Ticket> tickets) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(tickets);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting tickets to JSON", e);
        }
    }

    private Ticket parseJsonToTicket(java.io.BufferedReader reader) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(reader, Ticket.class);
        } catch (JsonProcessingException e) {
            throw new IOException("Error parsing JSON to Ticket", e);
        }
    }
}
