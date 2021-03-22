package ru.geekbrains.storage_demo_app.DAO;

import ru.geekbrains.storage_demo_app.entities.Folder;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateful
public class FolderDAO {

    @PersistenceContext(unitName = "ps")
    private EntityManager entityManager;


    public List<Folder> getAllFolders() {
        return entityManager.createNamedQuery("GET_FOLDERS", Folder.class).getResultList();
    }

    public void createFolder(Folder folder) {
        entityManager.persist(folder);
    }

    public void updateFolder(Folder folder) {
        entityManager.merge(folder);
    }

    public Folder getFolderById(Long id) {
        return entityManager.find(Folder.class, id);
    }

    public List<Folder> getFoldersById(Long id) {
        return entityManager.createNamedQuery("GET_FOLDER_BY_ID", Folder.class).setParameter("id", id).getResultList();
    }

    public List<Folder> getFoldersByUserId() {
        return entityManager.createNamedQuery("GET_FOLDER_BY_USERID", Folder.class).getResultList();
    }

    public void deleteFolderById(Long id) {
        entityManager.remove(getFolderById(id));
    }


}
