package controller;

import controller.command.Command;
import controller.command.impl.GoToMainPageCommand;
import controller.constants.ControllerConstants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class Servlet extends HttpServlet {
    private static final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        config.getServletContext().setAttribute(ControllerConstants.LOGGED_USERS_ATTR, new HashSet<String>());
        commands.put("/main_page", new GoToMainPageCommand());
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        Optional<String> commandParameter = Optional.ofNullable(req.getParameter("command"));
        if (commandParameter.isPresent()) {
            path += "?command=" + commandParameter.get();
        }
        String lastRequest = req.getRequestURI();
        Optional<String> queryString = Optional.ofNullable(req.getQueryString());
        if (queryString.isPresent()) {
            lastRequest += "?" + queryString.get();
        }
        req.getSession().setAttribute("lastRequest", lastRequest);
        System.out.println(lastRequest);                             // system out print line

        Optional<Command> commandOpt = Optional.ofNullable(commands.get(path));
        if (commandOpt.isPresent()) {
            String page = commandOpt.get().execute(req, resp);
            if (page.startsWith(ControllerConstants.REDIRECT_PREFIX)) {
                resp.sendRedirect(page.replaceFirst(ControllerConstants.REDIRECT_PREFIX, ""));
                return;
            }
            req.getRequestDispatcher(page).forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
