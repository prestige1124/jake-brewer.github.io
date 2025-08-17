package com.animal_shelter.animalshelter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.animal_shelter.animalshelter.service.AnimalService;

@SpringBootTest
class AnimalShelterApplicationTests {
    // This test class is used to verify that the application context loads correctly
    // and that the AnimalService can retrieve all animals from the database.
    @Autowired
    private AnimalService animalService;

    
   @Test
   void testGetAllAnimals() {
        // This test checks if the application context loads and retrieves all animals
        // from the database without any issues.
        var animals = animalService.getAllAnimals();
        assert animals != null : "Animal list should not be null";
        assert !animals.isEmpty() : "Animal list should not be empty";
    }

 



}
