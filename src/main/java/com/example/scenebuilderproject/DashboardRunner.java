    package com.example.scenebuilderproject;

    import com.example.scenebuilderproject.controllers.RunnerDashboardController;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.stage.Stage;

    import java.io.IOException;
    import java.util.Objects;

    public class DashboardRunner {
        private Integer marathonId;

        public DashboardRunner(Integer marathonId) {
            this.marathonId = marathonId;
        }
        public void openDashboard() {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("interface/DashboardRunner.fxml"));
                Parent root = loader.load();
                RunnerDashboardController controller = loader.getController();
                controller.setMarathon(marathonId);

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                //stage.setTitle("Dashboard");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
