package model.dao;

import model.entity.Course;
import model.entity.filter.CourseFilterOption;
import model.entity.filter.CourseSortParameter;
import model.exception.DBException;
import model.exception.EntityNotFoundException;
import model.exception.IllegalDeleteException;

import java.util.List;

public interface CourseDao extends Dao {

    void updateCourse(Course course) throws DBException, EntityNotFoundException;
    void createCourse(Course course) throws DBException, EntityNotFoundException;
    Course findCourseById(int id) throws DBException, EntityNotFoundException;
    void deleteCourse(int id) throws DBException, EntityNotFoundException, IllegalDeleteException;
    int getFilteredCoursesCount(CourseFilterOption filterOption) throws DBException;
    List<Course> getFilteredCourses(CourseSortParameter sortParameter, CourseFilterOption filterOption) throws DBException;
    List<Course> getFilteredCourses(int offset, int numberOfItems,
                                    CourseSortParameter sortParameter, CourseFilterOption filterOption) throws DBException;
}
