package ru.geekbrains.storage_demo_app.service;

import org.primefaces.model.file.UploadedFile;
import ru.geekbrains.storage_demo_app.DAO.FileDao;
import ru.geekbrains.storage_demo_app.entities.File;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import java.io.IOException;
import java.util.List;

@Stateful
public class FileProcess {

    @EJB
    private FileDao fileDao;

    private final File file = new File();


    @PostConstruct
    public void init(){

    }

    @TransactionAttribute
    public void upload(UploadedFile file) {
            try {
                this.file.setContent(file.getInputStream().readAllBytes());
                this.file.setName(file.getFileName().toString());
                this.file.setType(file.getContentType());
                this.file.setSize(file.getSize());
                fileDao.writeFile(this.file);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public List<File> getFiles(){
      return fileDao.getAllFiles();
    }


}
