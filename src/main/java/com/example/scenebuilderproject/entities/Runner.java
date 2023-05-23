package com.example.scenebuilderproject.entities;

import javafx.beans.property.*;

import java.time.LocalTime;

public class Runner {
    private IntegerProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty password;
    private StringProperty email;
    private StringProperty gender;
    private SimpleObjectProperty<Marathon> marathonId;
    private ObjectProperty<LocalTime> recordRaceTime;
    private BooleanProperty isCanceled;
    private BooleanProperty isConfirmed;
    private IntegerProperty rank;

    public Runner(int id,String firstName, String lastName, String email, String password, String gender, LocalTime recordRaceTime, boolean isCanceled, boolean isConfirmed, Marathon marathon) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.gender = new SimpleStringProperty(gender);
        this.marathonId = new SimpleObjectProperty<Marathon>(marathon);
        this.recordRaceTime = new SimpleObjectProperty<>(recordRaceTime);
        this.isCanceled = new SimpleBooleanProperty(isCanceled);
        this.isConfirmed = new SimpleBooleanProperty(isConfirmed);
        this.rank = new SimpleIntegerProperty();
    }

    public Runner(String firstName, String lastName, String email, String gender, LocalTime recordRaceTime) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.gender = new SimpleStringProperty(gender);
        this.recordRaceTime = new SimpleObjectProperty<>(recordRaceTime);
        this.rank = new SimpleIntegerProperty();
    }

    public Runner(Integer id, String firstName, String lastName) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.rank = new SimpleIntegerProperty();
    }


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }


    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }



    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public Marathon getMarathonId() {
        return marathonId.get();
    }

    public SimpleObjectProperty<Marathon> marathonIdProperty() {
        return marathonId;
    }

    public void setMarathonId(Marathon marathonId) {
        this.marathonId.set(marathonId);
    }

    public LocalTime getRecordRaceTime() {
        return recordRaceTime.get();
    }

    public ObjectProperty<LocalTime> recordRaceTimeProperty() {
        return recordRaceTime;
    }

    public void setRecordRaceTime(LocalTime recordRaceTime) {
        this.recordRaceTime.set(recordRaceTime);
    }

    public boolean isCanceled() {
        return isCanceled.get();
    }

    public BooleanProperty isCanceledProperty() {
        return isCanceled;
    }

    public void setIsCanceled(boolean isCanceled) {
        this.isCanceled.set(isCanceled);
    }

    public boolean isConfirmed() {
        return isConfirmed.get();
    }

    public BooleanProperty isConfirmedProperty() {
        return isConfirmed;
    }

    public void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed.set(isConfirmed);
    }

    public int getRank() {
        return rank.get();
    }

    public IntegerProperty rankProperty() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank.set(rank);
    }
}
