package ru.geekbrains.storage_demo_app.DAO;

import ru.geekbrains.storage_demo_app.entities.Folder;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Stateful
public class FolderDAO {

    @PersistenceContext(unitName = "ps")
    private EntityManager entityManager;



    public void createFolder(Folder folder) {
        entityManager.persist(folder);
    }

    public void updateFolder(Folder folder) {
        entityManager.merge(folder);
    }

    public Folder getFolderById(Long id) {
        return entityManager.find(Folder.class, id);
    }


    public List<Folder> getFoldersByUserId(Long id) {
        return new ArrayList<>(entityManager.createNamedQuery("GET_FOLDER_BY_USERID", Folder.class).setParameter("id", id).getResultList());
    }

    public Folder getRootFolder(Long id){
        return entityManager.createNamedQuery("GET_ROOT_FOLDER", Folder.class).setParameter("id", id).getSingleResult();
    }

    @Transactional
    public void deleteFolder(Folder folder){
        entityManager.remove(entityManager.contains(folder) ? folder : entityManager.merge(folder));
    }


}
