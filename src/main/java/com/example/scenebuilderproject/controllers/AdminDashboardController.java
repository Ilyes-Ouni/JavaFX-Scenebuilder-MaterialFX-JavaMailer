package com.example.scenebuilderproject.controllers;

import com.example.scenebuilderproject.Configuration.Connexion;
import com.example.scenebuilderproject.Registration;
import com.example.scenebuilderproject.entities.Agent;
import com.example.scenebuilderproject.entities.Marathon;
import com.example.scenebuilderproject.entities.Runner;
import com.example.scenebuilderproject.entities.Sponsor;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;




public class AdminDashboardController implements Initializable {

    @FXML
    public Pane pnlLeaderBoard;

    @FXML
    public TextField PrizeMarathon;

    @FXML
    public TableView<Runner> tableViewRunnersLeadBoard;

    @FXML
    public MFXFilterComboBox<String> winnerChoiceBox;

    @FXML
    public MFXFilterComboBox<String> agentGenderChoiceBox;

    @FXML
    public MFXFilterComboBox<String> marathonChoiceBoxBoard;

    @FXML
    public MFXDatePicker agentBirthDatePicker;

    @FXML
    public MFXDatePicker startDatePickerMarathon;

    @FXML
    public MFXDatePicker endDatePickerMarathon;

    @FXML
    public MFXFilterComboBox<String> runnerCanceledChoiceBox;

    @FXML
    public MFXFilterComboBox<String> runnerConfirmedChoiceBox;

    @FXML
    public MFXFilterComboBox<String> runnerMarathonChoiceBox;

    @FXML
    public MFXFilterComboBox<String> runnerGenderChoiceBox;

    @FXML
    public MFXFilterComboBox<String> marathonChoiceBoxAgent;

    @FXML
    public MFXFilterComboBox<String> sponsorMarathonCheckBox;
    public TextField birthDateRunner;

    @FXML
    private TableColumn<Runner, String> ColPrizeMarathons;

    @FXML
    private TableColumn<Runner, String> ColFirstNameRunnerBoard;

    @FXML
    private TableColumn<Runner, Integer> ColRankRunnerBoard;

    @FXML
    private TableColumn<Runner, String> ColLastNameRunnerBoard;

    @FXML
    private TableColumn<Runner, String> ColEmailRunnerBoard;

    @FXML
    private TableColumn<Runner, ?> ColRecordRunnerBoard;

    @FXML
    private TableColumn<Runner, String> ColGenderRunnerBoard;

    @FXML
    public MFXFilterComboBox<String> marathonChoiceBox;

    @FXML
    private TableColumn<?, ?> ColBirthDateAgent;

    @FXML
    private Button btnLeaderBaord;

    @FXML
    public Label prizeOverviewLabel;

    @FXML
    private TableColumn<?, ?> ColCanceledRunner;

    @FXML
    private TableColumn<?, ?> ColConfirmedRunner;

    @FXML
    private TableColumn<?, ?> ColDistanceMarathons;

    @FXML
    private TableColumn<?, ?> ColEndDateMarathons;

    @FXML
    private TableColumn<Agent, String> ColFullNameAgent;

    @FXML
    private TableColumn<?, ?> ColFirstNameRunner;

    @FXML
    private TableColumn<?, ?> ColGenderAgent;

    @FXML
    private TableColumn<?, ?> ColGenderRunner;

    @FXML
    private TableColumn<Agent, Integer> ColPhoneNumberAgent;

    @FXML
    private TableColumn<Runner, String> ColLastNameRunner;

    @FXML
    private TableColumn<Runner, String> ColEmailRunner;

    @FXML
    private TableColumn<?, ?> ColLocationMarathons;

    @FXML
    private TableColumn<Agent, String> ColMarathonAgent;

    @FXML
    private TableColumn<Runner, String> ColMarathonRunner;

    @FXML
    private TableColumn<?, ?> ColNameMarathon;

    @FXML
    private TableColumn<Sponsor, String> ColNameSponsor;

    @FXML
    private TableColumn<?, ?> ColNameSponsor1211;

    @FXML
    private TableColumn<?, ?> ColPhoneRunner;

    @FXML
    private TableColumn<?, ?> ColRecordRunner;

    @FXML
    private TableColumn<Sponsor, BigDecimal> ColSponsorAmount;

    @FXML
    private TableColumn<Sponsor, String> ColSponsorMarathon;

    @FXML
    private TableColumn<?, ?> ColStartDateMarathons;

    @FXML
    private TableColumn<Runner, String> ColWinnerMarathons;

    @FXML
    private TextField agentBirthDate;

    @FXML
    private TextField idMarathon;

    @FXML
    private TextField agentFullName;

    @FXML
    private TextField agentEmail;

    @FXML
    private TextField agentPassword;

    @FXML
    private TextField agentPhoneNumber;

    @FXML
    private Button btnAgents;

    @FXML
    private Button btnMarathons;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnRunners;

    @FXML
    private  Button btnLeaderBoard;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btnSponsors;

    @FXML
    private Label nbrRunners;

    @FXML
    private Label nbrSponsors;

    @FXML
    private Label nbrAgents;


    @FXML
    private TextField distanceMarathon;

    @FXML
    public TextField sponsorID;

    @FXML
    private TextField endDateMarathon;

    @FXML
    private TextField firstNameRunner;

    @FXML
    private TextField lastNameRunner;

    @FXML
    private TextField runnerPassword;

    @FXML
    private TextField locationMarathon;

    @FXML
    private TextField nameMarathon;

    @FXML
    private TextField emailRunner;

    @FXML
    private VBox pnItems;

    @FXML
    private Pane pnlAgents;

    @FXML
    private Pane pnlMarathons;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlRunners;

    @FXML
    private Pane pnlSpnsors;

    @FXML
    private TextField genderRunner;

    @FXML
    private TextField selectedMarathonRunner;

    @FXML
    private TextField canceledRunner;

    @FXML
    private TextField confirmedRunner;

    @FXML
    private TextField selectedMarathon;

    @FXML
    private TextField recordRaceTimeRunner;

    @FXML
    private TextField sponsorAmount;

    @FXML
    private TextField sponsorName;

    @FXML
    private TextField sponsorMarathonID;

