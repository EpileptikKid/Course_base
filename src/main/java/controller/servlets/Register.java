package controller.servlets;

import controller.constants.ControllerConstants;
import model.entity.User;
import model.exception.EntityAlreadyExistsException;
import model.service.UserService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "RegisterUtil", value = "/register-util")
public class Register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User currentUser = (User) req.getSession().getAttribute(ControllerConstants.USER_ATTR);
        String errorMessage = "";
        String firstName = req.getParameter("firstname");
        if (firstName.length() <= 3) {
            errorMessage += "<p style=\"color:#ff0000\">Name is too short!</p>";
        }
        String lastName = req.getParameter("lastname");
        if (lastName.length() <= 3) {
            errorMessage += "<p style=\"color:#ff0000\">Surname is too short!</p>";
        }
        String login = req.getParameter("login");
        if (login.length() <= 3) {
            errorMessage += "<p style=\"color:#ff0000\">Login is too short!</p>";
        }
        if (login.length() >= 15) {
            errorMessage += "<p style=\"color:#ff0000\">Login is too long!</p>";
        }
        String password = req.getParameter("password");
        if (password.length() <= 3) {
            errorMessage += "<p style=\"color:#ff0000\">Password is too short!</p>";
        }

        User registerUser = new User.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setLogin(login)
                .setPassword(password)
                .setRole(User.Role.STUDENT)
                .setBlocked(false)
                .build();


        UserService userService = new UserService();
        List<User> users = userService.findAllUsers();
        if (currentUser.getRole() == User.Role.GUEST) {
            boolean check = false;
            for (User us : users) {
                if (us.getLogin().equals(registerUser.getLogin())) {
                    check = true;
                    errorMessage += "<p style=\"color:#ff0000\">This login is already taken!</p>";
                    break;
                }
            }
            System.out.println(check);
            if (!check && errorMessage.length() < 3) {
                try {
                    userService.registerUser(registerUser);
                    req.getSession().setAttribute("message", "<p style=\"color:#00ff00\">User successfully registered!</p>");
                    resp.sendRedirect("/Course_base_war_exploded/logout");
                } catch (EntityAlreadyExistsException e) {
                    throw new RuntimeException(e);
                }
            } else {
                req.getSession().setAttribute("message", errorMessage);
                resp.sendRedirect("/Course_base_war_exploded/registration");
            }
        }
    }



}
