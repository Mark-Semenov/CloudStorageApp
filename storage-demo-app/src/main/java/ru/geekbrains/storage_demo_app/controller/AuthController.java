package ru.geekbrains.storage_demo_app.controller;

import ru.geekbrains.storage_demo_app.DAO.RoleDAO;
import ru.geekbrains.storage_demo_app.DAO.UserDAO;
import ru.geekbrains.storage_demo_app.entities.Role;
import ru.geekbrains.storage_demo_app.entities.User;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Named
@SessionScoped
@PermitAll
public class AuthController implements Serializable {

    @EJB
    private UserDAO userDAO;

    @EJB
    private RoleDAO roleDAO;

    @Inject
    private HttpServletRequest request;


    private User user;
    private String login;

    private String password;
    private Set<Role> roles;

    private int age;

    @PostConstruct
    public void init() {
        roles = new HashSet<>();
        user = new User();

    }


    public String getRegistration() {
        return "reg.xhtml";
    }

    public String getAuthXhtml(){
        return "authentication.xhtml";
    }


    public String createNewUser() {
        FacesMessage message;
        String link = null;
        if (!login.isEmpty() && !password.isEmpty()) {
            if (password.chars().count() < 7) {
                message = new FacesMessage("Reg Error","Password must be at least 7 characters");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                if (isLoginExist()) {
                    message = new FacesMessage("Reg Error", "This login is already exist");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } else {
                    roles.add(getUserRole("user"));
                    user.setLogin(login);
                    user.setPassword(password);
                    user.setRoles(roles);
                    message = new FacesMessage("Reg Success", "Welcome" + user.getLogin());
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    saveUser();
                    link = goToCloud();
                }
            }
        } else {
            message = new FacesMessage("Reg Error","Login or password is empty");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        return link;
    }


    public String goToCloud() {
        try {
            request.login(login, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "/index.xhtml";
    }

    public boolean isLoginExist() {
        for (User user : userDAO.getAllUsers()) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }


    @Transactional
    public void saveUser() {
        userDAO.addUser(user);
    }

    public Role getUserRole(String name) {
        return roleDAO.getRole(name);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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