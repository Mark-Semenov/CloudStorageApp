package ru.geekbrains.storage_demo_app.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


@NamedQueries({
        @NamedQuery(name = "GET_USERS", query ="from User"),
        @NamedQuery(name = "CHECK_USER", query = "from User u where u.login = :login and u.password = :password"),
        @NamedQuery(name = "GET_USER_BY_ID", query = "from User u where u.id = :id"),
        @NamedQuery(name = "GET_USER_BY_LOGIN", query = "from User u where u.login = :login"),

})

@Entity
@Table (name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private int age;

    @OneToMany
    @JoinColumn (name = "user_id")
    private List<Folder> folders;

    @OneToMany
    @JoinColumn (name = "user_id")
    private List<File> files;


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Transient
    private static final long serialVersionUID = 1L;

    public User() {
    }

    public User(String firstName, String lastName, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String nickName) {
        this.login = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}

