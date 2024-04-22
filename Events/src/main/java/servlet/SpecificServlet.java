package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import repositories.SpecificRepository;
import entity.Specific;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/specifics")
public class SpecificServlet extends HttpServlet {

    private SpecificRepository specificRepository = new SpecificRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Specific> specifics = specificRepository.findAll();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(convertSpecificsToJson(specifics));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Specific specific = parseJsonToSpecific(request.getReader());
        specificRepository.create(specific);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Specific specific = parseJsonToSpecific(request.getReader());
        specificRepository.update(specific);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID id = UUID.fromString(request.getParameter("id"));
        boolean deleted = specificRepository.delete(id);
        if (deleted) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private String convertSpecificsToJson(List<Specific> specifics) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(specifics);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting specifics to JSON", e);
        }
    }

    private Specific parseJsonToSpecific(java.io.BufferedReader reader) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(reader, Specific.class);
        } catch (JsonProcessingException e) {
            throw new IOException("Error parsing JSON to Specific", e);
        }
    }
}
