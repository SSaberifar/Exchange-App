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

    public static void stageChanger(ActionEvent event, String s) throws IOException {
        Parent root = new FXMLLoader(Main.class.getResource(s)).load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        Scene loginPanel = new Scene(root);
        stage.setTitle("Fum coin exchange");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/icon.png"))));
        stage.setScene(loginPanel);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(t -> System.exit(0));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
