package controller.servlets.util;

import com.mysql.cj.Session;
import controller.constants.ControllerConstants;
import model.entity.User;
import model.service.UserService;
import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(request, response);
    }

    public void destroy() {
    }
}