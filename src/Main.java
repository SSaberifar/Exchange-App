import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Graphic/Login.fxml"));
        Scene loginPanel = new Scene(root);
        stage.setTitle("Exchange App");
        Image icon = new Image("Graphic/icon.png");
        stage.setScene(loginPanel);
        stage.getIcons().add(icon);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
