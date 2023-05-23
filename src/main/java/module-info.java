module com.example.scenebuilderproject {
    exports com.example.scenebuilderproject.controllers;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires MaterialFX;
    requires java.sql;
    requires kotlin.stdlib;
    requires sib.api.v3.sdk;
    requires sendinblue;
    requires java.mail;

    opens com.example.scenebuilderproject.entities;
    opens com.example.scenebuilderproject.fonts to javafx.fxml;
    opens com.example.scenebuilderproject to javafx.fxml;

    opens com.example.scenebuilderproject.controllers to javafx.fxml;
    exports com.example.scenebuilderproject;
}