    @FXML
    private TextField startDateMarathon;

    @FXML
    private TableView<Runner> tableViewRunners;

    @FXML
    private TableView<Agent> tableViewAgents;

    @FXML
    private TableView<Marathon> tableViewMarathons;

    @FXML
    private TableView<Sponsor> tableViewSponsors;

    @FXML
    private Label startTimeLabel;

    @FXML
    private Label endTimeLabel;

    @FXML
    private Label marathonNameLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label winnerLabel;

    @FXML
    private Label distanceLabel;

    @FXML
    void Search(ActionEvent event) {
        Connexion connexion = null;
        try {
            connexion = new Connexion();
            connexion.connexion();
            if(pnlOverview.isVisible()) {
                String sql = "SELECT * FROM marathon WHERE name = ? ";
                PreparedStatement statement = connexion.prepareStatement(sql);

                statement.setString(1, marathonChoiceBox.getValue());
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    marathonNameLabel.setText(rs.getString("name"));
                    locationLabel.setText(rs.getString("location"));
                    startTimeLabel.setText(rs.getString("starting_date"));
                    endTimeLabel.setText(rs.getString("ending_date"));
                    distanceLabel.setText(rs.getString("distance"));
                    this.prizeOverviewLabel.setText(rs.getString("prize") + " DT");

                    String sqlWinner = "SELECT * FROM runner WHERE marathon_id = ? AND record_race_time IS NOT NULL AND record_race_time = (SELECT MAX(record_race_time) FROM runner WHERE marathon_id = ?)";
                    PreparedStatement statementWinner = connexion.prepareStatement(sqlWinner);
                    statementWinner.setInt(1, rs.getInt("id"));
                    statementWinner.setInt(2, rs.getInt("id"));
                    ResultSet rsWinner = statementWinner.executeQuery();

                    if (rsWinner.next()) {
                        winnerLabel.setText(rsWinner.getString("first_name") + " " + rsWinner.getString("last_name"));
                    } else {
                        winnerLabel.setText("No winner yet");
                    }


                    // Get the number of runners for the marathon from the database
                    String queryRunners = "SELECT COUNT(*) AS nbRunners FROM runner, marathon WHERE runner.marathon_id = marathon.id and marathon.name = ?";
                    PreparedStatement statementRunners = connexion.prepareStatement(queryRunners);
                    statementRunners.setString(1, marathonChoiceBox.getValue());

                    ResultSet rsRunners = statementRunners.executeQuery();
                    if (rsRunners.next()) {
                        this.nbrRunners.setText(rsRunners.getString("nbRunners"));
                    }

                    // Get the number of sponsors for the marathon from the database
                    String querySponsors = "SELECT COUNT(*) AS nbSponsors FROM sponsors, marathon WHERE sponsors.marathon_id = marathon.id and marathon.name = ?";
                    PreparedStatement statementSponsors = connexion.prepareStatement(querySponsors);
                    statementSponsors.setString(1, marathonChoiceBox.getValue());

                    ResultSet rsSponsors = statementSponsors.executeQuery();
                    if (rsSponsors.next()) {
                        this.nbrSponsors.setText(rsSponsors.getString("nbSponsors"));
                    }

                    // Get the number of agents for the marathon from the database
                    String queryAgents = "SELECT COUNT(*) AS nbAgents FROM administrators, marathon WHERE administrators.marathon_id = marathon.id and marathon.name = ? and role = 'AGENT'";
                    PreparedStatement statementAgents = connexion.prepareStatement(queryAgents);
                    statementAgents.setString(1, marathonChoiceBox.getValue());

                    ResultSet rsAgents = statementAgents.executeQuery();
                    if (rsAgents.next()) {
                        this.nbrAgents.setText(rsAgents.getString("nbAgents"));
                    }
                }
            } else{ // if the user is on the leaderboard page
                String sql = "SELECT r.* FROM runner as r, marathon as m WHERE r.marathon_id = m.id and m.name = ?";
                PreparedStatement statement = connexion.prepareStatement(sql);
                ObservableList<Runner> runners = FXCollections.observableArrayList();

                statement.setString(1, marathonChoiceBoxBoard.getValue());
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    String firstname =  rs.getString("first_name");
                    String lastname =  rs.getString("last_name");
                    String email = rs.getString("email");
                    String gender = rs.getString("gender");
                    LocalTime recordRaceTime = rs.getTime("record_race_time").toLocalTime();


                    Runner runner = new Runner(firstname, lastname, email, gender, recordRaceTime);
                    runners.add(runner);
                }
                // Sort the runners list based on the recordRaceTime property in descending order
                Comparator<Runner> recordRaceTimeComparator = Comparator.comparing(Runner::getRecordRaceTime).reversed();
                runners.sort(recordRaceTimeComparator);

                LocalTime previousRecordTime = null;
                int rank = 1;
                for (Runner runner : runners) {
                    LocalTime recordRaceTime = runner.getRecordRaceTime();

                    if (previousRecordTime != null && recordRaceTime.compareTo(previousRecordTime) != 0) {
                        rank++;
                    }

                    runner.setRank(rank);
                    previousRecordTime = recordRaceTime;
                }

                ColRankRunnerBoard.setCellValueFactory(new PropertyValueFactory<>("rank"));
                ColFirstNameRunnerBoard.setCellValueFactory(new PropertyValueFactory<>("firstName"));
                ColLastNameRunnerBoard.setCellValueFactory(new PropertyValueFactory<>("lastName"));
                ColEmailRunnerBoard.setCellValueFactory(new PropertyValueFactory<>("email"));
                ColRecordRunnerBoard.setCellValueFactory(new PropertyValueFactory<>("recordRaceTime"));
                ColGenderRunnerBoard.setCellValueFactory(new PropertyValueFactory<>("gender"));

                // Create a sorted list with the runners
                SortedList<Runner> sortedRunners = new SortedList<>(runners);

                // Bind the sorted list to the TableView

                tableViewRunnersLeadBoard.setItems(sortedRunners);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void uploadProgram(ActionEvent event) {
    /*    FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            System.out.println("File selected: " + selectedFile.getName());
            storeFile(selectedFile, selectedFileName);
        }
     */
    }

    private void storeFile(File file, String selectedFileName) {
        String projectPath = System.getProperty("user.dir");
        String uploadsPath = projectPath + File.separator + "uploads";
        File uploadsDir = new File(uploadsPath);

        // Create the "uploads" directory if it doesn't exist
        if (!uploadsDir.exists()) {
            boolean created = uploadsDir.mkdir();
            if (!created) {
                System.out.println("Failed to create the 'uploads' directory.");
                return;
            }
        }

        // Rename the file with the current date in binary format
        String originalFileName = file.getName();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String renamedFileName = Instant.now().getEpochSecond() + fileExtension;


        // Move the selected file to the "uploads" directory with the renamed filename
        File destination = new File(uploadsDir, renamedFileName);
        boolean moved = file.renameTo(destination);
        if (moved) {
            System.out.println("File stored successfully: " + destination.getAbsolutePath());
        } else {
            System.out.println("Failed to store the file.");
        }
    }


    @FXML
    public void Quit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter");
        alert.setHeaderText("Voulez-vous quitter l'application ?");
        ButtonType okButton = new ButtonType("Confirmer", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == okButton) {
            System.exit(0);
        }
    }

