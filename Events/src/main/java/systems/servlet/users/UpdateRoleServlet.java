package systems.servlet.users;

import data.entity.UserRole;
import data.exceptions.DBException;
import data.mappers.*;
import data.models.SpecificModel;
import data.models.UserModel;
import data.repositories.EventRepository;
import data.repositories.SpecificRepository;
import data.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import services.SpecificService;
import services.UserService;
import util.JspHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

@WebServlet("/update-user")
public class UpdateRoleServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateRoleServlet.class);
    private final UserService userService = new UserService(
        new UserRepository(),
        new UserToUserModelMapper(),
        new UserModelToUserMapper(),
        new CreateUserModelToUserMapper());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JspHelper.get("users/updateUser")).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserModel updateUser = parseJsonToUserModel(request);
            UserModel user = userService.getById(updateUser.getUid());
            user.setRole(updateUser.getRole());
            userService.update(user);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (DBException e) {
            logger.error(e.getMessage());
            response.sendRedirect(request.getContextPath() + "/users");
        }
    }

    private UserModel parseJsonToUserModel(HttpServletRequest request) {
        UserRole role = UserRole.valueOf(request.getParameter("role"));
        UUID uid = UUID.fromString(request.getParameter("uid"));

        return new UserModel(null, null, role, null, null, null,  uid);
    }
}
