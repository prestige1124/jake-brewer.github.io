package com.animal_shelter.animalshelter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal_shelter.animalshelter.model.Animal;
import com.animal_shelter.animalshelter.service.AnimalService;

//  This controller handles requests related to animals in the shelter
//  It provides endpoints to retrieve all animals, get animals by rescue type, and create a new animal
//  The endpoints are prefixed with /api/animals to follow RESTful conventions
@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    // This endpoint retrieves all animals from the shelter
    // It returns a list of Animal objects in JSON format
    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    // This endpoint retrieves animals by their rescue type
    // It takes a rescue type as a path variable and returns a list of Animal objects that  match the specified rescue type
    @GetMapping("/rescue/{rescueType}")
    public List<Animal> getAnimalsByRescueType(@PathVariable String rescueType) {
        return animalService.getAnimalsByRescueType(rescueType);
    }

    // This endpoint creates a new animal in the shelter
    // It takes an Animal object in the request body and returns the created Animal object
    @PostMapping
    public Animal createAnimal(@RequestBody Animal animal) {
        return animalService.createAnimal(animal);
    }

}
