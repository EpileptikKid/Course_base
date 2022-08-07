package controller.servlets;

import controller.command.Command;
import controller.command.impl.goToMainPageCommand;
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
        Command command = new goToMainPageCommand();
        writer.write(command.execute(req, resp));
        writer.write("</body>");
    }
}
