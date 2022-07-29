package controller.filter;

import model.entity.User;
import model.exception.EntityNotFoundException;
import model.service.UserService;
import model.service.exception.WrongPasswordException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filter work...");
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");
        UserService userService = new UserService();


        final HttpSession session = req.getSession();

        if (session!=null && session.getAttribute("login")!=null && session.getAttribute("password")!=null) {
            final User.Role role = (User.Role) session.getAttribute("role");

        } else {
            try {
                User loginUser = userService.signInUser(login, password);
                req.getSession().setAttribute("user", loginUser);
            } catch (EntityNotFoundException | WrongPasswordException e) {
                session.getServletContext().getRequestDispatcher("/fail");
                throw new RuntimeException(e);
            }


        }
    }

    @Override
    public void destroy() {
        System.out.println("destroy filter");
        Filter.super.destroy();
    }

}
