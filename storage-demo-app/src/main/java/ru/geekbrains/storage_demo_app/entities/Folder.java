package ru.geekbrains.storage_demo_app.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@NamedQueries({
        @NamedQuery(name = "GET_FOLDER_BY_USERID", query = "select f from Folder f inner join User u on f.user.id = u.id where u.id = :id"),
        @NamedQuery(name = "GET_ROOT_FOLDER", query = "select f from Folder f inner join User u on u.id = f.user.id where f.name ='Root' and u.id = :id"),
        @NamedQuery(name = "DELETE_FOLDER", query = "delete from Folder f where f.id = :id"),

})

@Entity
@Table(name = "folders")
public class Folder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String path;
    private String type;
    private Long size;
    private String icon;

    @ManyToOne
    private User user;

    @OneToMany (fetch = FetchType.EAGER)
    @JoinColumn(name = "folder_id")
    private List<File> files;

    @Transient
    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Folder parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Folder> children;


    public Folder(String name) {
        this.name = name;
    }

    public Folder(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Folder() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }


    @Override
    public String toString() {
        return name;
    }

    public List<Folder> getChildren() {
        return children;
    }

    public void setChildren(List<Folder> children) {
        this.children = children;
    }

}
