package ru.geekbrains.storage_demo_app.DAO;

import ru.geekbrains.storage_demo_app.entities.File;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateful
public class FileDao {

    @PersistenceContext (unitName = "ps")
    EntityManager entityManager;


    public List<File> getAllFiles(){
        return entityManager.createNamedQuery("File.files", File.class).getResultList();
    }

    public void writeFile(File file){
        entityManager.persist(file);
    }

}
