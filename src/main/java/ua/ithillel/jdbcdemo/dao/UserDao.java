package ua.ithillel.jdbcdemo.dao;

import ua.ithillel.jdbcdemo.exception.UserAppException;
import ua.ithillel.jdbcdemo.model.User;

import java.util.List;

public interface UserDao {
    User findById(Integer id) throws UserAppException;
    User findByEmail(String email) throws UserAppException;
    List<User> findAll() throws UserAppException;
    User save(User user) throws UserAppException;
    User update(User user);
}
