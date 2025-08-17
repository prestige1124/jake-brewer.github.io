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
                .title("Animal Types")
                .width(800)
                .height(600)
                .build();

        // Count animals by type
        Map<String, Long> animalTypeCounts = animals.stream()
                .collect(Collectors.groupingBy(Animal::getAnimalType, Collectors.counting()));

        // Add data to the pie chart
        animalTypeCounts.forEach((type, count) -> pieChart.addSeries(type, count));

        return pieChart;
    }
}
