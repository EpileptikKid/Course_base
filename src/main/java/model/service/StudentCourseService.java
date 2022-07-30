package model.service;

import model.dao.StudentCourseDao;
import model.dao.impl.JDBCDaoFactory;
import model.exception.DBException;
import model.exception.EntityNotFoundException;
import model.service.exception.ServiceException;

public class StudentCourseService {
    public void enrollStudent(int studId, int courseId) throws EntityNotFoundException {
        try (StudentCourseDao studentCourseDao = new JDBCDaoFactory().createStudentCourseDao()) {
            studentCourseDao.enrollStudent(studId, courseId);
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }
}
