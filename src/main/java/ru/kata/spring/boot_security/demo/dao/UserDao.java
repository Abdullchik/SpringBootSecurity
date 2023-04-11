package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void add(User car);

    User get(String username);

    void update(User user);

    void delete(long id);

    List<User> getUsersList();
}
