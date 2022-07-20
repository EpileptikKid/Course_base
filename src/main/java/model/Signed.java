package model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
@WebServlet(name = "signed", value = "/signed")
public class Signed extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        req.getSession().getServletContext().setAttribute("login", login);
        req.getSession().getServletContext().setAttribute("password", password);
        getServletContext().getRequestDispatcher("/test").forward(req, resp);
    }
}
