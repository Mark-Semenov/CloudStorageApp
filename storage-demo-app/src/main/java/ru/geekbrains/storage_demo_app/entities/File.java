package ru.geekbrains.storage_demo_app.entities;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({
        @NamedQuery(name = "GET_FILES_BY_FOLDER_ID", query = "select f from File f where f.folder.id = :id"),
        @NamedQuery(name = "GET_FILES", query = "select f from File f"),
})


@Entity
@Table(name = "files")
public class File implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;

    @Transient
    private byte [] content;
    @Transient
    private static final long serialVersionUID = 1L;

    private Long size;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "folder_id")
    private Folder folder;

    public File() {
    }

    public File(String name, String type, Long size, byte[] content) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.content = content;
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

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }


    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

