package com.animal_shelter.animalshelter.views;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;

import com.animal_shelter.animalshelter.model.Animal;
import com.animal_shelter.animalshelter.service.AnimalService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.model.Select;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

//  This class represents the home view of the application
//  It displays a welcome message, a filter for rescue types, and a grid of animals
//  It also includes a pie chart showing the distribution of animal types and a map for rescue locations
//  The view is annotated with @Route to define the URL path for this view
//  It uses Vaadin components for layout and interactivity
@JsModule("https://unpkg.com/leaflet@1.9.4/dist/leaflet.js")
@StyleSheet("https://unpkg.com/leaflet@1.9.4/dist/leaflet.css")
@Route("")
public class HomeView extends VerticalLayout {

    private Component chartComponent;
    private ComboBox<String> animalTypeComboBox;
    private ComboBox<String> AgeRangeComboBox;

    private final Grid<Animal> animalGrid = new Grid<>(Animal.class);

    public HomeView(AnimalService animalService) {

        add(new H1("Welcome to your new application"));
        add(new Paragraph("This is the home view"));

        add(new Paragraph("You can edit this view in src\\main\\java\\com\\animal_shelter\\AnimalShelter\\views\\HomeView.java"));

        setSpacing(true);
        setPadding(true);

        add(new H2("AAC Animal Dashboard"));
        add(new Paragraph("Filter animals by rescue type"));
        // RadioButtonGroup for selecting rescue type
        // This allows users to filter animals based on their rescue type
        // The options include various rescue types and a reset option to show all animals 
        // When a rescue type is selected, the grid updates to show animals matching that type
        // If "Reset" is selected, it shows all animals
        // The RadioButtonGroup is added to the layout for user interaction
        // The grid displays animal details such as ID, type, breed, color, name,
        RadioButtonGroup<String> rescueTypeGroup = new RadioButtonGroup<>();
        rescueTypeGroup.setLabel("Rescue Type");
        rescueTypeGroup.setItems("Water Rescue", "Mountain Rescue", "Disaster Rescue", "Reset");
        add(rescueTypeGroup);

        // ComboBox for selecting animal type
        // This allows users to filter animals based on their type
        // The options include various animal types such as Bird, Cat, Dog, Livestock,
        ComboBox<String> animalTypeComboBox = new ComboBox<>("Animal Type");
        animalTypeComboBox.setItems("Bird", "Cat", "Dog", "Livestock", "Other");
        animalTypeComboBox.setPlaceholder("Select an animal type");
        animalTypeComboBox.setClearButtonVisible(true);
        animalTypeComboBox.setWidth("300px");
        add(animalTypeComboBox);

        // ComboBox for selecting age range
        // This allows users to filter animals based on their age range
        // The options include various age ranges such as 0-12 weeks, 13-52 weeks, 1-5 years, and Senior
        // The ComboBox is configured with a placeholder, clear button, and width
        // When an age range is selected, the grid
        ComboBox<String> AgeRangeComboBox = new ComboBox<>("Age Range");
        AgeRangeComboBox.setItems("0-12 weeks", "13-52 weeks", "1-5 years", "Senior");
        AgeRangeComboBox.setPlaceholder("Select an age range");
        AgeRangeComboBox.setClearButtonVisible(true);
        AgeRangeComboBox.setWidth("300px");
        add(AgeRangeComboBox);

        // Grid for displaying animal data
        // This grid shows a list of animals with their details
        // It is configured to display specific columns such as ID, animal ID, type, breed, color, name, outcome type, outcome
        animalGrid.setColumns("animalId", "animalType", "breed", "color", "name", "outcomeType", "outcomeSubtype", "sexUponOutcome");
        animalGrid.setItems(animalService.getAllAnimals());
        animalGrid.setSizeFull();
        animalGrid.setHeight("400px");
        add(animalGrid);

        // Create a pie chart to visualize the distribution of animal types
        // This chart is built using the AnimalPieChartBuilder class
        // It uses the animal service to get all animals and build the chart
        chartComponent = createChartComponent(AnimalPieChartBuilder.buildPieChart(animalService.getAllAnimals()));
        add(new H3("Animal Types Pie Chart"));
        add(chartComponent);

        // Add listeners to the rescue type group and combo boxes
        // These listeners update the grid and chart based on user selections
        // When a rescue type is selected, it filters the animals displayed in the grid
        // If "Reset" is selected, it shows all animals
        // The animal type and age range combo boxes also trigger updates to the grid and chart
        rescueTypeGroup.addValueChangeListener(event -> {
            String selectedRescueType = event.getValue();
            if ("Reset".equals(selectedRescueType)) {
                updateByRescueType(animalService, null);
            } else {
                updateByRescueType(animalService, selectedRescueType);
            }
        });

        animalTypeComboBox.addValueChangeListener(event -> applyFilters(animalService));
        AgeRangeComboBox.addValueChangeListener(event -> applyFilters(animalService));

        // Add a map to visualize rescue locations
        // This map shows the locations of animal rescues
        // It uses Leaflet.js to display the map
        // The map is initialized with a default view and a tile layer from OpenStreetMap
        // The map is displayed below the pie chart
        // The map container is created as a Div component with specific dimensions
        // The map is initialized with a default view and a tile layer from OpenStreetMap
        Div mapContainer = new Div();
        mapContainer.setId("map");
        mapContainer.setWidth("600px");
        mapContainer.setHeight("400px");
        add(new H4("Rescue Locations Map"));
        add(mapContainer);

        UI.getCurrent().getPage().executeJs(
                "setTimeout(function() {"
                + "  var map = L.map('map').setView([37.7749, -122.4194], 13);"
                + // TODO: Replace with actual coordinates for your rescue locations, example coordinates for San Francisco
                "  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {"
                + "    maxZoom: 19,"
                + "    attribution: '© OpenStreetMap contributors'"
                + "  }).addTo(map);"
                + "}, 0);"
        );

    }
    // Component to hold the pie chart and grid of animals
    // This component is used to display the pie chart and animal grid in the view
    // This component is used to display the pie chart in the view  

