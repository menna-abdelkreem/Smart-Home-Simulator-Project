 import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class LoginController extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        Label title = new Label("Smart Home Management");
        title.setStyle("-fx-font-size:20px; -fx-font-weight:bold;");

        TextField txtUser = new TextField();
        txtUser.setPromptText("Username");

        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Password");

        Label msg = new Label();
        msg.setStyle("-fx-text-fill:red;");

        Button btnLogin = new Button("Login");
        btnLogin.setOnAction(e -> {
            if (txtUser.getText().equals("admin") && txtPass.getText().equals("admin")) {
                openDashboard();
            } else {
                msg.setText("Invalid credentials (demo: admin/admin)");
            }
        });

        VBox box = new VBox(12, title, txtUser, txtPass, btnLogin, msg);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER);

        Scene scene = new Scene(box, 350, 260);

 
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) btnLogin.fire();
        });

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    private void openDashboard() {
        DashboardController d = new DashboardController();
        Scene dashScene = new Scene(d.createUI(), 1100, 700);

        primaryStage.setTitle("Smart Home Dashboard");
        primaryStage.setScene(dashScene);
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
