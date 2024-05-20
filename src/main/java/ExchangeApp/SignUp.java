package ExchangeApp;

import com.mewebstudio.captcha.Captcha;
import com.mewebstudio.captcha.Config;
import com.mewebstudio.captcha.GeneratedCaptcha;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private TextField SignName;
    @FXML
    private TextField SignFName;
    @FXML
    private TextField SignLName;
    @FXML
    private TextField SignEmail;
    @FXML
    private TextField SignPhone;
    @FXML
    private PasswordField SignPass;
    @FXML
    private PasswordField SignRepass;
    @FXML
    private TextField CaptchaCode;
    @FXML
    private ImageView SignCap;

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

    public void FileChoose() {
        File selectedFile = fileChooser.showOpenDialog(stage);
    }

    public void SignUpApp(ActionEvent event) throws IOException {
        {
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
            validator.createCheck().withMethod(c -> {
                if (!c.get("captcha").equals(captchaCode)) {
                    c.error("please enter valid phone captcha!");
                }
            }).dependsOn("captcha", CaptchaCode.textProperty()).decorates(CaptchaCode).immediate();

            if (validator.validate()) {
                Database.SignUpDB(event, SignFName.getText(), SignLName.getText(), SignName.getText(), SignPass.getText(), SignEmail.getText(), SignPhone.getText());
            }
        }
    }

    public void LoginPage(ActionEvent event) throws IOException {
        Main.stageChanger(event, "Login.fxml");
    }
}
