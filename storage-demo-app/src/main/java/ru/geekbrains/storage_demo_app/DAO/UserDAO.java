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


    public void createUser(User user){
        entityManager.persist(user);
    }

    public List<User> getUsers(){
        return new ArrayList<>(entityManager.createNamedQuery("User.users", User.class).getResultList());
    }

}
