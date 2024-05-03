package Login_SignUp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class SignUp {

    private Parent root;
    private Stage stage;
    private Scene scene;
    FileChooser fileChooser = new FileChooser();

    public void FileChoose(ActionEvent event){
        File selectedFile = fileChooser.showOpenDialog(stage);
    }

    public void LoginPage(ActionEvent event) throws IOException {
        root = new FXMLLoader(getClass().getResource("Login.fxml")).load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
