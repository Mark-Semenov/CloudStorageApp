package ru.geekbrains.storage_demo_app.DAO;

import ru.geekbrains.storage_demo_app.entities.Role;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
public class RoleDAO {

    @PersistenceContext(unitName = "ps")
    private EntityManager em;

    public Role getRole(String name){
        return em.createNamedQuery("GET_ROLES_BY_NAME", Role.class).setParameter("name", name).getSingleResult();
    }
}
