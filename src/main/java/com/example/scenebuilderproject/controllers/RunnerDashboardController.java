package com.example.scenebuilderproject.controllers;

import com.example.scenebuilderproject.Configuration.Connexion;
import com.example.scenebuilderproject.Registration;
import com.example.scenebuilderproject.entities.*;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class RunnerDashboardController {

    private Integer marathonId;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlLeaderBoard;

    @FXML
    private TableView<Runner> tableViewRunnersBoard;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnLeaderBaord;

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
    public TableColumn<?, ?> ColSponsorName;

    @FXML
    public Label LocationLabel;

    @FXML
    public Label prizeOverviewLabel;

    @FXML
    public Button btnSignout;

    @FXML
    public Label distanceLabel;

    @FXML
    public Label endTimeLabel;

    @FXML
    public Label marathonNameLabel;

    @FXML
    public Label nbrAgents;

    @FXML
    public Label nbrRunners;

    @FXML
    public Label nbrSponsors;

    @FXML
    public Label startTimeLabel;

    @FXML
    public TableView<Sponsor> tableViewSponsors;

    @FXML
    public Label winnerLabel;

    @FXML
    void Signout(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // Open the home interface
        Registration registration = new Registration();
        registration.openRegistration();
    }

    @FXML
    void handleClicks(ActionEvent event) throws SQLException {
        if (event.getSource() == btnOverview) {
            pnlLeaderBoard.setVisible(false);

            pnlOverview.setVisible(true);
        } else if (event.getSource() == btnLeaderBaord) {
            pnlOverview.setVisible(false);

            Connexion connexion = new Connexion();
            connexion.connexion();

            String sql = "SELECT r.* FROM runner as r, marathon as m WHERE r.marathon_id = m.id and m.id = ?";
            PreparedStatement statement = connexion.prepareStatement(sql);
            statement.setInt(1, marathonId);
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

    public void initializeMarathonInfos() {
        Connexion connexion = null;
        try {
            connexion = new Connexion();
            connexion.connexion();
            // Get the data of the latest marathon from the database
            String query = "SELECT * FROM marathon WHERE id = ?";
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setInt(1, marathonId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                // Set the data of the marathon

                this.marathonNameLabel.setText(rs.getString("name"));
                this.LocationLabel.setText(rs.getString("location"));
                this.startTimeLabel.setText(rs.getString("starting_date"));
                this.endTimeLabel.setText(rs.getString("ending_date"));
                this.distanceLabel.setText(rs.getString("distance"));
                this.prizeOverviewLabel.setText(rs.getString("prize") + " DT");

                String sqlWinner = "SELECT * FROM runner WHERE marathon_id = ? AND record_race_time IS NOT NULL AND record_race_time = (select max(record_race_time) from runner where marathon_id = ?)";
                PreparedStatement statementWinner = connexion.prepareStatement(sqlWinner);
                statementWinner.setInt(1, marathonId);
                statementWinner.setInt(2, marathonId);
                ResultSet rsWinner = statementWinner.executeQuery();

                if (rsWinner.next()) {
                    winnerLabel.setText(rsWinner.getString("first_name") + " " + rsWinner.getString("last_name"));
                } else {
                    winnerLabel.setText("No winner yet");
                }


                // Get the number of runners for the marathon from the database
                String queryRunners = "SELECT COUNT(*) AS nbRunners FROM runner, marathon WHERE runner.marathon_id = marathon.id and marathon.id = ?";
                PreparedStatement statementRunners = connexion.prepareStatement(queryRunners);
                statementRunners.setInt(1, marathonId);

                ResultSet rsRunners = statementRunners.executeQuery();
                if (rsRunners.next()) {
                    this.nbrRunners.setText(rsRunners.getString("nbRunners"));
                }

                // Get the number of sponsors for the marathon from the database
                String querySponsors = "SELECT COUNT(*) AS nbSponsors FROM sponsors, marathon WHERE sponsors.marathon_id = marathon.id and marathon.id = ?";
                PreparedStatement statementSponsors = connexion.prepareStatement(querySponsors);
                statementSponsors.setInt(1, marathonId);

                ResultSet rsSponsors = statementSponsors.executeQuery();
                if (rsSponsors.next()) {
                    this.nbrSponsors.setText(rsSponsors.getString("nbSponsors"));
                }

                // Get the number of sponsors for the marathon from the database
                String queryAgents = "SELECT COUNT(*) AS nbAgents FROM administrators, marathon WHERE administrators.marathon_id = marathon.id and marathon.id = ? and role = 'AGENT'";
                PreparedStatement statementAgents = connexion.prepareStatement(queryAgents);
                statementAgents.setInt(1, marathonId);

                ResultSet rsAgents = statementAgents.executeQuery();
                if (rsAgents.next()) {
                    this.nbrAgents.setText(rsAgents.getString("nbAgents"));
                }

                // Fill the tableView
                String sql = "SELECT sponsors.* FROM sponsors, marathon WHERE sponsors.marathon_id = marathon.id AND sponsors.marathon_id = ?";
                PreparedStatement ps = connexion.prepareStatement(sql);
                ps.setInt(1, marathonId);
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
    void exportProgram(ActionEvent event) {
        Connexion connexion = null;
        try {
            connexion = new Connexion();
            connexion.connexion();
            String selectedFileName = null;

            String queryMarathon = "SELECT program FROM marathon where id = ?";
            PreparedStatement statementMarathon = connexion.prepareStatement(queryMarathon);
            statementMarathon.setInt(1, marathonId);
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
}
