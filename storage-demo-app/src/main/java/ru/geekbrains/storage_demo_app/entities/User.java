package ru.geekbrains.storage_demo_app.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


@NamedQueries({
        @NamedQuery(name = "GET_USERS", query = "from User"),
        @NamedQuery(name = "CHECK_USER", query = "from User u where u.login = :login and u.password = :password"),
        @NamedQuery(name = "GET_USER_BY_ID", query = "from User u where u.id = :id"),
        @NamedQuery(name = "GET_USER_BY_LOGIN", query = "from User u where u.login = :login"),

})

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String login;
    @NotNull
    private String password;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Folder> folders;


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Transient
    private static final long serialVersionUID = 1L;

    public User() {
    }


    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(Set<Role> roles) {
        this.roles = roles;
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

}

