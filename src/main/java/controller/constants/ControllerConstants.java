package controller.constants;

public class ControllerConstants {
    private ControllerConstants() { throw new AssertionError(); }
    public static final String REDIRECT_PREFIX = "redirect:";
    public static final String USER_ATTR = "user";
    public static final String LOGGED_USERS_ATTR = "loggedUsers";
    public static final String ERROR_ATR = "error";
    public static final String FORWARD_TO_ERROR_PAGE = "/error.jsp";
    public static final int ITEMS_PER_PAGE = 3;
    public static final String FORWARD_TO_MAIN_PAGE = "/WEB-INF/jsp/main.jsp";
}
