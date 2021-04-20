package ru.geekbrains.storage_demo_app.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "GET_ROLES_BY_NAME", query = "from Role r where r.name = :name")
})

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @NotNull
    private long id;

    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {

    }

    public Role(@NotNull String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


}