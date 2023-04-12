package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void add(User user, List<String> roleList);

    void update(User user, List<String> roleNamesList);

    void delete(long id);

    List<User> getUsersList();
}