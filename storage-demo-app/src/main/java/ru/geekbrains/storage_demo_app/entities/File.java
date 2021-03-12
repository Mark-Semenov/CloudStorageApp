package ru.geekbrains.storage_demo_app.entities;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({

        @NamedQuery(name = "File.files", query = "from File")
})


@Entity
@Table(name = "files")
public class File implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private byte [] content;
    private Long size;

    @Transient
    private static final long serialVersionUID = 1L;

    public File() {
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
}
