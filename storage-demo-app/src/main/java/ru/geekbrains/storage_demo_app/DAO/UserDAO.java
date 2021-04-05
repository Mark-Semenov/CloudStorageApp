package ru.geekbrains.storage_demo_app.DAO;


import ru.geekbrains.storage_demo_app.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
public class UserDAO {

    @PersistenceContext (unitName = "ps")
    EntityManager entityManager;

    @Transactional
    public void createOrUpdateUser(User user){
        if(user.getId() != null){
            entityManager.merge(user);
        } else entityManager.persist(user);
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
