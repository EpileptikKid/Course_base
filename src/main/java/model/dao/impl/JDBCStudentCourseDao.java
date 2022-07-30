package model.dao.impl;

import model.dao.StudentCourseDao;
import model.exception.DBException;
import model.exception.EntityNotFoundException;
import model.util.SqlStatementLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCStudentCourseDao extends JDBCAbstractDao implements StudentCourseDao {
    static SqlStatementLoader loader = SqlStatementLoader.getInstance();
    public static final String ENROLL_USER = loader.getSqlStatement("enrollUser");

    protected JDBCStudentCourseDao(Connection connection) {
        super(connection);
    }

    @Override
    public void enrollStudent(int studId, int courseId) throws DBException, EntityNotFoundException {
        try (PreparedStatement enrollStudentStatement = connection.prepareStatement(ENROLL_USER)) {
            connection.setAutoCommit(false);
            enrollStudentStatement.setInt(1, studId);
            enrollStudentStatement.setInt(2, courseId);
            enrollStudentStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