    private Component createChartComponent(PieChart chart) {
        try {
            // Convert the pie chart to a BufferedImage
            // This is done using the BitmapEncoder from XChart
            // The BufferedImage is then converted to a byte array for display
            // The byte array is used to create a StreamResource for the image
            // The StreamResource is used to create an Image component
            // The Image component is then added to the layout for display
            BufferedImage bufferedImage = BitmapEncoder.getBufferedImage(chart);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            byte[] imageData = outputStream.toByteArray();
            // Create a StreamResource to hold the image data
            // This resource is used to display the image in the UI
            StreamResource resource = new StreamResource("chart.png", () -> new ByteArrayInputStream(imageData));
            Image image = new Image(resource, "Pie Chart");

            image.setWidth("600px");
            image.setHeight("400px");

            return image;

        } catch (IOException e) {
            return new Paragraph("Failed to load chart.");
        }

    }

    // Method to update the grid and chart based on the selected rescue type
    // This method retrieves animals based on the selected rescue type  
    private void updateByRescueType(AnimalService animalService, String rescueType) {
        List<Animal> animals;
        if (rescueType == null) {
            animals = animalService.getAllAnimals();
        } else {
            animals = animalService.getAnimalsByRescueType(rescueType);
        }
        updateGridAndChart(animals);
    }

    // Method to apply filters based on animal type and age range
    // This method filters the animals based on the selected animal type and age range
    // It retrieves all animals and applies the filters based on user selections
    // The filtered animals are then used to update the grid and chart
    // This allows users to see animals that match their criteria
    private void applyFilters(AnimalService animalService) {
        String selectedType = animalTypeComboBox.getValue();
        String selectedAgeRange = AgeRangeComboBox.getValue();

        List<Animal> filtered = animalService.getAllAnimals().stream()
                .filter(animal -> selectedType == null || selectedType.equalsIgnoreCase(animal.getAnimalType()))
                .filter(animal -> {
                    if (selectedAgeRange == null) {
                        return true;
                    }
                    Double ageWeeks = animal.getAgeUponOutcomeInWeeks();
                    if (ageWeeks == null) {
                        return true;
                    }
// Filter animals based on the selected age range
                    return switch (selectedAgeRange) {
                        case "0-12 weeks" ->
                            ageWeeks >= 0 && ageWeeks <= 12;
                        case "13-52 weeks" ->
                            ageWeeks >= 13 && ageWeeks <= 52;
                        case "1-3 years" ->
                            ageWeeks >= 53 && ageWeeks <= 156;
                        case "4+ years" ->
                            ageWeeks > 156;
                        default ->
                            true;
                    };
                })
                .collect(Collectors.toList());

        updateGridAndChart(filtered);
    }

    // Method to update the grid and chart with the filtered animals
    // This method sets the items in the animal grid to the filtered list of animals        
    private void updateGridAndChart(List<Animal> animals) {
        animalGrid.setItems(animals);

        PieChart chart = AnimalPieChartBuilder.buildPieChart(animals);
        Component newChartComponent = createChartComponent(chart);
        replace(chartComponent, newChartComponent);
        chartComponent = newChartComponent;
    }
}
