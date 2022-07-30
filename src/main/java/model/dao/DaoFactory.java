package model.dao;

import model.exception.DBException;

public interface DaoFactory {
    UserDao createUserDao() throws DBException;

    CourseDao createCourseDao() throws DBException;
    StudentCourseDao createStudentCourseDao() throws DBException;
}
