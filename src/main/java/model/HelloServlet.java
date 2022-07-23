package model;

import com.mysql.cj.Session;
import controller.constants.ControllerConstants;
import model.entity.User;
import model.service.UserService;
import java.io.*;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        UserService userService = new UserService();
        List<User> users = userService.findAllUsers();
        for (User user : users) {
            out.println(user + "<br>");
        }
        String role = ((User) request.getSession().getAttribute(ControllerConstants.USER_ATTR)).getRole().toString();
        out.println(role);

        out.println("</body></html>");
    }

    public void destroy() {
    }
}