package ru.spring.boot_security.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.boot_security.dao.RoleDao;
import ru.spring.boot_security.model.Role;

import java.util.Set;

@Service
public class RoleServiceImp implements RoleService{

    private final RoleDao roleDao;

    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    @Transactional
    @Override
    public void add(Role role) {
        roleDao.add(role);
    }
    @Transactional(readOnly = true)
    @Override
    public Role findByName(String roleName) {
        return roleDao.findByName(roleName);
    }
    @Transactional
    @Override
    public void saveAll(Set<Role> roleSet) {
        for(Role r: roleSet) {
            roleDao.add(r);        }
    }
}
