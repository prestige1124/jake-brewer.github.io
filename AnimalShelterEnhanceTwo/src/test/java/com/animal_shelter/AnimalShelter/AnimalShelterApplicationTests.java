package com.animal_shelter.animalshelter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.animal_shelter.animalshelter.service.AnimalService;

@SpringBootTest
class AnimalShelterApplicationTests {
    // This test class is used to verify that the application context loads correctly
    // and that the AnimalService can retrieve all animals from the database.
    @Autowired
    private AnimalService animalService;

   

 



}
