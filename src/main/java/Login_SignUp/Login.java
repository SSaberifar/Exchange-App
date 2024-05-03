package Login_SignUp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Login {

    private Parent root;
    private Stage stage;
    private Scene scene;

    public void signUpPage(ActionEvent event) throws IOException {
        //captcha


        root = new FXMLLoader(getClass().getResource("SignUp.fxml")).load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
