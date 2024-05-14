package ExchangeApp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;
import java.util.regex.Pattern;

public class Profile extends Menu {
    @FXML
    private Button editbtn;
    @FXML
    private TextField SignFName;
    @FXML
    private TextField SignLName;
    @FXML
    private TextField SignPhone;
    @FXML
    private TextField SignEmail;
    @FXML
    private PasswordField SignPass;
    private final Validator validator = new Validator();
    private int editSw = 1;

    public void editInfo() {
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
        if (editSw == 1) {
            SignFName.setDisable(false);
            SignLName.setDisable(false);
            SignEmail.setDisable(false);
            SignPass.setDisable(false);
            SignPhone.setDisable(false);
            editbtn.setText("ثبت اطلاعات کاربری");
        } else {
            if (validator.validate()) {
                SignFName.setDisable(true);
                SignLName.setDisable(true);
                SignEmail.setDisable(true);
                SignPass.setDisable(true);
                SignPhone.setDisable(true);
                editbtn.setText("ویرایش اطلاعات کاربری");
            }
        }
        editSw *= -1;
    }

}
