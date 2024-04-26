import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.CHOCOLATE);

        stage.setTitle("Exchange App");

        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);

        stage.setWidth(1280);
        stage.setHeight(720);

        stage.setScene(scene);
        stage.show();
    }
}
