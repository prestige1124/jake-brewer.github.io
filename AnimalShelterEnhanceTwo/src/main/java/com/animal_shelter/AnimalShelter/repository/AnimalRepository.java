package com.animal_shelter.animalshelter.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.animal_shelter.animalshelter.model.Animal;

// This interface extends MongoRepository to provide CRUD operations for the Animal entity
// It allows for custom queries to be defined for specific search criteria
@Repository
public interface AnimalRepository extends MongoRepository<Animal, String> {

    List<Animal> findByAnimalType(String animalType);

    List<Animal> findByBreed(String breed);

    List<Animal> findByRescueType(String rescueType);

    List<Animal> findByColor(String color);

    List<Animal> findByOutcomeType(String outcomeType);

    List<Animal> findByOutcomeSubtype(String outcomeSubtype);

    List<Animal> findBySexUponOutcome(String sexUponOutcome);

    List<Animal> findByLocationLatAndLocationLong(String locationLat, String locationLong);

    List<Animal> findByAgeUponOutcome(String ageUponOutcome);

    List<Animal> findByDateOfBirth(String dateOfBirth);

    List<Animal> findByDatetime(String datetime);

    List<Animal> findByMonthyear(String monthyear);

    List<Animal> findByAnimalId(String animalId);

    List<Animal> findByName(String name);

    // E2 Algo D
    List<Animal> findByAgeUponOutcomeInWeeks(Double ageUponOutcomeInWeeks);

    List<Animal> findByAgeUponOutcomeInWeeksBetween(Double minAge, Double maxAge);

    List<Animal> findByAnimalTypeAndAgeUponOutcomeInWeeksBetween(String type, Double min, Double max);
List<Animal> findByAnimalTypeAndAgeUponOutcomeInWeeks(String type, String ageRange);

    // This method retrieves all animals from the database
    // It is used to get a complete list of animals without any filters
    @Override
    List<Animal> findAll();

    // This method retrieves animals based on specific criteria
    @Query("{ 'breed': { $in: ?0 }, 'sex_upon_outcome': ?1, 'age_upon_outcome_in_weeks': { $gte: ?2, $lte: ?3 } }")
    List<Animal> findAnimalsByCriteria(List<String> breeds, String sex, double minAge, double maxAge);

    
}
