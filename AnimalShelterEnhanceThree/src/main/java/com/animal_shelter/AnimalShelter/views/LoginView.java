package com.animal_shelter.animalshelter.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("login")
public class LoginView extends VerticalLayout {

    // This view is used for user login
    // It extends VerticalLayout to arrange components vertically
    public LoginView() {
        System.out.println("LoginView initialized");
        LoginForm loginForm = new LoginForm();
        loginForm.setForgotPasswordButtonVisible(true);
        // Set the action for the login form to handle login requests
        add(new H1("Welcome to your new application"));
        add(new Paragraph("This is the home view"));
        loginForm.setAction("login");
        // Add the login form to the layout
        add(loginForm);
        setAlignItems(Alignment.CENTER);
        setSizeFull();

        // Add a listener to handle login events    
        loginForm.addLoginListener(event -> {
            System.out.println("Login attempt: " + event.getUsername());

        });

    }
}
