package controller.listener;


import controller.constants.ControllerConstants;
import model.entity.User;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Set;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        User user = new User.Builder()
                .setRole(User.Role.GUEST)
                .build();
        se.getSession().setAttribute(ControllerConstants.USER_ATTR, user);
        se.getSession().setAttribute("locale", "en");
        System.out.println("listener started");
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        User user = (User) se.getSession().getAttribute(ControllerConstants.USER_ATTR);
        String login = user.getLogin();
        Set<String> userLogins = (Set<String>) se.getSession().getAttribute(ControllerConstants.LOGGED_USERS_ATTR);
        userLogins.remove(login);
    }
}
