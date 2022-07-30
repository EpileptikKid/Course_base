package controller.servlets;

import controller.constants.ControllerConstants;
import controller.servlets.utils.CourseViewer;
import model.entity.Course;
import model.entity.User;
import model.entity.filter.CourseFilterOption;
import model.entity.filter.CourseSortParameter;
import model.service.CourseService;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@WebServlet(name = "main-test", value = "/main-test")
public class MainPage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        CourseService courseService = new CourseService();
        List<Course> courses = courseService.getAllCourse();
        Writer writer = resp.getWriter();
        writer.write("<head><title>Main page</title></head>");
        writer.write("<body>");
        for (int i = 1; i < 11; i++) {
            for (Course course : courses) {
                writer.write(CourseViewer.printCourse(course, ((User) req.getSession().getAttribute(ControllerConstants.USER_ATTR)).getRole()));
//            writer.write("<h2>Course name - " + course.getName() + "</h2>");
//            writer.write("<h3>Theme course - " + course.getTheme().getId() + "</h3>");
//            writer.write("<h3> Description - " + course.getDescription() + "</h3>");
//            writer.write("----------------------------------------------------------------------------------------<br>");
                writer.write("<br>");
            }
        }
        writer.write("</body>");
    }
}
