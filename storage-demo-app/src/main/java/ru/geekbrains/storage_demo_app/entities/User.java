package ru.geekbrains.storage_demo_app.entities;

import javax.persistence.*;
import java.io.Serializable;


@NamedQueries({
        @NamedQuery(name = "User.users", query ="from User"),
        @NamedQuery(name = "User.checkByLogPas", query = "from User u where u.nickName = :nickname and u.password = :password")
})

@Entity
@Table (name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String nickName;
    private String password;
    private String privileges;
    private int age;

    public User() {
    }

    public User(String firstName, String lastName, String nickName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
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
}

