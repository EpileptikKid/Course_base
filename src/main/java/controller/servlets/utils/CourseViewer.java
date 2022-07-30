package controller.servlets.utils;

import model.entity.Course;
import model.entity.User;

public class CourseViewer {
    public static String printCourse(Course course, User.Role role) {
        String resultString = "";
        resultString += "<table border=\"1\"><tbody>";
        resultString += "<tr><th colspan=\"2\">" + course.getName() + "</th></tr>";
        resultString += "<tr><th>Theme</th><th>" + course.getTheme().getName() + "</th></tr>";
        resultString += "<tr><th>Tutor</th><th>" + course.getTutor().getFirstName() + " " + course.getTutor().getLastName() + "</th></tr>";
        resultString += "<tr><th>Start date</th><th>" + course.getStartDate() + "</th></tr>";
        resultString += "<tr><th>End date</th><th>" + course.getEndDate() + "</th></tr>";
        resultString += "<tr><th>Description</th><th>" + course.getDescription() + "</th></tr>";
        resultString += "</tbody></table>";
        if (role.equals(User.Role.STUDENT)) {
            resultString += "";
        }

        return resultString;
    }
}
