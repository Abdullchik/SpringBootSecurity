package ru.kata.spring.boot_security.demo.dao;



import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    private final EntityManager entityManager;

    public UserDaoImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public List<User> getUsersList() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }
}