package model.dao;

import model.exception.DBException;
import model.exception.EntityNotFoundException;

public interface StudentCourseDao extends Dao {
    void enrollStudent(int studId, int courseId) throws DBException, EntityNotFoundException;
}
