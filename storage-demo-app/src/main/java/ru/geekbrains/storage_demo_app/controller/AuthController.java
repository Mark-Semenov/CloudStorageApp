package ru.geekbrains.storage_demo_app.controller;

import ru.geekbrains.storage_demo_app.DAO.UserDAO;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@SessionScoped
public class AuthController implements Serializable {

    @EJB
    private UserDAO userDAO;

    @Inject
    private HttpSession context;




}
