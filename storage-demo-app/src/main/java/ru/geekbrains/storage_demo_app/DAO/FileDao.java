package ru.geekbrains.storage_demo_app.DAO;

import ru.geekbrains.storage_demo_app.entities.File;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateful
public class FileDao {

    @PersistenceContext(unitName = "ps")
    EntityManager entityManager;


    public List<File> getAllFiles() {
        return entityManager.createNamedQuery("GET_FILES", File.class).getResultList();
    }

    public void writeFile(File file) {
        entityManager.persist(file);
    }

    public File getFileById(Long id) {
        return entityManager.find(File.class, id);
    }


    public List<File> getFilesByUserId(Long id) {
        return entityManager.createNamedQuery("GET_FILES_BY_USER_ID", File.class).setParameter("id", id).getResultList();
    }

    public List<File> getFilesByUserName(String login) {
        return entityManager.createNamedQuery("GET_FILES_BY_USER_NAME", File.class).setParameter("login", login).getResultList();
    }


    public void deleteFileById(Long id) {
        entityManager.remove(getFileById(id));
    }


}
