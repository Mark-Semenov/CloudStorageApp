package ru.geekbrains.storage_demo_app.controller;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import ru.geekbrains.storage_demo_app.entities.DownloadFile;
import ru.geekbrains.storage_demo_app.entities.File;
import ru.geekbrains.storage_demo_app.entities.Folder;
import ru.geekbrains.storage_demo_app.service.FileProcess;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class IndexController implements Serializable {

    @EJB
    private FileProcess fileProcess;

    private UploadedFile file;
    private UploadedFiles files;
    private List<File> fileList;
    private final List<File> fileBuffer = new ArrayList<>();
    private File selectedFile;
    private StreamedContent downloadFile;
    private TreeNode root;
    private TreeNode selectedNode;

    @Inject
    private HttpSession httpSession;


    @PostConstruct
    public void init() {


        root = createDocuments();




        fileList = fileProcess.getFiles();
    }


    public TreeNode createDocuments() {
        TreeNode root = new DefaultTreeNode(new Folder("Files", "Folder"), null);
        TreeNode applications = new DefaultTreeNode(new Folder("Applications", "Folder"), root);
        TreeNode cloud = new DefaultTreeNode(new Folder("Cloud", "Folder"), root);
        TreeNode desktop = new DefaultTreeNode(new Folder("Desktop", "Folder"), root);
        TreeNode documents = new DefaultTreeNode(new Folder("Documents", "Folder"), root);
        TreeNode downloads = new DefaultTreeNode(new Folder("Downloads", "Folder"), root);
        TreeNode main = new DefaultTreeNode(new Folder("Main", "Folder"), root);
        TreeNode other = new DefaultTreeNode(new Folder("Other", "Folder"), root);
        TreeNode pictures = new DefaultTreeNode(new Folder("Pictures", "Folder"), root);
        TreeNode videos = new DefaultTreeNode(new Folder("Videos", "Folder"), root);


        TreeNode primeface = new DefaultTreeNode(new Folder("Primefaces", "Folder"), applications);
        TreeNode primefacesapp = new DefaultTreeNode("app", new Folder("primefaces.app", "Application"), cloud);
        TreeNode nativeapp = new DefaultTreeNode("app", new Folder("native.app", "Application"), desktop);
        TreeNode mobileapp = new DefaultTreeNode("app", new Folder("mobile.app", "Application"), documents);
        TreeNode editorapp = new DefaultTreeNode("app", new Folder("editor.app", "Application"), downloads);
        TreeNode settingsapp = new DefaultTreeNode("app", new Folder("settings.app", "Application"), pictures);
        return root;

    }

    public String logout() {
        httpSession.invalidate();
        return "/authentication.xhtml?faces-redirect=true";
    }


    public void deleteSelectedFile() {
        if (selectedFile == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("File isn't selected"));
        } else {
            fileProcess.deleteFile(selectedFile.getId());
            selectedFile = null;
            downloadFile = null;
        }
    }

    public File findFile(Long id) {
        return fileProcess.findFileById(id);
    }

    public StreamedContent getFileDownload() {
        if (selectedFile == null) {
            FacesMessage message = new FacesMessage("File isn't selected");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            downloadFile = new DownloadFile(findFile(selectedFile.getId()));
        }
        return downloadFile;
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile upFile = event.getFile();
        File file = new File(upFile.getFileName(), upFile.getContentType(), upFile.getContent(), upFile.getSize());
        fileBuffer.add(file);
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        fileProcess.upload(fileBuffer);
    }

    public void onRowSelect(SelectEvent<File> event) {
        selectedFile = event.getObject();
        FacesMessage msg = new FacesMessage("File Selected", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent<File> event) {
        selectedFile = null;
        downloadFile = null;
        FacesMessage msg = new FacesMessage("File Unselected", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFiles getFiles() {
        return files;
    }

    public void setFiles(UploadedFiles files) {
        this.files = files;
    }

    public StreamedContent getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(StreamedContent downloadFile) {
        this.downloadFile = downloadFile;
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
}

