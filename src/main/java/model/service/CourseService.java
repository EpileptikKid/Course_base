package model.service;

import model.dao.CourseDao;
import model.dao.impl.JDBCDaoFactory;
import model.entity.Course;
import model.entity.filter.CourseFilterOption;
import model.entity.filter.CourseSortParameter;
import model.exception.DBException;
import model.exception.EntityNotFoundException;
import model.exception.IllegalDeleteException;
import model.service.exception.ServiceException;

import java.util.List;

public class CourseService {

    public void createCourse(Course course) throws EntityNotFoundException {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            courseDao.createCourse(course);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }

    public int getAvailableCoursesCount(CourseFilterOption filterOption) {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            int courseCount =  courseDao.getFilteredCoursesCount(filterOption);
            return courseCount;
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }

    public List<Course> getFilteredCourses(CourseSortParameter sortParameter, CourseFilterOption filterOption) {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            List<Course> courses = courseDao.getFilteredCourses(sortParameter, filterOption);
            return courses;
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }

    public List<Course> getFilteredCoursesPage(int currentPage, int itemsPerPage,
                                               CourseSortParameter sortParameter, CourseFilterOption filterOption) {
        int offset = itemsPerPage * (currentPage - 1);
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            List<Course> courses = courseDao.getFilteredCourses(offset, itemsPerPage, sortParameter, filterOption);
            return courses;
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }

    public Course findCourseById(int id) throws EntityNotFoundException {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            return courseDao.findCourseById(id);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteCourseById(int id) throws IllegalDeleteException, EntityNotFoundException {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            courseDao.deleteCourse(id);
        } catch (IllegalDeleteException | EntityNotFoundException e) {
            throw e;
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }

    public void updateCourse(Course course) throws EntityNotFoundException {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            courseDao.updateCourse(course);
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }
}
