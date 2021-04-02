package ru.geekbrains.storage_demo_app.service;

import org.primefaces.model.TreeNode;
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
import javax.validation.constraints.NotNull;
import java.io.IOException;
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

    private Folder rootUserFolder;

    private List<Folder> userFolders;

    private TreeNode treeViewRoot;

    @PostConstruct
    public void init() {

        this.user = userDAO.getUserByLogin(sessionContext.getCallerPrincipal().getName());
        userFolders = folderDAO.getFoldersByUserId(this.user.getId());
        if (userFolders.isEmpty()) {
            rootUserFolder = new Folder("Root", "Folder");
            rootUserFolder.setUser(this.user);
            createFolder(rootUserFolder);
        } else rootUserFolder = findRootFolder(this.user.getId());

        treeViewRoot = new TreeMaker(userFolders).getView();

    }


    public void upload(List<File> files) {
        File f = new File();
        for (File file : files) {
            f.setFolder(file.getFolder());
            f.setName(file.getName());
            f.setSize(file.getSize());
            f.setContent(file.getContent());
            f.setType(file.getType());
            fileDao.writeFile(f);
            writeFileAtHDD(f);
        }
    }

    @TransactionAttribute
    public void deleteFile(@NotNull Long id) {
        File file = fileDao.getFileById(id);
        deleteFileFromHDD(file);
        fileDao.deleteFileById(id);
    }

    public List<File> getFiles() {
        return fileDao.getFilesByFolderId(rootUserFolder.getId());
    }


    public List<Folder> getFolders() {
        return folderDAO.getFoldersByUserId(user.getId());
    }


    public File findFileById(@NotNull Long id) {
        return fileDao.getFileById(id);
    }

    public Folder findFolder(Folder folder) {
        return folderDAO.getFolderById(folder.getId());
    }

    @TransactionAttribute
    public void createFolder(@NotNull Folder folder) {
        folder.setUser(this.user);
        folderDAO.createFolder(folder);
    }

    @TransactionAttribute
    public void deleteFolder (@NotNull Folder folder){
        folderDAO.deleteFolder(folder);
    }

    public Folder findRootFolder(@NotNull Long userId) {
        return folderDAO.getRootFolder(userId);
    }

    public void updateFolder(Folder folder) {
        folderDAO.updateFolder(folder);
    }


    //Запись на диск в директорию с именем <Логин> пользователя

    public void writeFileAtHDD(@NotNull File file) {

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
    public void deleteFileFromHDD(@NotNull File file) {
        Path path = Paths.get("D:\\" + user.getLogin() + "\\" + file.getName());
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Folder getRootUserFolder() {
        return rootUserFolder;
    }

    public void setRootUserFolder(Folder rootUserFolder) {
        this.rootUserFolder = rootUserFolder;
    }

    public List<Folder> getUserFolders() {
        return userFolders;
    }

    public void setUserFolders(List<Folder> userFolders) {
        this.userFolders = userFolders;
    }

    public TreeNode getTreeViewRoot() {
        return treeViewRoot;
    }
}
