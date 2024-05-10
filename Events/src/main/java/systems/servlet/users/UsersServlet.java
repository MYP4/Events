package systems.servlet.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.exceptions.DBException;
import data.mappers.CreateUserModelToUserMapper;
import data.mappers.TicketModelToTicketMapper;
import data.mappers.TicketToTicketModelMapper;
import data.mappers.UserModelToUserMapper;
import data.mappers.UserToUserModelMapper;
import data.models.CreateUserModel;
import data.models.UserModel;
import data.repositories.SpecificRepository;
import data.repositories.TicketRepository;
import data.repositories.UserRepository;
import data.entity.User;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import services.TicketService;
import services.UserService;
import util.JspHelper;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UsersServlet.class);
    private final UserService userService = new UserService(
        new UserRepository(),
        new UserToUserModelMapper(),
        new UserModelToUserMapper(),
        new CreateUserModelToUserMapper());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<UserModel> users = userService.getAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher(JspHelper.get("users/users")).forward(request, response);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
    }
}