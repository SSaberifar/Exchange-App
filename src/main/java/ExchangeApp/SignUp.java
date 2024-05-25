package ExchangeApp;

import com.mewebstudio.captcha.Captcha;
import com.mewebstudio.captcha.Config;
import com.mewebstudio.captcha.GeneratedCaptcha;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SignUp implements Initializable {

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
    File selectedFile;
    private String path;

    /**
     * Generates a new captcha image and displays it in the ImageView.
     */
    public void GenerateCaptcha() {
        customConfig.setWidth(100);
        customConfig.setHeight(40);
        GeneratedCaptcha generatedCaptcha = captcha.generate();
        BufferedImage captchaImage = generatedCaptcha.getImage();
        captchaCode = generatedCaptcha.getCode();
        Image captchaImg = SwingFXUtils.toFXImage(captchaImage, null);
        SignCap.setImage(captchaImg);
    }

    /**
     * Opens a file chooser dialog for selecting a file.
     */
    public void FileChoose() {
        selectedFile = fileChooser.showOpenDialog(stage);
        path = selectedFile.getAbsolutePath();
    }

    /**
     * Handles the sign-up process, including form validation and data submission.
     *
     * @param event the action event that triggered this method.
     * @throws IOException if an error occurs during sign-up.
     */
    public void SignUpApp(ActionEvent event) throws IOException {
        setupValidators();
        if (validator.validate()) {
            Database.SignUpDB(event, SignFName.getText(), SignLName.getText(), SignName.getText(), SignPass.getText(), SignEmail.getText(), SignPhone.getText(),path);
        }
    }

    /**
     * Navigates to the login page.
     *
     * @param event the action event that triggered this method.
     * @throws IOException if an error occurs during navigation.
     */
    public void LoginPage(ActionEvent event) throws IOException {
        Main.stageChanger(event, "Login.fxml");
    }

    /**
     * Sets up the validators for the sign-up form fields.
     */
    private void setupValidators() {
        validator.createCheck().withMethod(c -> {
            String password = c.get("password");
            String repeatPassword = c.get("repeat password");
            if (password == null || !Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,20}$", password) || !password.equals(repeatPassword)) {
                c.error("please enter a valid password!");
            }
        }).dependsOn("password", SignPass.textProperty()).dependsOn("repeat password", SignRepass.textProperty()).decorates(SignPass).decorates(SignRepass).immediate();

        validator.createCheck().withMethod(c -> {
            String username = c.get("username");
            if (username == null || !Pattern.matches("^[a-z0-9_-]{3,15}$", username)) {
                c.error("please enter a valid username!");
            }
        }).dependsOn("username", SignName.textProperty()).decorates(SignName).immediate();

        validator.createCheck().withMethod(c -> {
            String firstname = c.get("firstname");
            if (firstname == null || !Pattern.matches("^([\\u0600-\\u06FF\\s])+$", firstname)) {
                c.error("please enter a valid firstname!");
            }
        }).dependsOn("firstname", SignFName.textProperty()).decorates(SignFName).immediate();

        validator.createCheck().withMethod(c -> {
            String lastname = c.get("lastname");
            if (lastname == null || !Pattern.matches("^([\\u0600-\\u06FF\\s])+$", lastname)) {
                c.error("please enter a valid lastname!");
            }
        }).dependsOn("lastname", SignLName.textProperty()).decorates(SignLName).immediate();

        validator.createCheck().withMethod(c -> {
            String email = c.get("email");
            if (email == null || !Pattern.matches("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+", email)) {
                c.error("please enter a valid email!");
            }
        }).dependsOn("email", SignEmail.textProperty()).decorates(SignEmail).immediate();

        validator.createCheck().withMethod(c -> {
            String phoneNumber = c.get("phone number");
            if (phoneNumber == null || !Pattern.matches("^(?:(?:\\\\+?|00)(98)|(0))?((?:90|91|92|93|99)[0-9]{8})$", phoneNumber)) {
                c.error("please enter a valid phone number!");
            }
        }).dependsOn("phone number", SignPhone.textProperty()).decorates(SignPhone).immediate();

        validator.createCheck().withMethod(c -> {
            String captcha = c.get("captcha");
            if (captcha == null || !captcha.equals(captchaCode)) {
                c.error("please enter a valid captcha!");
            }
        }).dependsOn("captcha", CaptchaCode.textProperty()).decorates(CaptchaCode).immediate();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Generate a captcha on initialization
        GenerateCaptcha();
    }
}
