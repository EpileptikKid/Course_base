package model.service;

import model.dao.UserDao;
import model.dao.impl.JDBCDaoFactory;
import model.entity.User;
import model.exception.DBException;
import model.exception.EntityAlreadyExistsException;
import model.exception.EntityNotFoundException;
import model.service.exception.ServiceException;
import model.service.exception.WrongPasswordException;

import java.util.List;

public class UserService {

    public void registerUser(User user) throws EntityAlreadyExistsException {
        try (UserDao dao = new JDBCDaoFactory().createUserDao()) {
            dao.createUser(user);
        } catch (EntityAlreadyExistsException e) {
            throw new EntityAlreadyExistsException();
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }

    public User signInUser(String login, String password) throws EntityNotFoundException, WrongPasswordException {
        try (UserDao dao = new JDBCDaoFactory().createUserDao()) {
            User user = dao.findUserByLogin(login);
            if (!password.equals(user.getPassword())) {

                throw new WrongPasswordException();
            }
            return user;
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }

    public List<User> findAllUsers() {
        try (UserDao dao = new JDBCDaoFactory().createUserDao()) {
            return dao.findAll();
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }

}
