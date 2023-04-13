package ru.spring.boot_security.service;



import ru.spring.boot_security.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    void add(User user, Set<String> roleNameSet);

    void update(User user, Set<String> roleNameSet);

    void delete(long id);

    List<User> getUsersList();
}