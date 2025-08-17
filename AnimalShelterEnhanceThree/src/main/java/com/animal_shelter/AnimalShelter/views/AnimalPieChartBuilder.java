package com.animal_shelter.animalshelter.views;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;

import com.animal_shelter.animalshelter.model.Animal;

// This class is responsible for building a pie chart that visualizes the distribution of animal types
// It uses the XChart library to create the pie chart
public class AnimalPieChartBuilder {

    // This method takes a list of animals and builds a pie chart showing the distribution of animal types
    // It groups the animals by their type and counts the occurrences of each type
    public static PieChart buildPieChart(List<Animal> animals) {
        PieChart pieChart = new PieChartBuilder()
                .title("Preferred Breed Types")
                .width(800)
                .height(600)
                .build();

        // Group animals by their type and count occurrences
        Map<String, Long> breedCounts = animals.stream()
                .filter(animal -> animal.getBreed() != null)
                .collect(Collectors.groupingBy(Animal::getBreed, Collectors.counting()));

        //  Calculate the total number of animals to compute percentages    
        long totalAnimals = breedCounts.values().stream().mapToLong(Long::longValue).sum();

        // Add each breed and its percentage to the pie chart
        breedCounts.forEach((breed, count) -> {
            double percentage = (double) count / totalAnimals * 100;
            pieChart.addSeries(breed, percentage);
        });

        // Log the number of breeds added to the pie chart
        // This is useful for debugging and ensuring the chart is built correctly
        // Mountain and Diaster will not load into pie chart
        System.out.println("Pie chart built with " + breedCounts.size() + " breeds.");

        return pieChart;
    }
}
