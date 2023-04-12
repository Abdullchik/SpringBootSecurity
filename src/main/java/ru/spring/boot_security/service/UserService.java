package ru.spring.boot_security.service;


import ru.spring.boot_security.model.User;

import java.util.List;

public interface UserService {

    void add(User user, List<String> roleList);

    void update(User user, List<String> roleNamesList);

    void delete(long id);

    List<User> getUsersList();
}