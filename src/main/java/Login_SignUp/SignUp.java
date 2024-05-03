package Login_SignUp;

import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class SignUp {

    @FXML
    TextField SignName;
    @FXML
    TextField SignFName;
    @FXML
    TextField SignLName;
    @FXML
    TextField SignEmail;
    @FXML
    TextField SignPhone;
    @FXML
    PasswordField SignPass;
    @FXML
    PasswordField SignRepass;


    private Parent root;
    private Stage stage;
    private Scene scene;
    private Validator validator = new Validator();
    private StringBinding problemsText;
    FileChooser fileChooser = new FileChooser();

    public void FileChoose(ActionEvent event){
        File selectedFile = fileChooser.showOpenDialog(stage);
    }
    public void SignUpApp(ActionEvent event) throws IOException {
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$", c.get("password")) || c.get("password").equals(null) || !c.get("password").equals(c.get("repassword"))) {
                c.error("please enter valid password!");
            }
        }).dependsOn("password", SignPass.textProperty()).dependsOn("repassword", SignRepass.textProperty()).decorates(SignPass).decorates(SignRepass).immediate();
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^[a-z0-9_-]{3,15}$", c.get("username")) || c.get("username").equals(null)) {
                c.error("please enter valid username!");
            }
        }).dependsOn("username", SignName.textProperty()).decorates(SignName).immediate();
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^([A-Za-z])+$", c.get("firstname")) || c.get("firstname").equals(null)) {
                c.error("please enter valid firstname!");
            }
        }).dependsOn("firstname", SignFName.textProperty()).decorates(SignFName).immediate();
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^([A-Za-z])+$", c.get("lastname")) || c.get("lastname").equals(null)) {
                c.error("please enter valid lastname!");
            }
        }).dependsOn("lastname", SignLName.textProperty()).decorates(SignLName).immediate();
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+", c.get("email")) || c.get("email").equals(null)) {
                c.error("please enter valid email!");
            }
        }).dependsOn("email", SignEmail.textProperty()).decorates(SignEmail).immediate();
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^(?:(?:(?:\\\\+?|00)(98))|(0))?((?:90|91|92|93|99)[0-9]{8})$", c.get("phonenumber")) || c.get("phonenumber").equals(null)) {
                c.error("please enter valid phonenumber!");
            }
        }).dependsOn("phonenumber", SignPhone.textProperty()).decorates(SignPhone).immediate();
        if (validator.validate()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sign up complete");
            alert.setHeaderText(null);
            alert.setContentText("You successfully Signed Up!!!");
            alert.showAndWait();
        }
    }
    public void LoginPage(ActionEvent event) throws IOException {
        root = new FXMLLoader(getClass().getResource("Login.fxml")).load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
