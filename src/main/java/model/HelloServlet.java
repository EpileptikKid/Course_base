package model;

import model.dao.ConnectionPool;
import model.entity.User;
import model.dao.mapper.UserMapper;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        Connection conn = ConnectionPool.getInstance().getConnection();
        try (Statement statement = conn.createStatement()){
            ResultSet res = statement.executeQuery("SELECT * FROM users");
            out.println("______________USERS______________<br>");
            while (res.next()) {
                User user = new UserMapper().extract(res);
                out.println(user + "<br>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("</body></html>");
    }

    public void destroy() {
    }
}