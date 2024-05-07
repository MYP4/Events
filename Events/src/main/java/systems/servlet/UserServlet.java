package systems.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.exceptions.DBException;
import data.mappers.UserToUserModelMapper;
import data.models.UserModel;
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
import org.apache.log4j.Logger;
import util.JspHelper;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private static final org.apache.log4j.Logger logger = Logger.getLogger(UserServlet.class);
    private final UserRepository userRepository = new UserRepository();
    private final UserToUserModelMapper userToUserModelMapper = new UserToUserModelMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<UserModel> users = userRepository
                    .getAll()
                    .stream()
                    .map(userToUserModelMapper::map)
                    .toList();
            request.setAttribute("users", users);
            request.getRequestDispatcher(JspHelper.get("users")).forward(request, response);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = parseJsonToUser(request.getReader());
            userRepository.create(user);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = parseJsonToUser(request.getReader());
            userRepository.update(user);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UUID id = UUID.fromString(request.getParameter("id"));
            boolean deleted = userRepository.delete(id);
            if (deleted) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DBException e) {
            logger.error(e.getMessage());
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