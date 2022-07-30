package model.dao.impl;

import model.dao.CourseDao;
import model.dao.DaoFactory;
import model.dao.StudentCourseDao;
import model.dao.UserDao;
import model.exception.DBException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory implements DaoFactory {
    private final DataSource dataSource = ConnectionPool.getDataSource();
    @Override
    public UserDao createUserDao() throws DBException {
        return new JDBCUserDao(getConnection());
    }

    @Override
    public CourseDao createCourseDao() throws DBException {
        return new JDBCCourseDao(getConnection());
    }


    @Override
    public StudentCourseDao createStudentCourseDao() throws DBException {
        return new JDBCStudentCourseDao(getConnection());
    }
    private Connection getConnection() throws DBException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
