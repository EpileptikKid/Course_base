package controller.filter;

import controller.constants.ControllerConstants;
import controller.servlets.utils.RelationshipUserCourse;
import model.entity.User;
import model.exception.EntityNotFoundException;
import model.service.UserService;
import model.service.exception.WrongPasswordException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class AuthFilter implements Filter {

    private static final Map<User.Role, List<String>> accessMap = new HashMap<>();
    private static final List<String> allUrls = new ArrayList<>();
    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("init filter");

        List<String> guestUrls = new ArrayList<>();
        guestUrls.add("/Course_base_war_exploded/");
        guestUrls.add("/Course_base_war_exploded/registration");
        guestUrls.add("/Course_base_war_exploded/signin");
        guestUrls.add("/Course_base_war_exploded/register-util");
        guestUrls.add("/Course_base_war_exploded/logout");
        accessMap.put(User.Role.GUEST, guestUrls);

        List<String> studentUrls = new ArrayList<>();
        studentUrls.add("/Course_base_war_exploded/main_page");

        accessMap.put(User.Role.STUDENT, studentUrls);

        allUrls.addAll(guestUrls);
        allUrls.addAll(studentUrls);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.print("filter work...   ");
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;

        String url = req.getRequestURI();
        System.out.println(url);
        User user = (User) req.getSession().getAttribute(ControllerConstants.USER_ATTR);
        System.out.println(user.getRole());
        System.out.println(req.getParameter("courseName"));
        Enumeration<String> names =  req.getParameterNames();
        System.out.println("__________________________");
        while (names.hasMoreElements()) {
            System.out.println(names.nextElement());
        }
        System.out.println("__________________________");

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("firstname");
        if (login!=null && password!=null && name==null) {
            UserService userService = new UserService();
            try {
                user = userService.signInUser(login, password);
                req.getSession().setAttribute(ControllerConstants.USER_ATTR, user);
            } catch (EntityNotFoundException | WrongPasswordException e) {
                throw new RuntimeException(e);
            }

        }
        if (req.getParameter("courseName")!=null) {
            String message;
            try {
                message = RelationshipUserCourse.RegisterUserInCourse(req.getParameter("courseName"), String.valueOf(user.getId()));
            } catch (EntityNotFoundException e) {
                throw new RuntimeException(e);
            }
            req.getSession().setAttribute("message", message);
        }
        System.out.println(user.getRole());

        if (allUrls.contains(url)) {
            if (!accessMap.get(user.getRole()).contains(url)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
                System.out.println("success error " + url);
            }
        }

        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        System.out.println("destroy filter");
        Filter.super.destroy();
    }

}
