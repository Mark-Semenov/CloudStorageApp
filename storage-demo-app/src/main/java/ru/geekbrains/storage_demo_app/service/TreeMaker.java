package ru.geekbrains.storage_demo_app.service;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import ru.geekbrains.storage_demo_app.entities.Folder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class TreeMaker {

    private final List<Folder> folders;

    public TreeMaker(List<Folder> folders) {
        this.folders = new ArrayList<>(folders);
    }

    public TreeNode getView() {
        TreeNode root = new DefaultTreeNode(getRootFolder(), null);
        for (Folder folder : folders) {
            if (folder.getParent() != null && folder.getParent().getId().equals(getRootFolder().getId())) {
                TreeNode t = new DefaultTreeNode(folder, root);
                getTree(folder, t);
            }

        }

        return root;
    }

    private void getTree(@NotNull Folder folder, TreeNode node) {
        for (Folder f : folder.getChildren()) {
            getTree(f, new DefaultTreeNode(f, node));
        }
    }

    private Folder getRootFolder() {
        Folder root = null;
        for (Folder folder : this.folders) {
            if (folder.getName().equals("Root")) {
                root = folder;
            }
        }
        return root;
    }
}