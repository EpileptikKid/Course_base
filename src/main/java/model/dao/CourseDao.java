package model.dao;

import model.entity.Course;
import model.exception.DBException;
import model.exception.EntityNotFoundException;
import model.exception.IllegalDeleteException;

import java.util.List;

public interface CourseDao extends Dao {

    List<Course> findAllCourse() throws DBException;
}
