package com.animal_shelter.animalshelter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.animal_shelter.animalshelter.model.Animal;
import com.animal_shelter.animalshelter.repository.AnimalRepository;

// This service class provides methods to interact with the AnimalRepository
// It contains business logic for managing animal records in the database
@Service
public class AnimalService {
    // Autowired annotation is used to inject the AnimalRepository dependency
    // This allows the service to perform CRUD operations on Animal entities
    @Autowired
    private AnimalRepository animalRepository;

    public AnimalService() {
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }
    // This method retrieves animals based on their type
    // It returns a list of animals that match the specified animal type
    public List<Animal> getAnimalsByRescueType(String rescueType) {
        switch (rescueType) {
            case "Water Rescue":
                return animalRepository.findAnimalsByCriteria(
                        List.of("Labrador Retriever Mix", "Chesapeake Bay Retriever", "Newfoundland"),
                        "Intact Female",
                        26.0,
                        156.0
                );

            default:
                return animalRepository.findAll();
        }
    }

    // TODO: Create CRUD
    

    // This method is used to create a new animal record in the database
    // It returns true if the animal was successfully created, false otherwise
    public boolean createAnimal(Animal animal) {
        if (animal != null) {
            animalRepository.save(animal);
            return true;
        }
        if (animal == null) {
            throw new IllegalArgumentException("Animal data must not be null.");
        }
        return false;
    }

    // TODO: Update CRUD
    // TODo: Delete CRUD

    
}
