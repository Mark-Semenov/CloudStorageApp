package ru.geekbrains.storage_demo_app.DAO;

import ru.geekbrains.storage_demo_app.entities.File;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateful
public class FileDao {

    @PersistenceContext(unitName = "ps")
    EntityManager entityManager;

    @Transactional
    public void writeFile(File file) {
        entityManager.persist(file);
    }

    public File getFileById(Long id) {
        return entityManager.find(File.class, id);
    }


    public void deleteFileById(Long id) {
        entityManager.remove(getFileById(id));
    }

    public List<File> getFilesByFolderId(Long id){
        return entityManager.createNamedQuery("GET_FILES_BY_FOLDER_ID", File.class).setParameter("id", id).getResultList();
    }


}
