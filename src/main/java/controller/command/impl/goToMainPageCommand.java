package controller.command.impl;

import controller.command.Command;
import controller.constants.ControllerConstants;
import model.entity.Course;
import model.entity.User;
import model.service.CourseService;
import model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

public class goToMainPageCommand implements Command {
    private String red = "ff0000";
    private String green = "00ff00";
    private String blue = "0000ff";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = new UserService();
        CourseService courseService = new CourseService();
        List<Course> courses = courseService.getAllCourse();
        LocalDate today = LocalDate.now();
        String result = "";
        User.Role role = ((User) req.getSession().getAttribute(ControllerConstants.USER_ATTR)).getRole();
        for (Course course : courses) {
            String color = "";
            if (today.isBefore(course.getStartDate())) {
                color = "green";
                System.out.println("greeeen");
            } else if (today.isAfter(course.getEndDate())) {
                color = "gray";
                System.out.println("graaaaay");
            } else {
                color = "yellow";
                System.out.println("yellooooow");
            }
            result += "<table border=\"1\" bgcolor=\"" + color + "\"><tbody>";
            result += "<tr><th colspan=\"2\">" + course.getName() + "</th></tr>";
            result += "<tr><th>Theme</th><th>" + course.getTheme().getName() + "</th></tr>";
            result += "<tr><th>Tutor</th><th>" + course.getTutor().getFirstName() + " " + course.getTutor().getLastName() + "</th></tr>";
            result += "<tr><th>Start date</th><th>" + course.getStartDate() + "</th></tr>";
            result += "<tr><th>End date</th><th>" + course.getEndDate() + "</th></tr>";
            result += "<tr><th>Description</th><th>" + course.getDescription() + "</th></tr>";
            result += "</tbody></table>";
            result += "<br>";
            if (role.equals(User.Role.STUDENT)) {

            }
        }
        return result;
    }
}
