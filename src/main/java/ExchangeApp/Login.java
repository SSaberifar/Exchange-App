package ExchangeApp;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.mewebstudio.captcha.Captcha;
import com.mewebstudio.captcha.Config;
import com.mewebstudio.captcha.GeneratedCaptcha;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import net.synedra.validatorfx.Validator;

import java.io.IOException;

public class Login implements Initializable {

    @FXML
    private TextField loginName;
    @FXML
    private PasswordField loginPass;
    @FXML
    private TextField captchaCode;
    @FXML
    private ImageView loginCap;

    private final Validator validator = new Validator();
    private Stage stage;
    private final Config customConfig = new Config();
    private final Captcha captcha = new Captcha(customConfig);
    private String captchacode;

    @FXML
    public void generateCaptcha() {
        customConfig.setWidth(100);
        customConfig.setHeight(40);
        GeneratedCaptcha generatedCaptcha = captcha.generate();
        BufferedImage captchaImage = generatedCaptcha.getImage();
        captchacode = generatedCaptcha.getCode();
        Image captchaImg = SwingFXUtils.toFXImage(captchaImage, null);
        loginCap.setImage(captchaImg);
    }

    @FXML
    public void loginApp(ActionEvent event) throws IOException {
        // Username validation
        validator.createCheck().withMethod(c -> {
            String username = c.get("username");
            if (username == null || !Pattern.matches("^[a-z0-9_-]{3,15}$", username)) {
                c.error("Please enter a valid username!");
            }
        }).dependsOn("username", loginName.textProperty()).decorates(loginName).immediate();

        // Password validation
        validator.createCheck().withMethod(c -> {
            String password = c.get("password");
            if (password == null || !Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,20}$", password)) {
                c.error("Please enter a valid password!");
            }
        }).dependsOn("password", loginPass.textProperty()).decorates(loginPass).immediate();

        // Captcha validation
        validator.createCheck().withMethod(c -> {
            String captchaInput = c.get("captcha");
            if (!captchaInput.equals(captchacode)) {
                c.error("Please enter the correct captcha!");
            }
        }).dependsOn("captcha", captchaCode.textProperty()).decorates(captchaCode).immediate();

        // If validation is successful, proceed with login
        if (validator.validate()) {
            Database.loginDB(event, loginName.getText(), loginPass.getText());
        }
    }

    @FXML
    public void signUpPage(ActionEvent event) throws IOException {
        Main.stageChanger(event, "SignUp.fxml");
    }

    @FXML
    public void emailPage(ActionEvent event) throws IOException {
        Main.stageChanger(event, "SendEmail.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Generate a captcha on initialization
        generateCaptcha();
    }
}