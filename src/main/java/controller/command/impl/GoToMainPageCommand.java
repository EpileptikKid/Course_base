package controller.command.impl;

import controller.command.Command;
import controller.constants.ControllerConstants;
import controller.constants.Parameter;
import controller.util.PaginationUtil;
import model.entity.Course;
import model.entity.Theme;
import model.entity.User;
import model.entity.filter.CourseFilterOption;
import model.entity.filter.CourseSortParameter;
import model.service.CourseService;
import model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class GoToMainPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = new UserService();
        CourseService courseService = new CourseService();

        List<User> tutorsList = userService.getUsersByRole(User.Role.TUTOR);
        req.setAttribute("tutors", tutorsList);

        String sortBy = Optional.ofNullable(req.getParameter(Parameter.COURSE_SORT_OPTION.getValue())).orElse("CourseNameAsc");
        CourseSortParameter sortParameter = CourseSortParameter.getFormRequestParameter(sortBy);

        Optional<String> tutorFilter = Optional.ofNullable(req.getParameter(Parameter.COURSE_TUTOR_ID.getValue()));
        Optional<String> themeFilter = Optional.ofNullable(req.getParameter(Parameter.COURSE_THEME_ID.getValue()));
        Optional<User> userFilter = Optional.ofNullable((User) req.getSession().getAttribute(ControllerConstants.USER_ATTR));

        User tutor = null;
        Theme theme = null;
        String tutorStr = tutorFilter.orElse("");
        String themeStr = themeFilter.orElse("");
        if (!tutorStr.isEmpty()) {
            try {
                int id = Integer.parseInt(tutorStr);
                tutor = new User.Builder()
                        .setId(id)
                        .build();
            } catch (NumberFormatException e) {
                req.setAttribute(ControllerConstants.ERROR_ATR, "invalid_request_parameter");
                return ControllerConstants.FORWARD_TO_ERROR_PAGE;
            }
        }
        if (!themeStr.isEmpty()) {
            try {
                int id = Integer.parseInt(themeStr);
                theme = new Theme.Builder()
                        .setId(id)
                        .build();
            } catch (NumberFormatException e) {
                req.setAttribute(ControllerConstants.ERROR_ATR, "invalid_request_parameter");
                return ControllerConstants.FORWARD_TO_ERROR_PAGE;
            }
        }

        CourseFilterOption filterOption = new CourseFilterOption(tutor, theme, userFilter.orElse(null), CourseFilterOption.CourseStatus.NOT_STARTED);

        int pageCount = PaginationUtil.getPagesCount(courseService.getAvailableCoursesCount(filterOption));
        int currentPage = PaginationUtil.getCurrentPage(req, pageCount);
        List<Course> coursesPage = courseService.getFilteredCoursesPage(currentPage, ControllerConstants.ITEMS_PER_PAGE, sortParameter, filterOption);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("coursesPage", coursesPage);
        return ControllerConstants.FORWARD_TO_MAIN_PAGE + "?sortOption=" + sortBy +"&page=" + coursesPage;
    }
}
