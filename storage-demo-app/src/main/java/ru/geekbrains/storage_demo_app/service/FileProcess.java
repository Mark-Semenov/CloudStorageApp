package ru.geekbrains.storage_demo_app.service;

import ru.geekbrains.storage_demo_app.DAO.FileDao;
import ru.geekbrains.storage_demo_app.DAO.FolderDAO;
import ru.geekbrains.storage_demo_app.DAO.UserDAO;
import ru.geekbrains.storage_demo_app.entities.File;
import ru.geekbrains.storage_demo_app.entities.Folder;
import ru.geekbrains.storage_demo_app.entities.User;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Stateful
@RolesAllowed({"user", "admin"})
public class FileProcess {

    @EJB
    private FileDao fileDao;

    @EJB
    private UserDAO userDAO;

    @EJB
    private FolderDAO folderDAO;

    @Resource
    private SessionContext sessionContext;

    private User user;


    @PostConstruct
    public void init() {
        this.user = userDAO.getUserByLogin(sessionContext.getCallerPrincipal().getName());
    }

    @TransactionAttribute
    public void upload(List<File> files) {
        File f = new File();
        f.setUser(this.user);
        for (File file : files) {
            f.setName(file.getName());
            f.setSize(file.getSize());
            f.setContent(file.getContent());
            f.setType(file.getType());
            fileDao.writeFile(f);
            writeFileAtHDD(f);
        }
    }

    public void deleteFile(Long id) {
        File file = fileDao.getFileById(id);
        deleteFileFromHDD(file);
        fileDao.deleteFileById(id);
    }

    public List<File> getFiles() {
        if (!sessionContext.isCallerInRole("user")) {
            throw new SecurityException("роль не задана");
        } else return fileDao.getFilesByUserName(user.getLogin());
    }


    public File download(Long id) {
        return fileDao.getFileById(id);
    }

    public File findFileById(Long id) {
        return fileDao.getFileById(id);
    }


    public void createFolder(Folder folder) {
        folderDAO.createFolder(folder);
    }


    public List<File> showUserFiles(Long userId) {
        return fileDao.getFilesByUserId(userId);
    }

    public List<File> showUserFilesByName(String login) {
        return fileDao.getFilesByUserName(login);
    }


    //Запись на диск в директорию с именем <Логин> пользователя

    public void writeFileAtHDD(File file) {

        Path folderPats = Paths.get("D:\\" + user.getLogin());
        Path filePath = Paths.get("D:\\" + user.getLogin() + "\\" + file.getName());

        try {
            if (Files.notExists(folderPats)) {
                Files.createDirectory(folderPats);
            }

            Files.write(filePath, file.getContent());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Удаление файла
    public void deleteFileFromHDD(File file) {

        Path path = Paths.get("D:\\" + user.getLogin() + "\\" + file.getName());
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
