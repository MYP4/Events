package systems.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.repositories.UserRepository;
import data.entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private final UserRepository userDao = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userDao.getAll();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(convertUsersToJson(users));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = parseJsonToUser(request.getReader());
        userDao.create(user);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = parseJsonToUser(request.getReader());
        userDao.update(user);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID id = UUID.fromString(request.getParameter("id"));
        boolean deleted = userDao.delete(id);
        if (deleted) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private String convertUsersToJson(List<User> users) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting users to JSON", e);
        }
    }

    private User parseJsonToUser(java.io.BufferedReader reader) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(reader, User.class);
        } catch (JsonProcessingException e) {
            throw new IOException("Error parsing JSON to User", e);
        }
    }
}