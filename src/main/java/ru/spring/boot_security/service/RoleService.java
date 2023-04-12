package ru.spring.boot_security.service;

import ru.spring.boot_security.model.Role;

import java.util.Set;

public interface RoleService {
    void add(Role role);

    Role findByName(String roleName);

    void saveAll(Set<Role> roleSet);
}
