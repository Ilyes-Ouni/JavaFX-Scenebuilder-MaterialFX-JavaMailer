package com.example.scenebuilderproject.entities;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Marathon {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty location = new SimpleStringProperty();

    private SimpleDoubleProperty prize = new SimpleDoubleProperty();
    private ObjectProperty<LocalDate> starting_date = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> ending_date = new SimpleObjectProperty<>();
    private FloatProperty distance = new SimpleFloatProperty();
    //private IntegerProperty winnerId = new SimpleIntegerProperty();
    private ObjectProperty<Runner> winnerId = new SimpleObjectProperty<>();

    public Marathon() {
        // Default constructor is required for entity classes
    }

    public Marathon(int id, String name, String location, LocalDate startingDate, LocalDate endingDate, float distance, Runner winnerId, Double prize) {
        this.id.set(id);
        this.name.set(name);
        this.location.set(location);
        this.starting_date.set(startingDate);
        this.ending_date.set(endingDate);
        this.distance.set(distance);
        this.winnerId.set(winnerId);
        this.prize = new SimpleDoubleProperty(prize);
    }

    public Marathon(int id, String name) {
        this.id.set(id);
        this.name.set(name);
    }

    // Getters and setters for id
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Getters and setters for name
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    // Getters and setters for location
    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public StringProperty locationProperty() {
        return location;
    }

    // Getters and setters for startingDate
    public LocalDate getStartingDate() {
        return starting_date.get();
    }

    public void setStartingDate(LocalDate startingDate) {
        this.starting_date.set(startingDate);
    }

    public ObjectProperty<LocalDate> startingDateProperty() {
        return starting_date;
    }

    // Getters and setters for endingDate
    public LocalDate getEndingDate() {
        return ending_date.get();
    }

    public void setEndingDate(LocalDate endingDate) {
        this.ending_date.set(endingDate);
    }

    public ObjectProperty<LocalDate> endingDateProperty() {
        return ending_date;
    }

    // Getters and setters for distance
    public float getDistance() {
        return distance.get();
    }

    public void setDistance(float distance) {
        this.distance.set(distance);
    }

    public FloatProperty distanceProperty() {
        return distance;
    }

    // Getters and setters for winnerId
    public Runner getWinnerId() {
        return winnerId.get();
    }

    public void setWinnerId(Runner winnerId) {
        this.winnerId.set(winnerId);
    }

    public ObjectProperty<Runner> winnerIdProperty() {
        return winnerId;
    }

    public double getPrize() {
        return prize.get();
    }

    public void setPrize(double prize) {
        this.prize.set(prize);
    }

    public DoubleProperty priweProperty() {
        return prize;
    }
}
