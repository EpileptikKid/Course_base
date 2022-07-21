package model.dao.impl;

import model.dao.Dao;
import model.exception.DBException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class JDBCAbstractDao implements Dao {
    protected Connection connection;
    protected JDBCAbstractDao(Connection connection) { this.connection = connection; }

    @Override
    public void close() throws DBException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
