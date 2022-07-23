package model.dao.impl;

import model.dao.CourseDao;
import model.dao.mapper.CourseMapper;
import model.dao.mapper.ThemeMapper;
import model.dao.mapper.UserMapper;
import model.entity.Course;
import model.entity.Theme;
import model.entity.User;
import model.entity.filter.CourseFilterOption;
import model.entity.filter.CourseSortParameter;
import model.exception.DBException;
import model.exception.EntityNotFoundException;
import model.exception.IllegalDeleteException;
import model.util.SqlStatementLoader;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JDBCCourseDao extends JDBCAbstractDao implements CourseDao {
    private static final String FIND_COURSE_BY_ID;
    private static final String CREATE_COURSE;
    private static final String UPDATE_COURSE;
    private static final String DELETE_COURSE_BY_ID;

    public static final String GET_FILTERED_COURSES;
    private static final String GET_FILTERED_COURSE_PAGE;

    private static final String SORT_BY_NAME_ASC;
    private static final String SORT_BY_NAME_DESC;
    private static final String SORT_BY_STUDENTS_ASC;
    private static final String SORT_BY_STUDENTS_DESC;
    private static final String SORT_BY_DURATION_ASC;
    private static final String SORT_BY_DURATION_DESC;

    private static final String COUNT_COURSES_FILTERED;
    public static final String FIND_ONGOING;
    public static final String FIND_NOT_STARTED;
    public static final String FIND_COMPLETED;

    static {
        SqlStatementLoader loader = SqlStatementLoader.getInstance();
        CREATE_COURSE = loader.getSqlStatement("createCourse");

        FIND_COURSE_BY_ID = loader.getSqlStatement("findCourseById");
        UPDATE_COURSE = loader.getSqlStatement("updateCourse");
        DELETE_COURSE_BY_ID = loader.getSqlStatement("deleteCourseById");

        SORT_BY_NAME_ASC = loader.getSqlStatement("sortByNameAsc");
        SORT_BY_NAME_DESC = loader.getSqlStatement("sortByNameDesc");
        SORT_BY_STUDENTS_ASC = loader.getSqlStatement("sortByStudentsAsc");
        SORT_BY_STUDENTS_DESC = loader.getSqlStatement("sortByStudentsDesc");
        SORT_BY_DURATION_ASC = loader.getSqlStatement("sortByDurationAsc");
        SORT_BY_DURATION_DESC = loader.getSqlStatement("sortByDurationDesc");

        COUNT_COURSES_FILTERED = loader.getSqlStatement("countCoursesFiltered");

        GET_FILTERED_COURSES = loader.getSqlStatement("getFilteredCourses");
        GET_FILTERED_COURSE_PAGE = loader.getSqlStatement("getAvailableCoursesPage");

        FIND_ONGOING = loader.getSqlStatement("findOngoing");
        FIND_NOT_STARTED = loader.getSqlStatement("findNotStarted");
        FIND_COMPLETED = loader.getSqlStatement("findCompleted");
    }

    protected JDBCCourseDao(Connection connection) { super(connection); }

    private void setCourseParameters(Course course, PreparedStatement createCourseStatement) throws SQLException {
        createCourseStatement.setString(1, course.getName());
        createCourseStatement.setDate(2, Date.valueOf(course.getStartDate()));
        createCourseStatement.setDate(3, Date.valueOf(course.getEndDate()));
        createCourseStatement.setInt(4, course.getTheme().getId());
        createCourseStatement.setString(5, course.getDescription());
    }

    @Override
    public void updateCourse(Course course) throws DBException, EntityNotFoundException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COURSE)) {
            setCourseParameters(course, preparedStatement);
            User tutor = course.getTutor();
            if (tutor != null) {
                preparedStatement.setInt(6, tutor.getId());
            } else {
                preparedStatement.setNull(6, Types.INTEGER);
            }

            preparedStatement.setInt(7, course.getId());
            if (preparedStatement.executeUpdate() < 1) {
                throw new EntityNotFoundException();
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void createCourse(Course course) throws DBException, EntityNotFoundException {
        try (PreparedStatement createCourseStatement = connection.prepareStatement(CREATE_COURSE)) {
            setCourseParameters(course, createCourseStatement);
            User tutor = course.getTutor();
            if (tutor != null) {
                createCourseStatement.setInt(6, tutor.getId());
            } else {
                createCourseStatement.setNull(6, Types.INTEGER);
            }
            createCourseStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new EntityNotFoundException(e);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Course findCourseById(int id) throws DBException, EntityNotFoundException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_COURSE_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new EntityNotFoundException();
            }
            return new CourseMapper().extract(resultSet);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void deleteCourse(int id) throws DBException, EntityNotFoundException, IllegalDeleteException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COURSE_BY_ID)) {
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() < 1) {
                throw new EntityNotFoundException();
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new IllegalDeleteException();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void close() throws DBException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public int getFilteredCoursesCount(CourseFilterOption filterOption) throws DBException {
        String filterByStatusStatement = getFilterByStatusStatement(filterOption);
        String sql = String.format(COUNT_COURSES_FILTERED, filterByStatusStatement);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int themeId = Optional.ofNullable(filterOption.getTheme()).map(Theme::getId).orElse(0);
            int tutorId = Optional.ofNullable(filterOption.getTutor()).map(User::getId).orElse(0);
            preparedStatement.setInt(1, themeId);
            preparedStatement.setInt(2, themeId);
            preparedStatement.setInt(3, tutorId);
            preparedStatement.setInt(4, tutorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Course> getFilteredCourses(CourseSortParameter sortParameter, CourseFilterOption filterOption) throws DBException {
        String filterByStatus = getFilterByStatusStatement(filterOption);
        String sql = String.format(GET_FILTERED_COURSES, filterByStatus, getOrderByStatement(sortParameter));
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            prepareFilterStatement(filterOption, preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            return extractCoursesAsList(resultSet);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Course> getFilteredCourses(int offset, int numberOfItems, CourseSortParameter sortParameter, CourseFilterOption filterOption) throws DBException {
        String filterByStatus = getFilterByStatusStatement(filterOption);
        String sql = String.format(GET_FILTERED_COURSE_PAGE, filterByStatus, getOrderByStatement(sortParameter));
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            prepareFilterStatement(filterOption, preparedStatement);

            preparedStatement.setInt(7, offset);
            preparedStatement.setInt(8, numberOfItems);

            ResultSet resultSet = preparedStatement.executeQuery();
            return extractCoursesAsList(resultSet);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private String getFilterByStatusStatement(CourseFilterOption courseFilterOption) {
        CourseFilterOption.CourseStatus courseStatus = courseFilterOption.getCourseStatus();
        if (CourseFilterOption.CourseStatus.COMPLETED.equals(courseStatus)) {
            return FIND_COMPLETED;
        } else if (CourseFilterOption.CourseStatus.NOT_STARTED.equals(courseStatus)) {
            return FIND_NOT_STARTED;
        } else if (CourseFilterOption.CourseStatus.ONGOING.equals(courseStatus)) {
            return FIND_ONGOING;
        }
        return "0 = 0";
    }

    private String getOrderByStatement(CourseSortParameter sortParameter) {
        String orderByStatement;
        if (CourseSortParameter.BY_NAME_DESC.equals(sortParameter)) {
            orderByStatement = SORT_BY_NAME_DESC;
        } else if (CourseSortParameter.BY_DURATION_ASC.equals(sortParameter)) {
            orderByStatement = SORT_BY_DURATION_ASC;
        } else if (CourseSortParameter.BY_DURATION_DESC.equals(sortParameter)) {
            orderByStatement = SORT_BY_DURATION_DESC;
        } else if (CourseSortParameter.BY_STUDENTS_ASC.equals(sortParameter)) {
            orderByStatement = SORT_BY_STUDENTS_ASC;
        } else if (CourseSortParameter.BY_STUDENTS_DESC.equals(sortParameter)) {
            orderByStatement = SORT_BY_STUDENTS_DESC;
        } else {
            orderByStatement = SORT_BY_NAME_ASC;
        }
        return orderByStatement;
    }

    private void prepareFilterStatement(CourseFilterOption filterOption, PreparedStatement preparedStatement) throws SQLException {
        int themeId = Optional.ofNullable(filterOption.getTheme()).map(Theme::getId).orElse(0);
        int tutorId = Optional.ofNullable(filterOption.getTutor()).map(User::getId).orElse(0);
        int studId = Optional.ofNullable(filterOption.getAvailableForStudent()).map(User::getId).orElse(0);

        preparedStatement.setInt(1, themeId);
        preparedStatement.setInt(2, themeId);
        preparedStatement.setInt(3, tutorId);
        preparedStatement.setInt(4, tutorId);
        preparedStatement.setInt(5, studId);
        preparedStatement.setInt(6, studId);
    }

    private List<Course> extractCoursesAsList(ResultSet resultSet) throws SQLException {
        List<Course> courses = new ArrayList<>();
        CourseMapper courseMapper = new CourseMapper();
        UserMapper userMapper = new UserMapper();
        ThemeMapper themeMapper = new ThemeMapper();

        Map<Integer, Theme> themeCache = new HashMap<>();
        Map<Integer, User> tutorCache = new HashMap<>();
        while (resultSet.next()) {
            Course course = courseMapper.extract(resultSet);
            User tutor = userMapper.makeUnique(tutorCache, course.getTutor());
            Theme theme = themeMapper.makeUnique(themeCache, course.getTheme());
            course.setTheme(theme);
            course.setTutor(tutor);
            courses.add(course);
        }
        return courses;
    }
}
