package ExchangeApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

import java.io.IOException;
import java.util.regex.Pattern;

public class Profile {

    private final Validator validator = new Validator();

    @FXML
    Button menubtn;
    @FXML
    GridPane menu;
    @FXML
    Button editbtn;
    @FXML
    TextField SignFName;
    @FXML
    TextField SignLName;
    @FXML
    TextField SignPhone;
    @FXML
    TextField SignEmail;
    @FXML
    PasswordField SignPass;
    int menuSw=1;
    int editSw=1;

    public void menu(){
        menu.setVisible(menuSw != 1);
        menuSw*=-1;
    }
    public void exit(ActionEvent event)throws IOException {
        Parent root = new FXMLLoader(getClass().getResource("Login.fxml")).load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void editInfo(){
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,20}$", c.get("password")) || c.get("password") == null) {
                c.error("please enter valid password!");
            }
        }).dependsOn("password", SignPass.textProperty()).decorates(SignPass).immediate();
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^([\\u0600-\\u06FF\\s])+$", c.get("firstname")) || c.get("firstname") == null) {
                c.error("please enter valid firstname!");
            }
        }).dependsOn("firstname", SignFName.textProperty()).decorates(SignFName).immediate();
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^([\\u0600-\\u06FF\\s])+$", c.get("lastname")) || c.get("lastname") == null) {
                c.error("please enter valid lastname!");
            }
        }).dependsOn("lastname", SignLName.textProperty()).decorates(SignLName).immediate();
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+", c.get("email")) || c.get("email") == null) {
                c.error("please enter valid email!");
            }
        }).dependsOn("email", SignEmail.textProperty()).decorates(SignEmail).immediate();
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^(?:(?:\\\\+?|00)(98)|(0))?((?:90|91|92|93|99)[0-9]{8})$", c.get("phone number")) || c.get("phone number") == null) {
                c.error("please enter valid phone number!");
            }
        }).dependsOn("phone number", SignPhone.textProperty()).decorates(SignPhone).immediate();
        if(editSw==1){
            SignFName.setDisable(false);
            SignLName.setDisable(false);
            SignEmail.setDisable(false);
            SignPass.setDisable(false);
            SignPhone.setDisable(false);
            editbtn.setText("ثبت اطلاعات کاربری");
        }else{
            if(validator.validate()){
                SignFName.setDisable(true);
                SignLName.setDisable(true);
                SignEmail.setDisable(true);
                SignPass.setDisable(true);
                SignPhone.setDisable(true);
                editbtn.setText("ویرایش اطلاعات کاربری");
            }
        }
        editSw*=-1;
    }

}
