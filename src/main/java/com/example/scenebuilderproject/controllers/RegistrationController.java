package com.example.scenebuilderproject.controllers;

import com.example.scenebuilderproject.Configuration.Connexion;
import com.example.scenebuilderproject.DashboardAdmin;
import com.example.scenebuilderproject.DashboardAgent;
import com.example.scenebuilderproject.DashboardRunner;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.MFXStepper.MFXStepperEvent;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.MFXValidator;
import io.github.palexdev.materialfx.validation.Validated;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {
    private final MFXTextField registrationEmail;
    private final MFXTextField loginEmail;
    private final MFXPasswordField registrationPasswordField;
    private final MFXPasswordField loginPassword;
    private final MFXTextField firstName;
    private final MFXTextField lastName;
    private final MFXComboBox<String> genderCombo;
    private final MFXCheckbox checkbox;
    private MFXTextField genderLabel2;
    @FXML
    private MFXStepper RegistrationStepper, LoginStepper;
    @FXML
    private StackPane registrationPane, loginPane;
    @FXML
    private MFXButton registrationBtn, loginBtn;
    @FXML
    private Label quoteArea;


    public RegistrationController() {
        registrationEmail = new MFXTextField();
        loginEmail = new MFXTextField();
        registrationPasswordField = new MFXPasswordField();
        loginPassword = new MFXPasswordField();
        firstName = new MFXTextField();
        lastName = new MFXTextField();
        genderCombo = new MFXComboBox<>();
        checkbox = new MFXCheckbox("Confirm Data?");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registrationEmail.setPromptText("Email...");
        registrationEmail.getValidator().constraint("The email must be at least 6 characters long", registrationEmail.textProperty().length().greaterThanOrEqualTo(6));
        registrationEmail.setLeadingIcon(new MFXIconWrapper("mfx-user", 16, Color.web("#4D4D4D"), 24));

        registrationPasswordField.getValidator().constraint("The password must be at least 8 characters long", registrationPasswordField.textProperty().length().greaterThanOrEqualTo(8));
        registrationPasswordField.setPromptText("Password...");

        firstName.setPromptText("First Name...");
        firstName.getValidator().constraint("Please enter first name", firstName.textProperty().isNotEmpty());

        lastName.setPromptText("Last Name...");
        lastName.getValidator().constraint("Please enter last name", lastName.textProperty().isNotEmpty());

        genderCombo.setItems(FXCollections.observableArrayList("Male", "Female"));

        List<MFXStepperToggle> stepperToggles = createSteps();
        RegistrationStepper.getStepperToggles().addAll(stepperToggles);

        //login stepper
        loginEmail.setPromptText("Email...");
        loginEmail.getValidator().constraint("The email must be at least 6 characters long", loginEmail.textProperty().length().greaterThanOrEqualTo(6));
        loginEmail.setLeadingIcon(new MFXIconWrapper("mfx-user", 16, Color.web("#4D4D4D"), 24));

        loginPassword.getValidator().constraint("The password must be at least 8 characters long", loginPassword.textProperty().length().greaterThanOrEqualTo(8));
        loginPassword.setPromptText("Password...");

        List<MFXStepperToggle> loginStepperToggles = createLoginSteps();
        LoginStepper.getStepperToggles().addAll(loginStepperToggles);

        //initialize signPane, loginPane
        registrationBtn.setVisible(false);
        loginPane.setVisible(false);

        //quote thread
        new quoteGenerator().setQuotes();

        //Access Login detafils
        LoginStepper.setOnLastNext(event -> getLoginInputs());
    }

    private List<MFXStepperToggle> createSteps() {
        MFXStepperToggle step1 = new MFXStepperToggle("Step 1", new MFXFontIcon("mfx-lock", 16, Color.web("#f1c40f")));
        VBox step1Box = new VBox(20, wrapNodeForValidation(registrationEmail), wrapNodeForValidation(registrationPasswordField));
        step1Box.setAlignment(Pos.CENTER);
        step1.setContent(step1Box);
        step1.getValidator().dependsOn(registrationEmail.getValidator()).dependsOn(registrationPasswordField.getValidator());

        MFXStepperToggle step2 = new MFXStepperToggle("Step 2", new MFXFontIcon("mfx-user", 16, Color.web("#49a6d7")));
        VBox step2Box = new VBox(20, firstName, lastName, genderCombo);
        step2Box.setAlignment(Pos.CENTER);
        step2.setContent(step2Box);
        step2.getValidator().dependsOn(firstName.getValidator()).dependsOn(lastName.getValidator()).dependsOn(genderCombo.getValidator());

        MFXStepperToggle step3 = new MFXStepperToggle("Step 3", new MFXFontIcon("mfx-variant7-mark", 16, Color.web("#85CB33")));
        Node step3Grid = createGrid();
        step3.setContent(step3Grid);
        step3.getValidator().constraint("Data must be confirmed", checkbox.selectedProperty());

        return List.of(step1, step2, step3);
    }

    private List<MFXStepperToggle> createLoginSteps() {
        MFXStepperToggle loginStep1 = new MFXStepperToggle("Login Step1", new MFXFontIcon("mfx-lock", 16, Color.web("#f1c40f")));
        VBox loginStepBox1 = new VBox(20, wrapNodeForValidation(loginEmail), wrapNodeForValidation(loginPassword));
        loginStepBox1.setAlignment(Pos.CENTER);
        loginStep1.setContent(loginStepBox1);
        loginStep1.getValidator().dependsOn(loginEmail.getValidator()).dependsOn(loginPassword.getValidator());

        return List.of(loginStep1);
    }

    private <T extends Node & Validated> Node wrapNodeForValidation(T node) {
        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setManaged(false);
        RegistrationStepper.addEventHandler(MFXStepperEvent.VALIDATION_FAILED_EVENT, event -> {
            MFXValidator validator = node.getValidator();
            List<Constraint> validate = validator.validate();
            if (!validate.isEmpty()) {
                errorLabel.setText(validate.get(0).getMessage());
            }
        });
        RegistrationStepper.addEventHandler(MFXStepperEvent.NEXT_EVENT, event -> errorLabel.setText(""));
        VBox wrap = new VBox(9, node, errorLabel) {
            @Override
            protected void layoutChildren() {
                super.layoutChildren();

                double x = node.getBoundsInParent().getMinX();
                double y = node.getBoundsInParent().getMaxY() + getSpacing();
                double width = getWidth();
                double height = errorLabel.prefHeight(-1);
                errorLabel.resizeRelocate(x, y, width, height);
            }

            @Override
            protected double computePrefHeight(double width) {
                return super.computePrefHeight(width) + errorLabel.getHeight() + getSpacing();
            }
        };
        wrap.setAlignment(Pos.CENTER);
        return wrap;
    }

    private Node createGrid() {
        MFXTextField emailLabel1 = createLabel("Email: ");
        MFXTextField usernameLabel2 = createLabel("");
        usernameLabel2.textProperty().bind(registrationEmail.textProperty());

        MFXTextField firstNameLabel1 = createLabel("First Name: ");
        MFXTextField firstNameLabel2 = createLabel("");
        firstNameLabel2.textProperty().bind(firstName.textProperty());

        MFXTextField lastNameLabel1 = createLabel("Last Name: ");
        MFXTextField lastNameLabel2 = createLabel("");
        lastNameLabel2.textProperty().bind(lastName.textProperty());

        MFXTextField genderLabel1 = createLabel("Gender: ");
        genderLabel2 = createLabel("");
        genderLabel2.textProperty().bind(Bindings.createStringBinding(
                () -> genderCombo.getValue() != null ? genderCombo.getValue() : "Can't Say",
                genderCombo.valueProperty()
        ));

        emailLabel1.getStyleClass().add("header-label");
        firstNameLabel1.getStyleClass().add("header-label");
        lastNameLabel1.getStyleClass().add("header-label");
        genderLabel1.getStyleClass().add("header-label");

        MFXTextField completedLabel = MFXTextField.asLabel("Completed!");
        completedLabel.getStyleClass().add("completed-label");
        completedLabel.setAlignment(Pos.CENTER);
        Label completedRedirectLabel = new Label("Redirecting...");
        completedLabel.getStyleClass().add("completed-label");

        HBox b1 = new HBox(emailLabel1, usernameLabel2);
        HBox b2 = new HBox(firstNameLabel1, firstNameLabel2);
        HBox b3 = new HBox(lastNameLabel1, lastNameLabel2);
        HBox b4 = new HBox(genderLabel1, genderLabel2);

        b1.setMaxWidth(Region.USE_PREF_SIZE);
        b2.setMaxWidth(Region.USE_PREF_SIZE);
        b3.setMaxWidth(Region.USE_PREF_SIZE);
        b4.setMaxWidth(Region.USE_PREF_SIZE);

        VBox box = new VBox(10, b1, b2, b3, b4, checkbox);
        box.setAlignment(Pos.CENTER);
        StackPane.setAlignment(box, Pos.CENTER);

        RegistrationStepper.setOnLastNext(event -> {
            box.getChildren().setAll(completedLabel, completedRedirectLabel);
            RegistrationStepper.setMouseTransparent(true);
            getRegistrationInputs();
        });
        RegistrationStepper.setOnBeforePrevious(event -> {
            if (RegistrationStepper.isLastToggle()) {
                checkbox.setSelected(false);
                box.getChildren().setAll(b1, b2, b3, b4, checkbox);
            }
        });

        return box;
    }

    private MFXTextField createLabel(String text) {
        MFXTextField label = MFXTextField.asLabel(text);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setPrefWidth(200);
        label.setMinWidth(Region.USE_PREF_SIZE);
        label.setMaxWidth(Region.USE_PREF_SIZE);
        return label;
    }

    public void openRegistrationPane() {
        registrationPane.setVisible(true);
        registrationBtn.setVisible(false);
        loginPane.setVisible(false);
        loginBtn.setVisible(true);
    }

    public void openLoginPane() {
        loginPane.setVisible(true);
        loginBtn.setVisible(false);
        registrationPane.setVisible(false);
        registrationBtn.setVisible(true);
    }

    class quoteGenerator {
        private int i = 1;
        public void setQuotes() {
            String[] quotes = {"The greatest glory in living lies not in never falling, but in rising every time we fall. -Nelson Mandela",
                    "The way to get started is to quit talking and begin doing. -Walt Disney",
                    "Your time is limited, so don't waste it living someone else's life. Don't be trapped by dogma – which is living with the results of other people's thinking. -Steve Jobs",
                    "If life were predictable it would cease to be life, and be without flavor. -Eleanor Roosevelt",
                    "If you look at what you have in life, you'll always have more. If you look at what you don't have in life, you'll never have enough. -Oprah Winfrey",
                    "If you set your goals ridiculously high and it's a failure, you will fail above everyone else's success. -James Cameron",
                    "Life is what happens when you're busy making other plans. -John Lennon"
            };

            quoteArea.setText(quotes[1]);
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event ->{
                quoteArea.setText(quotes[i]);
                i ++;
                if (i <= 6) {
                    pause.play();
                } else {
                    quoteArea.setText(quotes[6]);
                }
            });
            pause.play();
        }

    }

    //Input Details
    public void getRegistrationInputs() {
        if(!registrationEmail.getText().isEmpty() || !firstName.getText().isEmpty() || !lastName.getText().isEmpty() || !genderCombo.getValue().isEmpty() || !checkbox.isSelected()) {
            Connexion connexion = null;
            try {
                connexion = new Connexion();
                connexion.connexion();

                // bring the last marathon id
                String queryMarathon = "SELECT MAX(id) as id FROM marathon";
                PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
                ResultSet rsMarathon = statementMarathon.executeQuery();

                int currentMarathonId = 0;
                while(rsMarathon.next()){
                    currentMarathonId = rsMarathon.getInt("id");
                }

                    String query = "INSERT INTO runner (first_name, last_name, email, gender, password, marathon_id, record_race_time, is_canceled, is_confirmed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connexion.prepareStatement(query);
                    statement.setString(1, firstName.getText());
                    statement.setString(2, lastName.getText());
                    statement.setString(3, registrationEmail.getText());
                    statement.setString(4, genderCombo.getValue());
                    statement.setString(5, registrationPasswordField.getText());
                    statement.setInt(6, currentMarathonId);
                    statement.setTime(7, new Time(0));
                    statement.setBoolean(8, false);
                    statement.setBoolean(9, false);
                    statement.executeUpdate();

                connexion.close();
    /*
                // Send registration confirmation email
                String apiKey = "YOUR_SENDINBLUE_API_KEY_HERE";
                SendinblueAPI client = new SendinblueAPI(apiKey);

                SendSmtpEmail email = new SendSmtpEmail();
                email.setSubject("Registration confirmation");
                email.setHtmlContent("Dear " + firstName.getText() + ",<br><br>Thank you for registering for the marathon!<br><br>Best regards,<br>The Marathon team");
                SendSmtpEmailSender sender = new SendSmtpEmailSender();
                sender.setEmail("YOUR_EMAIL_HERE");
                sender.setName("Marathon Team");
                email.setSender(sender);
                SendSmtpEmailTo to = new SendSmtpEmailTo();
                to.setEmail(registrationEmail.getText());
                email.setTo(Collections.singletonList(to));

                SendSmtpEmailResponse response = client.sendTransacEmail(email);
                System.out.println("Email sent with ID: " + response.getMessageId());
    */
            } catch (SQLException e) {
                System.out.println("An error occurred while registering the runner: " + e.getMessage());
            }
        }
    }


    public void getLoginInputs() {
        String email = loginEmail.getText();
        String password = loginPassword.getText();
        if(!email.isEmpty() && !password.isEmpty()) {
            try {
                // Connect to the database
                Connexion connexion = new Connexion();
                connexion.connexion();

                // Prepare the SQL statement
                PreparedStatement statement = connexion.prepareStatement("SELECT * FROM administrators WHERE email = ? AND password = ?");
                statement.setString(1, email);
                statement.setString(2, password);

                // Execute the query and get the result set
                ResultSet resultSet = statement.executeQuery();

                // Check if the user exists and has the role of admin
                if (resultSet.next()) {
                    String role = resultSet.getString("role");
                    if (role.equals("ADMIN")) {
                        // Close the login interface
                        Stage stage = (Stage) LoginStepper.getScene().getWindow();
                        stage.close();

                        // Open the dashboard interface
                        DashboardAdmin dashboardAdmin = new DashboardAdmin();
                        dashboardAdmin.openDashboard();
                    } else if (role.equals("AGENT")) {
                        // Close the login interface
                        Stage stage = (Stage) LoginStepper.getScene().getWindow();
                        stage.close();

                        // Open the dashboard interface
                        DashboardAgent dashboardAgent = new DashboardAgent(resultSet.getInt("marathon_id"));
                        dashboardAgent.openDashboard();
                    }
                } else {
                    PreparedStatement runnerStatement = connexion.prepareStatement("SELECT * FROM runner WHERE email = ? AND password = ? and is_canceled = 0");
                    runnerStatement.setString(1, email);
                    runnerStatement.setString(2, password);

                    // Execute the query and get the result set
                    ResultSet runnerResultSet = runnerStatement.executeQuery();

                    if (runnerResultSet.next()) {
                        // Close the login interface
                        Stage stage = (Stage) LoginStepper.getScene().getWindow();
                        stage.close();

                        // Open the dashboard interface and pass the user marathon inside the constructor
                        DashboardRunner dashboardRunner = new DashboardRunner(runnerResultSet.getInt("marathon_id"));
                        dashboardRunner.openDashboard();
                    }else {
                        // Show error message for invalid credentials
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Credentials");
                        alert.setHeaderText("The username or password is incorrect");
                        alert.setContentText("Please try again.");
                        alert.showAndWait();
                    }
                }

                // Close the database connection
                connexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database connection errors
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setHeaderText("An error occurred while connecting to the database");
                alert.setContentText("Please try again later.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void exportProgram(ActionEvent event) {
        Connexion connexion = null;
        try {
            connexion = new Connexion();
            connexion.connexion();
            String selectedFileName = null;

            String queryMarathon = "SELECT program FROM marathon where id = (SELECT MAX(id) FROM marathon)";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            ResultSet rsMarathon = statementMarathon.executeQuery();

            if (rsMarathon.next()) {
                selectedFileName = rsMarathon.getString("program");
                String projectPath = System.getProperty("user.dir");
                String filePath = projectPath + File.separator + "uploads" + File.separator + selectedFileName;

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File");
                fileChooser.setInitialFileName(selectedFileName);
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
                fileChooser.getExtensionFilters().add(extFilter);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                File selectedFile = fileChooser.showSaveDialog(stage);

                if (selectedFile != null) {
                    try (InputStream inputStream = new FileInputStream(filePath);
                         FileOutputStream outputStream = new FileOutputStream(selectedFile)) {

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }

                        System.out.println("File downloaded successfully: " + selectedFile.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Failed to download the file.");
                    }
                } else {
                    System.out.println("No file selected.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // Close the database connection
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}