    @FXML
    void Signout(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // Open the home interface
        Registration registration = new Registration();
        registration.openRegistration();
    }


    public void handleClicks(ActionEvent actionEvent) throws SQLException {
        if (actionEvent.getSource() == btnOverview) {
            pnlMarathons.setVisible(false);
            pnlSpnsors.setVisible(false);
            pnlRunners.setVisible(false);
            pnlAgents.setVisible(false);
            pnlLeaderBoard.setVisible(false);

            Connexion connexion = new Connexion();
            connexion.connexion();

            String queryMarathon = "SELECT name FROM marathon";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            ResultSet rsMarathon = statementMarathon.executeQuery();
            while (rsMarathon.next()) {
                marathonChoiceBox.getItems().add(rsMarathon.getString("name"));
                marathonChoiceBoxBoard.getItems().add(rsMarathon.getString("name"));
            }

            pnlOverview.setVisible(true);
        } else if (actionEvent.getSource() == btnSponsors) {
            pnlOverview.setVisible(false);
            pnlMarathons.setVisible(false);
            pnlRunners.setVisible(false);
            pnlAgents.setVisible(false);
            pnlLeaderBoard.setVisible(false);

            Connexion connexion = new Connexion();
            connexion.connexion();

            String queryMarathon = "SELECT name FROM marathon";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            ResultSet rsMarathon = statementMarathon.executeQuery();

            while (rsMarathon.next()) {
                sponsorMarathonCheckBox.getItems().add(rsMarathon.getString("name"));
            }

            tableViewSponsors.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    try {
                        sponsorName.setText(String.valueOf(newSelection.getName()));
                        sponsorAmount.setText(String.valueOf(newSelection.getAmount()));
                        sponsorMarathonCheckBox.setValue(newSelection.getMarathonId().getName());
                        sponsorID.setText(String.valueOf(newSelection.getId()));
                        //sponsorMarathonID.setText(String.valueOf(newSelection.getMarathonId().getId()));
                    }catch (Exception ignored){}
                }
            });

            pnlSpnsors.setVisible(true);

        } else if (actionEvent.getSource() == btnRunners) {
            pnlOverview.setVisible(false);
            pnlSpnsors.setVisible(false);
            pnlMarathons.setVisible(false);
            pnlAgents.setVisible(false);
            pnlLeaderBoard.setVisible(false);

            Connexion connexion = new Connexion();
            connexion.connexion();

            String queryMarathon = "SELECT name FROM marathon";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            ResultSet rsMarathon = statementMarathon.executeQuery();

            while (rsMarathon.next()) {
                runnerMarathonChoiceBox.getItems().add(rsMarathon.getString("name"));
            }
            runnerGenderChoiceBox.getItems().addAll("Male", "Female");
            runnerCanceledChoiceBox.getItems().addAll("true", "false");
            runnerConfirmedChoiceBox.getItems().addAll("true", "false");

            tableViewRunners.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    firstNameRunner.setText(String.valueOf(newSelection.getFirstName()));
                    lastNameRunner.setText(String.valueOf(newSelection.getLastName()));
                    emailRunner.setText(String.valueOf(newSelection.getEmail()));
                    runnerGenderChoiceBox.setValue(newSelection.getGender());
                    runnerMarathonChoiceBox.setValue(newSelection.getMarathonId().getName());
                    runnerPassword.setText(String.valueOf(newSelection.getPassword()));

                    if(newSelection.isCanceled()) runnerCanceledChoiceBox.setValue("true");
                    else runnerCanceledChoiceBox.setValue("false");
                    if (newSelection.isConfirmed()) runnerConfirmedChoiceBox.setValue("true");
                    else runnerConfirmedChoiceBox.setValue("false");

                    recordRaceTimeRunner.setText(String.valueOf(newSelection.getRecordRaceTime()));
                }
            });

            pnlRunners.setVisible(true);
        } else if (actionEvent.getSource() == btnMarathons) {
            pnlOverview.setVisible(false);
            pnlSpnsors.setVisible(false);
            pnlRunners.setVisible(false);
            pnlAgents.setVisible(false);
            pnlLeaderBoard.setVisible(false);

            tableViewMarathons.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    try {
                        idMarathon.setText(String.valueOf(newSelection.getId()));
                        nameMarathon.setText(String.valueOf(newSelection.getName()));
                        locationMarathon.setText(String.valueOf(newSelection.getLocation()));
                        distanceMarathon.setText(String.valueOf(newSelection.getDistance()));
                        endDatePickerMarathon.setValue(newSelection.getEndingDate());
                        startDatePickerMarathon.setValue(newSelection.getStartingDate());
                        PrizeMarathon.setText(String.valueOf(newSelection.getPrize()));

                        startDateMarathon.setText(String.valueOf(newSelection.getStartingDate()));
                        endDateMarathon.setText(String.valueOf(newSelection.getEndingDate()));
                    }catch (Exception ignored){}
                }
            });

            pnlMarathons.setVisible(true);
        } else if (actionEvent.getSource() == btnAgents) {
            pnlOverview.setVisible(false);
            pnlSpnsors.setVisible(false);
            pnlRunners.setVisible(false);
            pnlMarathons.setVisible(false);
            pnlLeaderBoard.setVisible(false);

            Connexion connexion = new Connexion();
            connexion.connexion();

            String queryMarathon = "SELECT name FROM marathon";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            ResultSet rsMarathon = statementMarathon.executeQuery();

            while (rsMarathon.next()) {
                marathonChoiceBoxAgent.getItems().add(rsMarathon.getString("name"));
            }
            agentGenderChoiceBox.getItems().addAll("Male", "Female");

            tableViewAgents.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    try {
                        agentGenderChoiceBox.setValue(newSelection.getGender());
                        agentFullName.setText(String.valueOf(newSelection.getFullName()));
                        agentEmail.setText(String.valueOf(newSelection.getEmail()));
                        agentBirthDatePicker.setValue(newSelection.getBirthDate());
                        marathonChoiceBoxAgent.setValue(newSelection.getMarathonId().getName());
                        agentPassword.setText(String.valueOf(newSelection.getPassword()));
                        agentPhoneNumber.setText(String.valueOf(newSelection.getPhoneNumber()));
                        selectedMarathon.setText(String.valueOf(newSelection.getMarathonId().getId()));
                        agentBirthDate.setText(String.valueOf(newSelection.getBirthDate()));
                    }catch (Exception ignored){}
                }
            });

            pnlAgents.setVisible(true);
        } else if (actionEvent.getSource() == btnLeaderBoard) {
            pnlOverview.setVisible(false);
            pnlSpnsors.setVisible(false);
            pnlRunners.setVisible(false);
            pnlAgents.setVisible(false);
            pnlMarathons.setVisible(false);

            pnlLeaderBoard.setVisible(true);
        }
    }


    @FXML
    void consultAgents(ActionEvent event) {
        try {
            Connexion con = new Connexion();
            con.connexion();
            ObservableList<Agent> agents = FXCollections.observableArrayList();

            PreparedStatement ps = con.prepareStatement("SELECT admins.*, marathon.name AS marathonName FROM administrators as admins JOIN marathon ON admins.marathon_id = marathon.id WHERE role = ?");
            ps.setString(1, "AGENT");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("full_name");
                LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String gender = rs.getString("gender");

                Marathon marathon = new Marathon(rs.getInt("marathon_id"), rs.getString("marathonName"));
                Agent agent = new Agent(id, fullName, birthDate, phoneNumber, gender, email, password, role, marathon);
                agents.add(agent);
            }

            ColFullNameAgent.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            ColPhoneNumberAgent.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            ColGenderAgent.setCellValueFactory(new PropertyValueFactory<>("gender"));
            ColBirthDateAgent.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
            ColMarathonAgent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarathonId().getName()));

            tableViewAgents.setItems(agents);

            // close the database connection
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void consultMarathons(ActionEvent event) {
        try {
            // get a connection to the database
            Connexion connexion = new Connexion();
            connexion.connexion();

            // execute a query to retrieve all marathons
            String query = "SELECT * FROM marathon";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // populate the table view with the retrieved data
            ObservableList<Marathon> marathons = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Marathon marathon = new Marathon();
                marathon.setId(resultSet.getInt("id"));
                marathon.setName(resultSet.getString("name"));
                marathon.setLocation(resultSet.getString("location"));
                marathon.setStartingDate(resultSet.getDate("starting_date").toLocalDate());
                marathon.setEndingDate(resultSet.getDate("ending_date").toLocalDate());
                marathon.setDistance(resultSet.getFloat("distance"));
                marathon.setPrize(resultSet.getDouble("prize"));

                String sql = "SELECT * FROM runner where marathon_id = ? and record_race_time = (select max(record_race_time) from runner where marathon_id = ?)";
                PreparedStatement ps = connexion.prepareStatement(sql);
                ps.setInt(1, marathon.getId());
                ps.setInt(2, marathon.getId());
                ResultSet result = ps.executeQuery();
                marathons.add(marathon);
            }

            ColNameMarathon.setCellValueFactory(new PropertyValueFactory<>("name"));
            ColLocationMarathons.setCellValueFactory(new PropertyValueFactory<>("location"));
            ColDistanceMarathons.setCellValueFactory(new PropertyValueFactory<>("distance"));
            ColEndDateMarathons.setCellValueFactory(new PropertyValueFactory<>("endingDate"));
            ColStartDateMarathons.setCellValueFactory(new PropertyValueFactory<>("startingDate"));
            ColPrizeMarathons.setCellValueFactory(new PropertyValueFactory<>("prize"));
            tableViewMarathons.setItems(marathons);

            // close the database connection
            connexion.close();
        } catch (SQLException e) {
            // show an error message if there was an error retrieving the data from the database
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error retrieving marathons");
            alert.showAndWait();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void consultRunners(ActionEvent event) {
        try {
            Connexion connexion = new Connexion();
            connexion.connexion();

            String sql = "SELECT runner.*, marathon.name AS marathonName FROM runner JOIN marathon ON runner.marathon_id = marathon.id";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ResultSet result = ps.executeQuery();

            ObservableList<Runner> runners = FXCollections.observableArrayList();

            while (result.next()) {
                int id = result.getInt("id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String password = result.getString("password");
                String email = result.getString("email");
                String gender = result.getString("gender");
                LocalTime recordRaceTime = result.getTime("record_race_time").toLocalTime();
                boolean isCanceled = result.getBoolean("is_canceled");
                boolean isConfirmed = result.getBoolean("is_confirmed");
                Marathon marathon = new Marathon(result.getInt("marathon_id"), result.getString("marathonName"));

                Runner runner = new Runner(id, firstName, lastName, email, password, gender, recordRaceTime, isCanceled, isConfirmed, marathon);
                runners.add(runner);
            }


            ColFirstNameRunner.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            ColLastNameRunner.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            ColEmailRunner.setCellValueFactory(new PropertyValueFactory<>("email"));
            ColGenderRunner.setCellValueFactory(new PropertyValueFactory<>("gender"));
            ColRecordRunner.setCellValueFactory(new PropertyValueFactory<>("recordRaceTime"));
            ColCanceledRunner.setCellValueFactory(new PropertyValueFactory<>("canceled"));
            ColConfirmedRunner.setCellValueFactory(new PropertyValueFactory<>("confirmed"));
            ColMarathonRunner.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Runner, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Runner, String> p) {
                    return new SimpleStringProperty(p.getValue().getMarathonId().getName());
                }
            });

            tableViewRunners.setItems(runners);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while consulting the runners");
            alert.showAndWait();
            e.printStackTrace();
        }
    }


    @FXML
    void consultSponsors(ActionEvent event) {
        try {
            Connexion connexion = new Connexion();
            connexion.connexion();

                String sql = "SELECT sponsors.*, marathon.name AS marathonName FROM sponsors JOIN marathon ON sponsors.marathon_id = marathon.id";
                PreparedStatement ps = connexion.prepareStatement(sql);
                ResultSet result = ps.executeQuery();

                ObservableList<Sponsor> sponsors = FXCollections.observableArrayList();

                while (result.next()) {
                    Marathon marathon = new Marathon(result.getInt("marathon_id"), result.getString("marathonName"));
                    Sponsor sponsor = new Sponsor(result.getInt("id"), result.getString("name"), result.getBigDecimal("amount"), marathon);
                    sponsors.add(sponsor);
                }

                ColNameSponsor.setCellValueFactory(new PropertyValueFactory<>("name"));
                ColSponsorAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
                ColSponsorMarathon.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sponsor, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Sponsor, String> p) {
                        return new SimpleStringProperty(p.getValue().getMarathonId().getName());
                    }
                });


                tableViewSponsors.setItems(sponsors);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la consultation des sponsors");
            alert.showAndWait();
            e.printStackTrace();
        }
    }


    @FXML
    void deleteAgent(ActionEvent event) {
        Agent selectedAgent = tableViewAgents.getSelectionModel().getSelectedItem();

        if (selectedAgent == null) {
            // show an error message if no marathon is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a marathon to delete.");
            alert.showAndWait();
            return;
        }

        try {
            // delete the selected marathon from the database
            Connexion conn = new Connexion();
            conn.connexion();

            PreparedStatement ps = conn.prepareStatement("DELETE FROM administrators WHERE id = ? and role = 'AGENT'");
            ps.setInt(1, selectedAgent.getId());
            ps.executeUpdate();
            conn.close();

            // remove the selected marathon from the table
            tableViewAgents.getItems().remove(selectedAgent);

            // show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Agent deleted successfully.");
            alert.showAndWait();
        } catch (SQLException e) {
            // show an error message if there was an error deleting the Agent from the database
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error deleting Agent");
            alert.showAndWait();
        }
    }

    @FXML
    void deleteMarathon(ActionEvent event) {
        // get the selected marathon
        Marathon selectedMarathon = tableViewMarathons.getSelectionModel().getSelectedItem();

        if (selectedMarathon == null) {
            // show an error message if no marathon is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a marathon to delete.");
            alert.showAndWait();
            return;
        }

        try {
            // delete the selected marathon from the database
            Connexion conn = new Connexion();
            conn.connexion();

            PreparedStatement ps = conn.prepareStatement("DELETE FROM marathon WHERE id = ?");
            ps.setInt(1, selectedMarathon.getId());
            ps.executeUpdate();
            conn.close();

            // remove the selected marathon from the table
            tableViewMarathons.getItems().remove(selectedMarathon);

            // show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Marathon deleted successfully.");
            alert.showAndWait();
        } catch (SQLException e) {
            // show an error message if there was an error deleting the marathon from the database
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error deleting marathon");
            alert.showAndWait();
        }
    }


    @FXML
    void deleteRunner(ActionEvent event) {
        Runner selectedRunner = tableViewRunners.getSelectionModel().getSelectedItem();

        if (selectedRunner == null) {
            // show an error message if no marathon is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a Runner to delete.");
            alert.showAndWait();
            return;
        }
        try {
            Connexion connexion = new Connexion();
            connexion.connexion();

            PreparedStatement ps = connexion.prepareStatement(
                    "DELETE FROM runner WHERE id = ?"
            );
            ps.setInt(1, selectedRunner.getId());
            ps.executeUpdate();

            // remove the selected marathon from the table
            tableViewRunners.getItems().remove(selectedRunner);

            // show success message to user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Confirmation Message");
            alert.setContentText("Runner deleted successfully!");
            this.consultSponsors(event);
            alert.showAndWait();

            connexion.close();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Inputs");
            alert.setContentText("Please Check your information.");
            alert.showAndWait();
        }
    }

    @FXML
    void deleteSponsor(ActionEvent event) {
        Sponsor selectedMarathon = tableViewSponsors.getSelectionModel().getSelectedItem();

        if (selectedMarathon == null) {
            // show an error message if no marathon is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a sponosr to delete.");
            alert.showAndWait();
            return;
        }
        try {
            Connexion connexion = new Connexion();
            connexion.connexion();

            PreparedStatement ps = connexion.prepareStatement(
                    "DELETE FROM sponsors WHERE id = ?"
            );
            ps.setInt(1, selectedMarathon.getId());
            ps.executeUpdate();

            // remove the selected marathon from the table
            tableViewSponsors.getItems().remove(selectedMarathon);

            // show success message to user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Confirmation Message");
            alert.setContentText("Sponsor deleted successfully!");
            this.consultSponsors(event);
            alert.showAndWait();

            connexion.close();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Inputs");
            alert.setContentText("Please Check your information.");
            alert.showAndWait();
        }
    }


    @FXML
    void updateAgent(ActionEvent event) {
        Agent selectedAgent = (Agent) tableViewAgents.getSelectionModel().getSelectedItem();
        if (selectedAgent != null) {
            try {
                // Get input values
                String fullName = agentFullName.getText();
                String phoneNumber = agentPhoneNumber.getText();
                String email = agentEmail.getText();
                String password = agentPassword.getText();

                // Prepare SQL statement
                Connexion connexion = new Connexion();
                connexion.connexion();
                String queryMarathon = "SELECT id FROM marathon where name = ?";
                PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
                statementMarathon.setString(1, marathonChoiceBoxAgent.getValue());
                ResultSet rsMarathon = statementMarathon.executeQuery();

                if (!rsMarathon.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Inputs");
                    alert.setContentText("Please Check your information.");
                    alert.showAndWait();
                }else {
                    String queryAgent = "SELECT id FROM administrators where email = ?";
                    PreparedStatement statementAgent = connexion.prepareStatement(queryAgent);
                    statementMarathon.setString(1, agentFullName.getText());
                    ResultSet rsAgent = statementAgent.executeQuery();

                    if (rsAgent.next()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid Inputs");
                        alert.setContentText("Email already used.");
                        alert.showAndWait();
                    } else {
                        String query = "UPDATE administrators SET full_name=?, birth_date=?, phone_number=?, email=?, password=?, gender= ?, marathon_id= ? WHERE id=?";
                        PreparedStatement statement = connexion.prepareStatement(query);
                        statement.setString(1, fullName);
                        statement.setDate(2, Date.valueOf(agentBirthDatePicker.getValue()));
                        statement.setString(3, phoneNumber);
                        statement.setString(4, email);
                        statement.setString(5, password);
                        statement.setString(6, agentGenderChoiceBox.getValue());
                        statement.setString(7, rsMarathon.getString("id"));
                        statement.setInt(8, selectedAgent.getId());

                        // Execute statement and update table view
                        statement.executeUpdate();
                        // show a success message
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Marathon updated successfully.");
                        alert.showAndWait();
                    }

                    connexion.close();
                    this.consultAgents(event);
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid inputs.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void updateMarathon(ActionEvent event) {
        try {
            // get the values entered in the form fields
            int id = Integer.parseInt(idMarathon.getText());
            String name = nameMarathon.getText();
            String distance = distanceMarathon.getText();
            String location = locationMarathon.getText();

            // update the marathon in the database
            Connexion connexion = new Connexion();
            connexion.connexion();

            String queryMarathon = "SELECT id FROM marathon where name = ?";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            statementMarathon.setString(1, name);
            ResultSet rsMarathon = statementMarathon.executeQuery();

            if (rsMarathon.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Inputs");
                alert.setContentText("Marathon with the same name already exists.");
                alert.showAndWait();
            } else {
                PreparedStatement ps = connexion.prepareStatement("UPDATE marathon SET name = ?, distance = ?, location = ?, starting_date = ?, ending_date = ?, prize = ? WHERE id = ?");
                ps.setString(1, name);
                ps.setString(2, distance);
                ps.setString(3, location);
                ps.setDate(4, Date.valueOf(startDatePickerMarathon.getValue()));
                ps.setDate(5, Date.valueOf(endDatePickerMarathon.getValue()));
                ps.setDouble(6, Double.parseDouble(PrizeMarathon.getText()));
                ps.setInt(7, id);
                ps.executeUpdate();
                connexion.close();

                // show a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Marathon updated successfully.");
                this.consultMarathons(event);
                alert.showAndWait();
            }
        } catch (NumberFormatException | SQLException e) {
            // show an error message if the maxParticipants field is not a valid integer
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid inputs.");
            alert.showAndWait();
        } catch (Exception e) {
            // show an error message if there was an error updating the Marathon in the database
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error updating Marathon");
            alert.showAndWait();
        }
    }


    @FXML
    void updateRunner(ActionEvent event) {
        Runner selectedRunner = tableViewRunners.getSelectionModel().getSelectedItem();

        if (selectedRunner == null) {
            // No runner is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a runner to update.");
            alert.showAndWait();
            return;
        }

        try {
            // Open database connection
            Connexion connexion = new Connexion();
            connexion.connexion();

            String queryMarathon = "SELECT id FROM marathon where name = ?";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            statementMarathon.setString(1, runnerMarathonChoiceBox.getValue());
            ResultSet rsMarathon = statementMarathon.executeQuery();

            if (!rsMarathon.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Inputs");
                alert.setContentText("Please Check your information.");
                alert.showAndWait();
            }else {
                // Prepare the update statement
                String sql = "UPDATE runner SET first_name = ?, last_name = ?, email = ?, gender = ?, marathon_id = ?, record_race_time = ?, is_canceled = ?, is_confirmed = ?, password = ? WHERE id = ?";
                PreparedStatement ps = connexion.prepareStatement(sql);
                ps.setString(1, firstNameRunner.getText());
                ps.setString(2, lastNameRunner.getText());
                ps.setString(3, emailRunner.getText());
                ps.setInt(5, rsMarathon.getInt("id"));
                ps.setString(4, runnerGenderChoiceBox.getValue());
                ps.setString(9, runnerPassword.getText());
                ps.setString(6, recordRaceTimeRunner.getText());
                ps.setInt(10, selectedRunner.getId());

                if(runnerCanceledChoiceBox.getValue().equals("true")) ps.setInt(7, 1);
                else ps.setInt(7, 0);

                if (runnerConfirmedChoiceBox.getValue().equals("true")) ps.setInt(8, 1);
                else ps.setInt(8,  0);


                // Execute the update
                int rowsAffected = ps.executeUpdate();

                // Close database connection
                connexion.close();

                if (rowsAffected > 0) {
                    // Update successful
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Runner updated successfully.");
                    this.consultRunners(event);
                    alert.showAndWait();
                } else {
                    // No rows affected, update failed
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to update the runner.");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while updating the runner.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }


    @FXML
    void updateSponsor(ActionEvent event) {
        try {
            Connexion connexion = new Connexion();
            connexion.connexion();

            String queryMarathon = "SELECT id FROM marathon where name = ?";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            statementMarathon.setString(1, sponsorMarathonCheckBox.getValue());
            ResultSet rsMarathon = statementMarathon.executeQuery();

            if (rsMarathon.next()) {
                int selectedSponsorId = Integer.parseInt(sponsorID.getText());
                PreparedStatement ps = connexion.prepareStatement(
                        "UPDATE sponsors SET name = ?, amount = ?, marathon_id = ? WHERE id = ?"
                );
                ps.setString(1, sponsorName.getText());
                ps.setBigDecimal(2, new BigDecimal(sponsorAmount.getText()));
                ps.setInt(3, rsMarathon.getInt("id"));
                ps.setInt(4, Integer.parseInt(this.sponsorID.getText()));
                ps.executeUpdate();
                connexion.close();
                // show success message to user
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Confirmation Message");
                alert.setContentText("Sponsor updated successfully!");
                this.consultSponsors(event);
                alert.showAndWait();
            } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Inputs");
                    alert.setContentText("Please Check your information.");
                    alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Inputs");
            alert.setContentText("Please Check your Inputs.");
            alert.showAndWait();
        }
    }

    @FXML
    void insertMarathon(ActionEvent event) {
        if (nameMarathon.getText().isEmpty() || distanceMarathon.getText().isEmpty() || locationMarathon.getText().isEmpty() || startDatePickerMarathon.getValue() == null || endDatePickerMarathon.getValue() == null || PrizeMarathon.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields.");
            alert.showAndWait();
        } else {
            try {
                Connexion connexion = new Connexion();
                connexion.connexion();

                String queryMarathon = "SELECT id FROM marathon where name = ?";
                PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
                statementMarathon.setString(1, nameMarathon.getText());
                ResultSet rsMarathon = statementMarathon.executeQuery();

                if (rsMarathon.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid Inputs");
                    alert.setContentText("Marathon with the same name already exists.");
                    alert.showAndWait();
                } else {
                    // Retrieve the selected file using the FileChooser
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Choose a file");
                    fileChooser.getExtensionFilters().add(
                            new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
                    );
                    File selectedFile = fileChooser.showOpenDialog(null);

                    // Store the file in the "uploads" directory if a file was selected
                    if (selectedFile != null) {
                        // Rename the file with the current date in binary format
                        String originalFileName = selectedFile.getName();
                        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                        String selectedFileName = Instant.now().getEpochSecond() + fileExtension;

                        storeFile(selectedFile, selectedFileName);

                        // get the values entered in the form fields
                        String name = nameMarathon.getText();
                        float distance = Float.parseFloat(distanceMarathon.getText());
                        String location = locationMarathon.getText();

                        // insert the Marathon into the database
                        PreparedStatement stmt = connexion.prepareStatement(
                                "INSERT INTO marathon (name, location, starting_date, ending_date, distance, program, prize) VALUES (?, ?, ?, ?, ?, ?, ?)");
                        stmt.setString(1, name);
                        stmt.setString(2, location);
                        stmt.setDate(3, Date.valueOf(startDatePickerMarathon.getValue()));
                        stmt.setDate(4, Date.valueOf(endDatePickerMarathon.getValue()));
                        stmt.setFloat(5, distance);
                        stmt.setString(6, selectedFileName);
                        stmt.setDouble(7, Double.parseDouble(PrizeMarathon.getText()));
                        stmt.executeUpdate();

                        // show a success message
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Marathon inserted successfully.");
                        this.consultMarathons(event);
                        alert.showAndWait();
                    }
                }
                connexion.close();
            } catch (NumberFormatException e) {
                // show an error message if the maxParticipants field is not a valid integer
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid number for the maximum number of participants.");
                alert.showAndWait();
            } catch (Exception e) {
                // show an error message if there was an error inserting the Marathon into the database
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error inserting Marathon");
                alert.showAndWait();
            }
        }
    }


    @FXML
    void insertRunners(ActionEvent event){
        try {
            Connexion connexion = new Connexion();
            connexion.connexion();

            String queryMarathon = "SELECT id FROM marathon where name = ?";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            statementMarathon.setString(1, runnerMarathonChoiceBox.getValue());
            ResultSet rsMarathon = statementMarathon.executeQuery();

            if (!rsMarathon.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Inputs");
                alert.setContentText("Please Check your information.");
                alert.showAndWait();
            }else {
                String sql = "INSERT INTO runner (first_name, last_name, email, gender, marathon_id, record_race_time, is_canceled, is_confirmed, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connexion.prepareStatement(sql);

                // set parameters for the query
                preparedStatement.setString(1, firstNameRunner.getText());
                preparedStatement.setString(2, lastNameRunner.getText());
                preparedStatement.setString(3, emailRunner.getText());
                preparedStatement.setString(4, runnerGenderChoiceBox.getValue());
                preparedStatement.setString(5, rsMarathon.getString("id"));
                preparedStatement.setString(6, recordRaceTimeRunner.getText());
                preparedStatement.setString(9, runnerPassword.getText());

                if(runnerCanceledChoiceBox.getValue().equals("true")) preparedStatement.setInt(7, 1);
                else preparedStatement.setInt(7, 0);

                if (runnerConfirmedChoiceBox.getValue().equals("true")) preparedStatement.setInt(8, 1);
                else preparedStatement.setInt(8,  0);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Runner added");
                    alert.setContentText("Runner added successfully!");
                    this.consultRunners(event);
                    alert.showAndWait();
                }
            }

            connexion.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Inputs");
            alert.setContentText("Please Check your information.");
            alert.showAndWait();
        }
    }

    @FXML
    void insertSponsors(ActionEvent event) {
        try {
            Connexion connexion = new Connexion();
            connexion.connexion();

            String queryMarathon = "SELECT id FROM marathon where name = ?";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            statementMarathon.setString(1, sponsorMarathonCheckBox.getValue());
            ResultSet rsMarathon = statementMarathon.executeQuery();

            if (!rsMarathon.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Inputs");
                alert.setContentText("Please Check your information.");
                alert.showAndWait();
            }else {
                PreparedStatement ps = connexion.prepareStatement(
                        "INSERT INTO sponsors (name, amount, marathon_id) VALUES (?, ?, ?)"
                );
                ps.setString(1, sponsorName.getText());
                ps.setBigDecimal(2, new BigDecimal(sponsorAmount.getText()));
                ps.setInt(3, rsMarathon.getInt("id"));
                ps.executeUpdate();
                connexion.close();

                // show success message to user
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Confirmation Message");
                alert.setContentText("Sponsor added successfully!");
                this.consultSponsors(event);
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Inputs");
            alert.setContentText("Please Check your credentials.");
            alert.showAndWait();
        }
    }


    @FXML
    void insertAgent(ActionEvent event) {
        try {
            Connexion connexion = new Connexion();
            connexion.connexion();

            String queryMarathon = "SELECT id FROM marathon where name = ?";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            statementMarathon.setString(1, marathonChoiceBoxAgent.getValue());
            ResultSet rsMarathon = statementMarathon.executeQuery();

            if (!rsMarathon.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Inputs");
                alert.setContentText("Please Check your information.");
                alert.showAndWait();
            }else {
                String queryAgent = "SELECT id FROM administrators where email = ?";
                PreparedStatement statementAgent = connexion.prepareStatement(queryAgent);
                statementMarathon.setString(1, agentFullName.getText());
                ResultSet rsAgent = statementAgent.executeQuery();

                if (rsAgent.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid Inputs");
                    alert.setContentText("Email already used.");
                    alert.showAndWait();
                } else {
                    String query = "INSERT INTO administrators (full_name, birth_date, phone_number, email, password, role, gender, marathon_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connexion.prepareStatement(query);

                    if (agentPassword.getText().length() < 8) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Inputs");
                        alert.setContentText("Password Length should contain at least 8 characters.");
                        alert.showAndWait();
                    }

                    // set parameters for the query
                    preparedStatement.setString(1, agentFullName.getText());
                    preparedStatement.setDate(2, Date.valueOf(agentBirthDatePicker.getValue()));
                    preparedStatement.setString(3, agentPhoneNumber.getText());
                    preparedStatement.setString(4, agentEmail.getText());
                    preparedStatement.setString(5, agentPassword.getText());
                    preparedStatement.setString(6, "AGENT"); // set role to AGENT
                    preparedStatement.setString(7, agentGenderChoiceBox.getValue());
                    preparedStatement.setInt(8, rsMarathon.getInt("id"));

                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Agent added");
                        alert.setContentText("Agent added successfully!");
                        this.consultAgents(event);
                        alert.showAndWait();
                    }
                }
            }
                connexion.close();
                this.consultAgents(event);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Inputs");
            alert.setContentText("Please Check your information.");
            alert.showAndWait();
        }
    }

    @FXML
    void rejectRunner(ActionEvent event){
        try{
            String subject = "Marathon Registration Rejection";
            String content = "Your participation in the upcoming marathon was reject. \n" +
                    "We are looking forward to seeing you with us for the next time ! \n" +
                    "Best regards, \n" +
                    "Marathon Team";

            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp-relay.sendinblue.com");
            properties.put("mail.smtp.port", 587);
            properties.put("mail.smtp.auth", "true");

            // Create a session with the SMTP server
            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("ilyes.ouni700@gmail.com", "7NOq2pAc509Ej1JD");
                }
            });

            // Create a new MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender address
            message.setFrom(new InternetAddress("ilyes.ouni700@gmail.com"));

            // Set the recipient address
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("yassine.ouni007@gmail.com"));

            // Set the email subject and content
            message.setSubject(subject);
            message.setText(content);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

    @FXML
    void confirmRunner(ActionEvent event){
        try{
            String subject = "Marathon Registration Confirmation";
            String content = "We are thrilled to confirm your participation in the upcoming marathon. \n" +
                    "We are looking forward to seeing you on the day of the event. \n" +
                    "Best regards, \n" +
                    "Marathon Team";

            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp-relay.sendinblue.com");
            properties.put("mail.smtp.port", 587);
            properties.put("mail.smtp.auth", "true");

            // Create a session with the SMTP server
            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("ilyes.ouni700@gmail.com", "7NOq2pAc509Ej1JD");
                }
            });

            // Create a new MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender address
            message.setFrom(new InternetAddress("ilyes.ouni700@gmail.com"));

            // Set the recipient address
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("yassine.ouni007@gmail.com"));

            // Set the email subject and content
            message.setSubject(subject);
            message.setText(content);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connexion connexion = null;
        try {
            connexion = new Connexion();
            connexion.connexion();


            String sql = "UPDATE runner " +
                    "JOIN marathon ON runner.marathon_id = marathon.id " +
                    "SET runner.is_canceled = 1 " +
                    "WHERE runner.is_confirmed=0 AND DATEDIFF(marathon.starting_date, CURDATE()) <= 2;";
            PreparedStatement ps = connexion.prepareStatement(sql);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                String selectSql = "SELECT email FROM runner WHERE is_canceled = 1";
                PreparedStatement selectStmt = connexion.prepareStatement(selectSql);
                ResultSet resultSet = selectStmt.executeQuery();

                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    // Send email to the runner
                    sendCancellationEmail(email);
                }
            }

            String queryMarathon = "SELECT name FROM marathon";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            ResultSet rsMarathon = statementMarathon.executeQuery();
            while (rsMarathon.next()) {
                marathonChoiceBox.getItems().add(rsMarathon.getString("name"));
                marathonChoiceBoxBoard.getItems().add(rsMarathon.getString("name"));
            }
        }catch(SQLException e){
                throw new RuntimeException(e);
        } finally{
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

    void sendCancellationEmail(String email){
        try{
            String subject = "Marathon Registration Canceled";
            String content = "We unfortunatelly didn't accept your participation in the upcoming marathon due to your non. \n" +
                    "We are looking forward to seeing you with us for the next time ! \n" +
                    "Best regards, \n" +
                    "Marathon Team";

            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp-relay.sendinblue.com");
            properties.put("mail.smtp.port", 587);
            properties.put("mail.smtp.auth", "true");

            // Create a session with the SMTP server
            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("ilyes.ouni700@gmail.com", "7NOq2pAc509Ej1JD");
                }
            });

            // Create a new MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender address
            message.setFrom(new InternetAddress("ilyes.ouni700@gmail.com"));

            // Set the recipient address
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));

            // Set the email subject and content
            message.setSubject(subject);
            message.setText(content);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

    @FXML
    void exportProgram(ActionEvent event) {
        Connexion connexion = null;
        try {
            String marathonName = marathonChoiceBox.getValue();
            String selectedFileName = null;

            connexion = new Connexion();
            connexion.connexion();

            String queryMarathon = "SELECT program FROM marathon where name = '" + marathonName + "'";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            ResultSet rsMarathon = statementMarathon.executeQuery();

            while (rsMarathon.next()) {
                selectedFileName = rsMarathon.getString("program");
            }

            if (selectedFileName == null || selectedFileName.isEmpty()) {
                // No runner is selected
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Please select a Marathon.");
                alert.showAndWait();
            } else {
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
