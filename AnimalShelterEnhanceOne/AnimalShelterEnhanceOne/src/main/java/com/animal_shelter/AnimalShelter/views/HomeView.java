package com.animal_shelter.animalshelter.views;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;

import com.animal_shelter.animalshelter.model.Animal;
import com.animal_shelter.animalshelter.service.AnimalService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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

    // Constructor for HomeView
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
        // Grid for displaying animal data
        // This grid shows a list of animals with their details
        // It is configured to display specific columns such as ID, animal ID, type, breed, color, name, outcome type, outcome
        Grid<Animal> animalGrid = new Grid<>(Animal.class);
        animalGrid.setColumns("id", "animalId", "animalType", "breed", "color", "name", "outcomeType", "outcomeSubtype", "sexUponOutcome");
        animalGrid.setItems(animalService.getAllAnimals());
        animalGrid.setSizeFull();
        animalGrid.setHeight("400px");
        add(animalGrid);
        // Add a listener to the rescueTypeGroup to filter animals based on the selected rescue type
        // When a rescue type is selected, the grid updates to show animals matching that type
        // If "Reset" is selected, it shows all animals
        // This allows users to easily filter and view animals based on their rescue type
        // The listener updates the grid items based on the selected rescue type
        rescueTypeGroup.addValueChangeListener(event -> {
            String selectedRescueType = event.getValue();
            if ("Reset".equals(selectedRescueType)) {
                animalGrid.setItems(animalService.getAllAnimals());
            } else {
                animalGrid.setItems(animalService.getAnimalsByRescueType(selectedRescueType));
            }
        });
        // Create a pie chart to visualize the distribution of animal types
        // This chart shows the number of animals for each type
        // It uses the AnimalPieChartBuilder class to create the chart
        // The chart is displayed below the grid
        PieChart chart = AnimalPieChartBuilder.buildPieChart(animalService.getAllAnimals());

        replace(chartComponent, chartComponent);
        chartComponent = createChartComponent(chart);

        add(new H3("Animal Types Pie Chart"));
        add(chartComponent);

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
    // Component to hold the pie chart
    // This component is used to display the pie chart in the view  
    private Component chartComponent;

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
}
