package ru.geekbrains.storage_demo_app.DAO;


import ru.geekbrains.storage_demo_app.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserDAO {

    @PersistenceContext (unitName = "ps")
    EntityManager entityManager;

    public void createOrUpdateUser(User user){
        if(user.getId() == null){
            entityManager.merge(user);
        } else entityManager.persist(user);
    }

    public List<User> getUsers(){
        return entityManager.createNamedQuery("GET_USERS", User.class).getResultList();
    }

    public User getUserById(Long id){
        return entityManager.find(User.class, id);
    }

    public void deleteUser(User user){
        entityManager.remove(user);
    }

    public User getUserByLogin(String login){
        return entityManager.createNamedQuery("GET_USER_BY_LOGIN", User.class).setParameter("login", login).getSingleResult();
    }

}
