package ExchangeApp.UserAccount;

import com.mewebstudio.captcha.Captcha;
import com.mewebstudio.captcha.Config;
import com.mewebstudio.captcha.GeneratedCaptcha;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

import java.awt.image.BufferedImage;
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
    @FXML
    TextField CaptchaCode;
    @FXML
    ImageView SignCap;

    private Stage stage;
    private final Validator validator = new Validator();
    private final Config customConfig = new Config();
    private final Captcha captcha = new Captcha(customConfig);
    private String captchaCode;
    private final FileChooser fileChooser = new FileChooser();

    public void GenerateCaptcha() {
        customConfig.setWidth(100);
        customConfig.setHeight(40);
        GeneratedCaptcha generatedCaptcha = captcha.generate();
        BufferedImage captchaImage = generatedCaptcha.getImage();
        captchaCode = generatedCaptcha.getCode();
        Image captchaImg = SwingFXUtils.toFXImage(captchaImage, null);
        SignCap.setImage(captchaImg);
    }
    public void FileChoose(){
        File selectedFile = fileChooser.showOpenDialog(stage);
    }
    public void SignUpApp(){
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,20}$", c.get("password")) || c.get("password") == null || !c.get("password").equals(c.get("repeat password"))) {
                c.error("please enter valid password!");
            }
        }).dependsOn("password", SignPass.textProperty()).dependsOn("repeat password", SignRepass.textProperty()).decorates(SignPass).decorates(SignRepass).immediate();
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^[a-z0-9_-]{3,15}$", c.get("username")) || c.get("username") == null) {
                c.error("please enter valid username!");
            }
        }).dependsOn("username", SignName.textProperty()).decorates(SignName).immediate();
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^([A-Za-z])+$", c.get("firstname")) || c.get("firstname") == null) {
                c.error("please enter valid firstname!");
            }
        }).dependsOn("firstname", SignFName.textProperty()).decorates(SignFName).immediate();
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^([A-Za-z])+$", c.get("lastname")) || c.get("lastname") == null) {
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

        if (validator.validate() && CaptchaCode.getText().equals(captchaCode)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sign up complete");
            alert.setHeaderText(null);
            alert.setContentText("You successfully Signed Up!!!");
            alert.showAndWait();
        }
    }
    public void LoginPage(ActionEvent event) throws IOException {
        Parent root = new FXMLLoader(getClass().getResource("Login.fxml")).load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
