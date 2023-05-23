package com.example.scenebuilderproject.entities;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Agent {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty fullName = new SimpleStringProperty();
    private ObjectProperty<LocalDate> birthDate = new SimpleObjectProperty<>();
    private StringProperty phoneNumber = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private StringProperty gender = new SimpleStringProperty();
    private StringProperty role = new SimpleStringProperty();

    private SimpleObjectProperty<Marathon> marathonId;

    public Agent() {
        // Default constructor is required for entity classes
    }

    public Agent(int id, String fullName, LocalDate birthDate, String phoneNumber, String gender, String email, String password, String role, Marathon marathon) {
        this.id.set(id);
        this.fullName.set(fullName);
        this.birthDate.set(birthDate);
        this.phoneNumber.set(phoneNumber);
        this.email.set(email);
        this.password.set(password);
        this.gender.set(gender);
        this.role.set(role);
        this.marathonId = new SimpleObjectProperty<>(marathon);
    }

    public Agent(int id, String fullName) {
        this.id.set(id);
        this.fullName.set(fullName);
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

    // Getters and setters for fullName
    public String getFullName() {
        return fullName.get();
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    // Getters and setters for birthDate
    public LocalDate getBirthDate() {
        return birthDate.get();
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate.set(birthDate);
    }

    public ObjectProperty<LocalDate> birthDateProperty() {
        return birthDate;
    }

    // Getters and setters for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    // Getters and setters for email
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    // Getters and setters for password
    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    // Getters and setters for role
    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public StringProperty roleProperty() {
        return role;
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public SimpleObjectProperty<Marathon>  marathonIdProperty() {
        return marathonId;
    }

    public Marathon getMarathonId() {
        return marathonId.get();
    }

    public void setMarathonId(Marathon marathonId) {
        this.marathonId.set(marathonId);
    }
}
