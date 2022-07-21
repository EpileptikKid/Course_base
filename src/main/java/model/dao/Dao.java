package model.dao;

import model.exception.DBException;

public interface Dao extends AutoCloseable {
    @Override
    void close() throws DBException;
}
