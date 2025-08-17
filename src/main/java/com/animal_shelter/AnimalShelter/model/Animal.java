package com.animal_shelter.animalshelter.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

// This class represents an Animal entity in the MongoDB database
// It is annotated with @Document to indicate that it is a MongoDB document
@Document(collection = "animals")
public class Animal {

    @Id
    private String id;

    @Field("age_upon_outcome")
    private String ageUponOutcome;

    @Field("animal_id")
    private String animalId;

    @Field("animal_type")
    private String animalType;

    private String breed;
    private String color;

    @Field("date_of_birth")
    private String dateOfBirth;
    @Field("datetime")
    private String datetime;
    private String monthyear;
    private String name;

    @Field("outcome_subtype")
    private String outcomeSubtype;

    @Field("outcome_type")
    private String outcomeType;

    private String sexUponOutcome;

    private String locationLat;

    private String locationLong;

    @Field("age_upon_outcome_in_weeks")
    private String ageUponOutcomeInWeeks;

    private String rescueType;

    // Default constructor
    // This is required for MongoDB to create instances of this class
    public Animal() {

    }

    // Getters and Setters for all fields
    // These methods allow access to the private fields of the class
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getMonthyear() {
        return monthyear;
    }

    public void setMonthyear(String monthyear) {
        this.monthyear = monthyear;
    }

    public String getAgeUponOutcomeInWeeks() {
        return ageUponOutcomeInWeeks;
    }

    public void setAgeUponOutcomeInWeeks(String ageUponOutcomeInWeeks) {
        this.ageUponOutcomeInWeeks = ageUponOutcomeInWeeks;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAgeUponOutcome() {
        return ageUponOutcome;
    }

    public void setAgeUponOutcome(String ageUponOutcome) {
        this.ageUponOutcome = ageUponOutcome;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutcomeSubtype() {
        return outcomeSubtype;
    }

    public void setOutcomeSubtype(String outcomeSubtype) {
        this.outcomeSubtype = outcomeSubtype;
    }

    public String getOutcomeType() {
        return outcomeType;
    }

    public void setOutcomeType(String outcomeType) {
        this.outcomeType = outcomeType;
    }

    public String getSexUponOutcome() {
        return sexUponOutcome;
    }

    public void setSexUponOutcome(String sexUponOutcome) {
        this.sexUponOutcome = sexUponOutcome;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(String locationLong) {
        this.locationLong = locationLong;
    }

    public String getRescueType() {
        return rescueType;
    }

    public void setRescueType(String rescueType) {
        this.rescueType = rescueType;
    }

}
