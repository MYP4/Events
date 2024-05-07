package systems.servlet;

import data.entity.User;
import data.exceptions.CreationException;
import data.exceptions.DBException;
import data.mappers.CreateUserModelToUserMapper;
import data.mappers.UserModelToUserMapper;
import data.mappers.UserToUserModelMapper;
import data.models.CreateUserModel;
import data.models.UserModel;
import data.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import services.UserService;
import util.JspHelper;
import util.PasswordHasher;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/sign-up")
public class SignupServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SignupServlet.class);
    private final UserService userService = new UserService(new UserRepository(),
                                                            new UserToUserModelMapper(),
                                                            new UserModelToUserMapper(),
                                                            new CreateUserModelToUserMapper());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JspHelper.get("sign-up")).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");

        try {
            UserModel user = userService.create(new CreateUserModel(login,
                    PasswordHasher.md5(password),
                    firstName,
                    secondName));
            request.getSession().setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/sign-in");
        } catch (Exception e) {
            logger.error(e.getMessage());
            String message = e.getMessage();
            request.setAttribute("message", message);
            request.getRequestDispatcher(JspHelper.get("sign-up")).forward(request, response);
        } catch (DBException e) {
            logger.error(e.getMessage());
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}
