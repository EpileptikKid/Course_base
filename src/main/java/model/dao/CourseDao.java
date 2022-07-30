package model.dao;

import model.entity.Course;
import model.exception.DBException;

import java.util.List;

public interface CourseDao extends Dao {

    List<Course> findAllCourse() throws DBException;
}
