package ru.geekbrains.storage_demo_app.controller;

import ru.geekbrains.storage_demo_app.DAO.UserDAO;
import ru.geekbrains.storage_demo_app.entities.Role;
import ru.geekbrains.storage_demo_app.entities.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Named
@SessionScoped
public class AuthController implements Serializable {

    @EJB
    private UserDAO userDAO;

    private User user;
    private String name;
    private String surname;
    private String login;
    private String password;
    private Set<Role> roles;
    private int age;

    @PostConstruct
    public void init(){
        roles = new HashSet<>();
    }


    public String getRegistration(){
        return "/reg.xhtml";
    }

    public String createNewUser(){
        user = new User();
        Role role = new Role("User");
        roles.add(role);
        user.setLogin(login);
        user.setPassword(password);
        user.setRoles(roles);
        userDAO.createOrUpdateUser(user);
        FacesMessage message = new FacesMessage("Success");
        FacesContext.getCurrentInstance().addMessage(null, message);
        return "/index.xhtml";
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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


}