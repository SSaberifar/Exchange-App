package Login_SignUp;

import java.util.regex.*;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import net.synedra.validatorfx.Validator;
import java.io.IOException;

public class Login {

    @FXML
    TextField LoginName;
    @FXML
    PasswordField LoginPass;
    @FXML
    Button Loginbtn;

    private Validator validator = new Validator();
    private StringBinding problemsText;

    private Parent root;
    private Stage stage;
    private Scene scene;

    public void LoginApp(ActionEvent event) throws IOException {
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,20}$", c.get("password")) || c.get("password").equals(null)) {
                c.error("please enter valid password!");
            }
        }).dependsOn("password", LoginPass.textProperty()).decorates(LoginPass).immediate();
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^[a-z0-9_-]{3,15}$", c.get("username")) || c.get("username").equals(null)) {
                c.error("please enter valid username!");
            }
        }).dependsOn("username", LoginName.textProperty()).decorates(LoginName).immediate();
        if (validator.validate()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login complete");
            alert.setHeaderText(null);
            alert.setContentText("You successfully Logged in!!!");
            alert.showAndWait();
        }
    }

    public void signUpPage(ActionEvent event) throws IOException {
        //captcha


        root = new FXMLLoader(getClass().getResource("SignUp.fxml")).load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void EmailPage(ActionEvent event) throws IOException {
        root = new FXMLLoader(getClass().getResource("SendEmail.fxml")).load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
