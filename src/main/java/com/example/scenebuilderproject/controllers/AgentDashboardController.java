package com.example.scenebuilderproject.controllers;

import com.example.scenebuilderproject.Configuration.Connexion;
import com.example.scenebuilderproject.Registration;
import com.example.scenebuilderproject.entities.Marathon;
import com.example.scenebuilderproject.entities.Runner;
import com.example.scenebuilderproject.entities.Sponsor;
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

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class AgentDashboardController implements Initializable {

    private Integer marathonId;

    @FXML
    private TableColumn<?, ?> ColFirstNameRunner;

    @FXML
    private TableColumn<?, ?> ColGenderRunner;

    @FXML
    private TableColumn<?, ?> ColLastNameRunner;


    @FXML
    private TableColumn<?, ?> ColRecordRunner;

    @FXML
    private TableColumn<?, ?> ColRankRunnerBoard;

    @FXML
    private TableColumn<?, ?> ColFirstNameRunnerBoard;

    @FXML
    private TableColumn<?, ?> ColGenderRunnerBoard;

    @FXML
    private TableColumn<?, ?> ColLastNameRunnerBoard;

    @FXML
    private TableColumn<?, ?> ColRecordRunnerBoard;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnRunners;

    @FXML
    private Button btnLeaderBaord;

    @FXML
    private Label nbrRunners;

    @FXML
    private Label prizeOverviewLabel;

    @FXML
    private Label nbrSponsors;

    @FXML
    private Label nbrAgents;

    @FXML
    private Button btnSignout;

    @FXML
    private VBox pnItems;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlLeaderBoard;

    @FXML
    private Pane pnlRunners;

    @FXML
    private TextField recordRaceTimeRunner;

    @FXML
    private TableView<Runner> tableViewRunners;

    @FXML
    private TableView<Runner> tableViewRunnersBoard;

    @FXML
    public Label startTimeLabel;

    @FXML
    public TableView<Sponsor> tableViewSponsors;

    @FXML
    public Label winnerLabel;

    public TableColumn<?, ?> ColSponsorName;

    @FXML
    public Label LocationLabel;

    @FXML
    public Label distanceLabel;

    @FXML
    public Label endTimeLabel;

    @FXML
    public Label marathonNameLabel;


    public void initializeMarathonInfos() {
        Connexion connexion = null;
        try {
            connexion = new Connexion();
            connexion.connexion();

            // Get the data of the latest marathon from the database
            String query = "SELECT * FROM marathon WHERE id = ?";
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setInt(1, this.marathonId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                // Set the data of the marathon

                this.marathonNameLabel.setText(rs.getString("name"));
                this.LocationLabel.setText(rs.getString("location"));
                this.startTimeLabel.setText(rs.getString("starting_date"));
                this.endTimeLabel.setText(rs.getString("ending_date"));
                this.distanceLabel.setText(rs.getString("distance"));
                this.prizeOverviewLabel.setText(rs.getString("prize") + " DT");

                String sqlWinner = "SELECT * FROM runner WHERE marathon_id = ? AND record_race_time IS NOT NULL AND record_race_time = (SELECT MAX(record_race_time) FROM runner WHERE marathon_id = ?)";
                PreparedStatement statementWinner = connexion.prepareStatement(sqlWinner);
                statementWinner.setInt(1, this.marathonId);
                statementWinner.setInt(2, this.marathonId);
                ResultSet rsWinner = statementWinner.executeQuery();

                if (rsWinner.next()) {
                    winnerLabel.setText(rsWinner.getString("first_name") + " " + rsWinner.getString("last_name"));
                } else {
                    winnerLabel.setText("No winner yet");
                }

                // Get the number of runners for the marathon from the database
                String queryRunners = "SELECT COUNT(*) AS nbRunners FROM runner, marathon WHERE marathon.id = ? AND runner.marathon_id = marathon.id";
                PreparedStatement statementRunners = connexion.prepareStatement(queryRunners);
                statementRunners.setInt(1, this.marathonId);
                ResultSet rsRunners = statementRunners.executeQuery();

                if (rsRunners.next()) {
                    this.nbrRunners.setText(rsRunners.getString("nbRunners"));
                }

                // Get the number of sponsors for the marathon from the database
                String querySponsors = "SELECT COUNT(*) AS nbSponsors FROM sponsors, marathon WHERE marathon.id = ? AND sponsors.marathon_id = marathon.id";
                PreparedStatement statementSponsors = connexion.prepareStatement(querySponsors);
                statementSponsors.setInt(1, this.marathonId);
                ResultSet rsSponsors = statementSponsors.executeQuery();
                if (rsSponsors.next()) {
                    this.nbrSponsors.setText(rsSponsors.getString("nbSponsors"));
                }

                // Get the number of sponsors for the marathon from the database
                String queryAgents = "SELECT COUNT(*) AS nbAgents FROM administrators, marathon WHERE marathon.id = ? and role = 'AGENT' AND administrators.marathon_id = marathon.id";
                PreparedStatement statementAgents = connexion.prepareStatement(queryAgents);
                statementAgents.setInt(1, this.marathonId);
                ResultSet rsAgents = statementAgents.executeQuery();
                if (rsAgents.next()) {
                    this.nbrAgents.setText(rsAgents.getString("nbAgents"));
                }

                // Fill the tableView
                String sql = "SELECT sponsors.* FROM sponsors, marathon WHERE sponsors.marathon_id = marathon.id AND marathon.id = ?";
                PreparedStatement ps = connexion.prepareStatement(sql);
                ps.setInt(1, this.marathonId);
                ResultSet result = ps.executeQuery();
                ObservableList<Sponsor> sponsors = FXCollections.observableArrayList();

                while (result.next()) {
                    String sponsorName = result.getString("name");
                    Sponsor sponsor = new Sponsor(1, sponsorName, null, null);
                    sponsors.add(sponsor);
                }

                ColSponsorName.setCellValueFactory(new PropertyValueFactory<>("name"));
                tableViewSponsors.setItems(sponsors);

            }
        } catch (SQLException e) {
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

    @FXML
    void consultRunners(ActionEvent event) throws SQLException {
        try {
            Connexion connexion = new Connexion();
            connexion.connexion();

            String sql = "SELECT runner.* FROM runner JOIN administrators ON runner.marathon_id = administrators.marathon_id where administrators.marathon_id = ? AND runner.is_confirmed = 1";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, this.marathonId);
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

                Runner runner = new Runner(id, firstName, lastName, email, password, gender, recordRaceTime, isCanceled, isConfirmed, null);
                runners.add(runner);
            }


            ColFirstNameRunner.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            ColLastNameRunner.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            ColGenderRunner.setCellValueFactory(new PropertyValueFactory<>("gender"));
            ColRecordRunner.setCellValueFactory(new PropertyValueFactory<>("recordRaceTime"));

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
    void handleClicks(ActionEvent event) throws SQLException {
        if (event.getSource() == btnOverview) {
            pnlRunners.setVisible(false);
            pnlLeaderBoard.setVisible(false);

            pnlOverview.setVisible(true);
        } else if (event.getSource() == btnRunners) {
            pnlOverview.setVisible(false);
            pnlLeaderBoard.setVisible(false);

            pnlRunners.setVisible(true);
        } else if (event.getSource() == btnLeaderBaord) {
            pnlOverview.setVisible(false);
            pnlRunners.setVisible(false);

            Connexion connexion = new Connexion();
            connexion.connexion();

            String sql = "SELECT r.* FROM runner as r, marathon as m WHERE r.marathon_id = m.id and m.id = ? and r.is_confirmed = 1";
            PreparedStatement statement = connexion.prepareStatement(sql);
            statement.setInt(1, this.marathonId);
            ObservableList<Runner> runners = FXCollections.observableArrayList();
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
            ColRecordRunnerBoard.setCellValueFactory(new PropertyValueFactory<>("recordRaceTime"));
            ColGenderRunnerBoard.setCellValueFactory(new PropertyValueFactory<>("gender"));

            // Create a sorted list with the runners
            SortedList<Runner> sortedRunners = new SortedList<>(runners);

            // Bind the sorted list to the TableView

            tableViewRunnersBoard.setItems(sortedRunners);
            pnlLeaderBoard.setVisible(true);
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

            // Prepare the update statement
            String sql = "UPDATE runner SET record_race_time = ? WHERE id = ?";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, String.valueOf(recordRaceTimeRunner.getText()));
            ps.setInt(2, selectedRunner.getId());

            // Execute the update
            int rowsAffected = ps.executeUpdate();

            // Close database connection
            connexion.close();

            if (rowsAffected > 0) {
                // Update successful
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Record set successfully.");
                this.consultRunners(event);
                alert.showAndWait();
            } else {
                // No rows affected, update failed
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to set the Record.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while setting the Record.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    void exportProgram(ActionEvent event) {
        Connexion connexion = null;
        try {
            connexion = new Connexion();
            connexion.connexion();
            String selectedFileName = null;

            String queryMarathon = "SELECT program FROM marathon where id = ?";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            statementMarathon.setInt(1, this.marathonId);
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

    public void setMarathon(Integer marathonId) {
        this.marathonId = marathonId;
        System.out.println("Marathon ID: " + this.marathonId);
        this.initializeMarathonInfos();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewRunners.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    recordRaceTimeRunner.setText(String.valueOf(newSelection.getRecordRaceTime()));
                }catch (Exception ignored){}
            }
        });
    }
}
