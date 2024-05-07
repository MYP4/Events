package systems.servlet;

import data.exceptions.DBException;
import data.mappers.CreateUserModelToUserMapper;
import data.mappers.UserModelToUserMapper;
import data.mappers.UserToUserModelMapper;
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

@WebServlet("/sign-in")
public class SigninServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SigninServlet.class);
    private final UserService userService = new UserService(new UserRepository(),
                                                            new UserToUserModelMapper(),
                                                            new UserModelToUserMapper(),
                                                            new CreateUserModelToUserMapper());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Objects.isNull(request.getSession().getAttribute("user"))) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        request.getRequestDispatcher(JspHelper.get("sign-in")).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
            UserModel user = userService.getByLoginPassword(login, PasswordHasher.md5(password));
            request.getSession().setAttribute("user", userService.getByLogin(login));
            response.sendRedirect(request.getContextPath() + "/events");
        } catch (Exception e) {
            logger.error(e.getMessage());
            String message = "Неверный логин/пороль.";
            request.setAttribute("message", message);
            request.getRequestDispatcher(JspHelper.get("login")).forward(request, response);
        } catch (DBException e) {
            logger.error(e.getMessage());
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}
