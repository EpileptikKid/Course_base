package controller.servlets.utils;

import model.exception.EntityNotFoundException;
import model.service.StudentCourseService;

public class RelationshipUserCourse {
    public static String RegisterUserInCourse(String course_id, String user_id) throws EntityNotFoundException {
        String result = "User registered in this course";
        StudentCourseService studentCourseService = new StudentCourseService();
        studentCourseService.enrollStudent(Integer.parseInt(user_id), Integer.parseInt(course_id));
        return result;
    }
}
