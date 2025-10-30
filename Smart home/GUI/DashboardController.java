package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Button livingRoomBtn;

    @FXML
    private Button bedroomBtn;

    @FXML
    private Button kitchenBtn;

    @FXML
    private Button garageBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button autoModeBtn;

    @FXML
    private Button voiceBtn;

    @FXML
    private Button statsBtn;

    @FXML
    private VBox deviceContainer;

    @FXML
    private TextArea logsArea;

    @FXML
    public void initialize() {
        logsArea.appendText("System started...\n");

        livingRoomBtn.setOnAction(e -> loadLights("Living Room"));
        bedroomBtn.setOnAction(e -> loadLights("Bedroom"));
        kitchenBtn.setOnAction(e -> logsArea.appendText("Kitchen devices will be added...\n"));
        garageBtn.setOnAction(e -> logsArea.appendText("Garage devices will be added...\n"));

        autoModeBtn.setOnAction(e -> logsArea.appendText("Auto mode window opening soon...\n"));
        voiceBtn.setOnAction(e -> logsArea.appendText("Voice assistant activating...\n"));
        statsBtn.setOnAction(e -> logsArea.appendText("Opening energy statistics...\n"));

        logoutBtn.setOnAction(e -> logout(e));
    }

    private void loadLights(String room) {
        logsArea.appendText(room + " selected...\n");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LightsControl.fxml"));
            VBox view = loader.load();
            deviceContainer.getChildren().setAll(view);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

