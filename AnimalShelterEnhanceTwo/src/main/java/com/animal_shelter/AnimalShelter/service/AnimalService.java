package com.animal_shelter.animalshelter.service;

import java.util.List;
import java.util.stream.Collectors;

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
    // Enhancement Two: Algorithms and DB
// This method retrieves animals based on multiple filters      
    public List<Animal> getAnimalsByAllFilters(String rescueType, String animalType, String ageRangeLabel) {
        List<Animal> animals = animalRepository.findAll();

        // Filter animals based on rescue type, animal type, and age range
        // This method applies the specified filters to the list of animals 
        return animals.stream()
                .filter(animal -> {
                    if (rescueType == null || "Reset".equalsIgnoreCase(rescueType)) {
                        return true;
                    }
                    String animalRescueType = animal.getRescueType();
                    return animalRescueType != null && animalRescueType.equalsIgnoreCase(rescueType);
                })
                .filter(animal -> {
                    if (animalType == null) {
                        return true;
                    }
                    String animalTypeValue = animal.getAnimalType();
                    return animalTypeValue != null && animalTypeValue.equalsIgnoreCase(animalType);
                })
                .filter(animal -> {
                    if (ageRangeLabel == null || animal.getAgeUponOutcomeInWeeks() == null) {
                        return true;
                    }
                    // Convert ageUponOutcomeInWeeks to double for comparison
                    double ageWeeks = animal.getAgeUponOutcomeInWeeks();
                    switch (ageRangeLabel) {
                        case "0-12 weeks":
                            return ageWeeks <= 12;
                        case "13-52 weeks":
                            return ageWeeks >= 13 && ageWeeks <= 52;
                        case "1-5 years":
                            return ageWeeks >= 53 && ageWeeks <= 260;
                        case "Senior":
                            return ageWeeks > 260;
                        default:
                            return true;
                    }
                })
                .collect(Collectors.toList());

    }

}
