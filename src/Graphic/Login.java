package Graphic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Login {

    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    TextField input1;
    @FXML
    TextField input2;

    public void LoginApp(ActionEvent e) throws IOException {
        String username = input1.getText();
        String userpass = input2.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("result.fxml"));
        root = loader.load();

        result resultControl = loader.getController();
        resultControl.Display(username);

        scene = new Scene(root);
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
