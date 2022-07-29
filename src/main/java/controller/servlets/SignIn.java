package controller.servlets;

import model.entity.User;
import model.exception.EntityNotFoundException;
import model.service.UserService;
import model.service.exception.WrongPasswordException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SignIn extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserService userService = new UserService();
        try {
            User loginUser = userService.signInUser(login, password);
            req.getSession().setAttribute("user", loginUser);
        } catch (EntityNotFoundException | WrongPasswordException e) {
            getServletContext().getRequestDispatcher("/fail").forward(req, resp);
            throw new RuntimeException(e);
        }
    }
}
