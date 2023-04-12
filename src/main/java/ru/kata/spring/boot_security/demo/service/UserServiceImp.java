package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private static boolean MARK;

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImp(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public void add(User user, List<String> roleNamesList) {
        userDao.add(UserCheck(user, roleNamesList));
    }



    @Transactional
    @Override
    public void update(User user, List<String> roleNamesList) {
        userDao.update(UserCheck(user, roleNamesList));
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
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CreateAdmin();
        return userDao.get(username);
    }

    //для создания учетки админа при запуске приложения
    @Transactional
    protected void CreateAdmin() {
        if(!MARK) {
            List<Role> roleList = List.of(new Role("ROLE_ADMIN"), new Role("ROLE_USER"));
            roleRepository.saveAll(roleList);
            userDao.add(new User("Admin", bCryptPasswordEncoder.encode("12345"), roleList));
            MARK = true;
        }
    }

    private User UserCheck (User user, List<String> roleNamesList) {
        if (userDao.get(user.getName()) != null) {
            throw new IllegalArgumentException("Пользователь с таким именем существует");
        }
        List<Role> roleList = new ArrayList<>();
        System.out.println(roleNamesList);
        for (String s : roleNamesList) {
            Role role = roleRepository.findByName(s);
            roleList.add(role);
        }
        user.setPass(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoleList(roleList);
        return user;
    }

}