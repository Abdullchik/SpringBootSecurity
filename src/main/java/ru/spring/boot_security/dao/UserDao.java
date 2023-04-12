package ru.spring.boot_security.dao;


import ru.spring.boot_security.model.User;

import java.util.List;

public interface UserDao {
    void add(User car);

    User get(String username);

    void update(User user);

    void delete(long id);

    List<User> getUsersList();
}
