package ru.spring.boot_security.service;

import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.boot_security.dao.UserDao;
import ru.spring.boot_security.model.Role;
import ru.spring.boot_security.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService, UserDetailsService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    public UserServiceImp(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public void add(User user, Set<String> roleNameSet) {
        userDao.add(UserCheck(user, roleNameSet));
    }


    @Transactional
    @Override
    public void update(User user, Set<String> roleNameSet) {
        userDao.update(UserCheck(user, roleNameSet));
    }

    @Transactional
    @Override
    public void delete(long id) {
        try {
            userDao.delete(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Введены неверные данные");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsersList() {
        return userDao.getUsersList();
    }

    @Transactional()
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.get(username);
        if (user != null) {
            Hibernate.initialize(user.getRoleSet());
        }
        return user;
    }

    @Transactional
    protected User UserCheck(User user, Set<String> roleNameSet) {
        if (userDao.get(user.getName()) != null) {
            throw new IllegalArgumentException("Пользователь с таким именем существует");
        }
        Set<Role> roleSet = new HashSet<>();
        System.out.println(roleNameSet);
        for (String s : roleNameSet) {
            Role role = roleService.findByName(s);
            roleSet.add(role);
        }
        user.setPass(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoleSet(roleSet);
        return user;
    }

}