package ExchangeApp;

import java.awt.image.BufferedImage;
import java.util.regex.*;
import com.mewebstudio.captcha.Captcha;
import com.mewebstudio.captcha.Config;
import com.mewebstudio.captcha.GeneratedCaptcha;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import net.synedra.validatorfx.Validator;
import java.io.IOException;

public class Login {

    @FXML
    private TextField LoginName;
    @FXML
    private PasswordField LoginPass;
    @FXML
    private TextField CaptchaCode;
    @FXML
    private ImageView LoginCap;

    private final Validator validator = new Validator();
    private Stage stage;
    private final Config customConfig = new Config();
    private final Captcha captcha = new Captcha(customConfig);
    private String captchaCode;

    public void GenerateCaptcha() {
        customConfig.setWidth(100);
        customConfig.setHeight(40);
        GeneratedCaptcha generatedCaptcha = captcha.generate();
        BufferedImage captchaImage = generatedCaptcha.getImage();
        captchaCode = generatedCaptcha.getCode();
        Image captchaImg = SwingFXUtils.toFXImage(captchaImage, null);
        LoginCap.setImage(captchaImg);
    }

    public void LoginApp(ActionEvent event) throws IOException {
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,20}$", c.get("password")) || c.get("password") == null) {
                c.error("please enter valid password!");
            }
        }).dependsOn("password", LoginPass.textProperty()).decorates(LoginPass).immediate();
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("^[a-z0-9_-]{3,15}$", c.get("username")) || c.get("username") == null) {
                c.error("please enter valid username!");
            }
        }).dependsOn("username", LoginName.textProperty()).decorates(LoginName).immediate();
        validator.createCheck().withMethod(c -> {
            if (!c.get("captcha").equals(captchaCode)) {
                c.error("please enter valid captcha!");
            }
        }).dependsOn("captcha", CaptchaCode.textProperty()).decorates(CaptchaCode).immediate();
        if (validator.validate()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("ورود کاربر");
            alert.setHeaderText(null);
            alert.setContentText("با موفقیت وارد شدید!");
            alert.showAndWait();
            Main.stageChanger(event,"Profile.fxml");
        }
    }

    public void signUpPage(ActionEvent event) throws IOException {
        Main.stageChanger(event,"SignUp.fxml");
    }

    public void EmailPage(ActionEvent event) throws IOException {
        Main.stageChanger(event,"SendEmail.fxml");
    }
}
