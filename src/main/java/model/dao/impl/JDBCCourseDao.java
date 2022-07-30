package model.dao.impl;

import model.dao.CourseDao;
import model.dao.mapper.CourseMapper;
import model.dao.mapper.ThemeMapper;
import model.dao.mapper.UserMapper;
import model.entity.Course;
import model.entity.Theme;
import model.entity.User;
import model.exception.DBException;
import model.util.SqlStatementLoader;

import java.sql.*;
import java.util.*;

public class JDBCCourseDao extends JDBCAbstractDao implements CourseDao {

    private static final String FIND_ALL_COURSE;


    static {
        SqlStatementLoader loader = SqlStatementLoader.getInstance();
        FIND_ALL_COURSE = loader.getSqlStatement("findAllCourse");
    }

    protected JDBCCourseDao(Connection connection) { super(connection); }






    @Override
    public List<Course> findAllCourse() throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_COURSE)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractCoursesAsList(resultSet);
        } catch (SQLException ex) {
            throw new DBException(ex);
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
