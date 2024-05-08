package ExchangeApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("UserAccount/Login.fxml"));
        Scene loginPanel = new Scene(root);
        stage.setTitle("Exchange App");
        stage.getIcons().add(new Image("ExchangeApp/images/icon.png"));
        stage.setScene(loginPanel);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
