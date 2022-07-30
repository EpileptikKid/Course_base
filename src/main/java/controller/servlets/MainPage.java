package controller.servlets;

import controller.constants.ControllerConstants;
import controller.servlets.utils.CourseViewer;
import model.entity.Course;
import model.entity.User;
import model.service.CourseService;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        CourseService courseService = new CourseService();
        List<Course> courses = courseService.getAllCourse();
        Writer writer = resp.getWriter();
        writer.write("<head><title>Main page</title></head>");
        writer.write("<body>");
        for (int i = 1; i < 11; i++) {
            for (Course course : courses) {
                writer.write(CourseViewer.printCourse(course, ((User) req.getSession().getAttribute(ControllerConstants.USER_ATTR)).getRole()));
                writer.write("<br>");
            }
        }
        writer.write("</body>");
    }
}
