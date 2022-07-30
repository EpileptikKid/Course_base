package model.service;

import model.dao.CourseDao;
import model.dao.impl.JDBCDaoFactory;
import model.entity.Course;
import model.exception.DBException;

import java.util.List;

public class CourseService {
    public List<Course> getAllCourse() {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            return courseDao.findAllCourse();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
    }
}
