    package com.example.scenebuilderproject;

    import com.example.scenebuilderproject.controllers.AgentDashboardController;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.stage.Stage;
    import java.io.IOException;

    public class DashboardAgent {
        private Integer marathonId;

        public DashboardAgent(Integer marathonId) {
            this.marathonId = marathonId;
        }

        public void openDashboard() {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("interface/DashboardAgent.fxml"));
                Parent root = loader.load();
                AgentDashboardController controller = loader.getController();
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
