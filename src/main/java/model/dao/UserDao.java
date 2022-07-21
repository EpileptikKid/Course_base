package model.dao;

import model.entity.User;
import model.exception.DBException;
import model.exception.EntityAlreadyExistsException;
import model.exception.EntityNotFoundException;

import java.util.List;

public interface UserDao extends Dao {
    void createUser(User user) throws DBException, EntityAlreadyExistsException;
    List<User> findAll() throws DBException;
    List<User> findUserByRole(User.Role role) throws DBException;
    User findUserByLogin(String login) throws DBException, EntityNotFoundException;
    void userUpdateIsBlockedStatus(User user) throws DBException, EntityNotFoundException;
}
