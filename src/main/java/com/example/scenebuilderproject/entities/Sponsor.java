package com.example.scenebuilderproject.entities;

import javafx.beans.property.*;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

public class Sponsor {
    private IntegerProperty id;
    private StringProperty name;
    private ObjectProperty<BigDecimal> amount;
    private SimpleObjectProperty<Marathon> marathonId;

    public Sponsor(int id, String name, BigDecimal amount, Marathon marathon) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleObjectProperty<>(amount);
        this.marathonId = new SimpleObjectProperty<>(marathon);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObjectProperty<BigDecimal> amountProperty() {
        return amount;
    }

    public BigDecimal getAmount() {
        return amount.get();
    }

    public void setAmount(BigDecimal amount) {
        this.amount.set(amount);
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
