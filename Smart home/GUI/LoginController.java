package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    // Placeholder login authentication
    private boolean authenticate(String user, String pass) {
        // Later connect with DB
        return user.equals("admin") && pass.equals("1234");
    }

    @FXML
    private void initialize() {
        loginBtn.setOnAction(this::handleLogin);
        registerBtn.setOnAction(this::handleRegister);
    }

    private void handleLogin(ActionEvent event) {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (authenticate(user, pass)) {
            loadDashboard(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password!");
            alert.show();
        }
    }

    private void handleRegister(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Registration");
        alert.setContentText("Registration window will be added later.");
        alert.show();
    }

    private void loadDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Dashboard.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Smart Home Dashboard");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

