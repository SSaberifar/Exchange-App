package ExchangeApp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.synedra.validatorfx.Validator;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Profile extends Menu implements Initializable {

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
    @FXML
    private ImageView ProfileImage;

    private final Validator validator = new Validator();
    private boolean isEditing = false;

    @FXML
    public void editInfo() {
        setupValidation();

        if (!isEditing) {
            enableEditing(true);
            editbtn.setText("ثبت اطلاعات کاربری");
        } else {
            if (validator.validate()) {
                Database.update(SignFName.getText(), SignLName.getText(), usershow.getText(), SignPass.getText(), SignEmail.getText(), SignPhone.getText());
                enableEditing(false);
                // Save updated information to the database or perform any required actions here
                editbtn.setText("ویرایش اطلاعات کاربری");
            }
        }

        isEditing = !isEditing;
    }

    private void setupValidation() {
        validator.createCheck()
                .withMethod(c -> {
                    if (!Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,20}$", c.get("password"))) {
                        c.error("please enter valid password!");
                    }
                })
                .dependsOn("password", SignPass.textProperty())
                .decorates(SignPass)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    if (!Pattern.matches("^([\\u0600-\\u06FF\\s])+$", c.get("firstname"))) {
                        c.error("please enter valid firstname!");
                    }
                })
                .dependsOn("firstname", SignFName.textProperty())
                .decorates(SignFName)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    if (!Pattern.matches("^([\\u0600-\\u06FF\\s])+$", c.get("lastname"))) {
                        c.error("please enter valid lastname!");
                    }
                })
                .dependsOn("lastname", SignLName.textProperty())
                .decorates(SignLName)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    if (!Pattern.matches("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+", c.get("email"))) {
                        c.error("please enter valid email!");
                    }
                })
                .dependsOn("email", SignEmail.textProperty())
                .decorates(SignEmail)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    if (!Pattern.matches("^(?:(?:\\+?|00)(98)|(0))?((?:90|91|92|93|99)[0-9]{8})$", c.get("phone number"))) {
                        c.error("please enter valid phone number!");
                    }
                })
                .dependsOn("phone number", SignPhone.textProperty())
                .decorates(SignPhone)
                .immediate();
    }

    private void enableEditing(boolean enable) {
        SignFName.setDisable(!enable);
        SignLName.setDisable(!enable);
        SignEmail.setDisable(!enable);
        SignPass.setDisable(!enable);
        SignPhone.setDisable(!enable);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        ProfileImage.setImage(new Image("file:" + User.user.getUserImage()));
        SignFName.setText(User.user.getUserFirstName());
        SignLName.setText(User.user.getUserLastName());
        SignPass.setText(User.user.getUserPassword());
        SignEmail.setText(User.user.getUserEmail());
        SignPhone.setText(User.user.getUserPhone());
    }
}
