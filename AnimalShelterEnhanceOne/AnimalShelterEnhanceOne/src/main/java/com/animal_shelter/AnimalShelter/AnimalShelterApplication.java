package com.animal_shelter.animalshelter;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;


@SpringBootApplication
@Theme("my-theme")
public class AnimalShelterApplication implements AppShellConfigurator {

// Add Spring Boot main method
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(AnimalShelterApplication.class, args);
        
    }

    
}
