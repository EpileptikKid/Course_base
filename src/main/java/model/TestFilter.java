package model;

import model.dao.impl.ConnectionPool;
import model.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/test")
public class TestFilter extends HttpFilter {
//    private static final List<String> allUrls = new ArrayList<>();

    @Override
    public void init() {
//    allUrls.add("/signed");
        System.out.println("Init  filter");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        System.out.println("Start filter work");
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) res;
//        String login = request.getParameter("login");
//        String password = request.getParameter("password");
//        User user = new User.Builder()
//                .setLogin(login)
//                .setPassword(password)
//                .build();
//        String str = "\nURAAAAAAAA!!!";
//        System.out.println("filter data - \n user login - " + user.getLogin() + "\n user password - " + user.getPassword());
//        if (!verifyUserLoggedIn(request, user)) {
//            str="\nFAIL!!!!!!!!!!!!!!";
//            req.setAttribute("result", str);
//            getServletContext().getRequestDispatcher("/fail").forward(req, res);
//        }
//
//        req.setAttribute("result", str);
        chain.doFilter(req, res);
    }

//    private boolean verifyUserLoggedIn(HttpServletRequest request, User user) {
//        Connection conn = ConnectionPool.getInstance().getConnection();
//        try (Statement statement = conn.createStatement()) {
//            ResultSet res = statement.executeQuery("SELECT * FROM users WHERE login='" + user.getLogin() + "';");
//            System.out.println("working verify module");
//            if (res.next()) {
//                if (res.getString("password").equals(user.getPassword())) {
//                    System.out.println("verify true!!!");
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("verify false!!!");
//        return false;
//    }
}
