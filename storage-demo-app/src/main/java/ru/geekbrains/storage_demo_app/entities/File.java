package ru.geekbrains.storage_demo_app.entities;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({

        @NamedQuery(name = "GET_FILES", query = "from File"),
        @NamedQuery(name = "GET_FILES_BY_USER_ID", query = "from File f inner join User u on f.user.id = u.id where u.id = :id"),
        @NamedQuery(name = "GET_FILES_BY_USER_NAME", query = "select f from File f inner join User u on f.user.id = u.id where u.login = :login"),
})


@Entity
@Table(name = "files")
public class File implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    private byte [] content;
    private Long size;


    @Transient
    private static final long serialVersionUID = 1L;

    @ManyToOne
    private User user;

    public File() {
    }

    public File(String name, String type, byte[] content, Long size) {
        this.name = name;
        this.type = type;
        this.content = content;
        this.size = size;
    }

    public File(Long id, String name, byte[] content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public File(String name) {
        this.name = name;
    }

    public File(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
