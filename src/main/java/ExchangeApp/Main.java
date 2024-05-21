package ExchangeApp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    /**
     * Changes the stage to a new FXML scene.
     *
     * @param event    the action event that triggered the stage change.
     * @param fxmlFile the FXML file to load for the new scene.
     * @throws IOException if the FXML file cannot be loaded.
     */
    public static void stageChanger(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxmlFile)));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Load the initial scene (Login screen)
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Exchange.fxml")));
        Scene loginPanel = new Scene(root);

        // Set application window properties
        stage.setTitle("Fum Coin Exchange");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/icon.png"))));
        stage.setScene(loginPanel);
        stage.setResizable(false);
        stage.show();

        // Ensure the application exits when the window is closed
        stage.setOnCloseRequest(t -> System.exit(0));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
