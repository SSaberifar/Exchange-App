package ExchangeApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

public class SendEmail {

    @FXML
    private TextField Email;
    @FXML
    private Button sendbtn;
    @FXML
    private Button recoverbtn;
    @FXML
    private TextField recovercode;

    private final Validator validator = new Validator();
    private final Random rand = new Random();
    private String code;

    /**
     * Validates the email and sends a recovery email with a code.
     */
    public void SignUpApp() {
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+", c.get("email"))) {
                c.error("please enter a valid email!");
            }
        }).dependsOn("email", Email.textProperty()).decorates(Email).immediate();

        if (validator.validate()) {
            sendRecoveryEmail();
        }
    }

    /**
     * Sends a recovery email with a code to the user's email address.
     */
    private void sendRecoveryEmail() {
        code = String.valueOf(rand.nextInt(9000) + 1000);

        // SMTP configuration
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        String username = "fumcoin780@gmail.com";
        String password = "npux osgx ehju mtib"; // Consider storing credentials securely

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(Email.getText()));
            message.setSubject("Recovery Email");
            message.setText("با سلام و عرض ادب کد بازیابی حساب کاربری شما: " + code);
            message.addHeader("FumCoin", "Recovery Email");
            Transport.send(message);

            showSuccessAlert("ایمیل بازیابی ارسال شد!");
            toggleEmailRecoveryFields(false);

        } catch (MessagingException e) {
            e.printStackTrace();
            showErrorAlert("خطا در ارسال ایمیل. لطفا دوباره تلاش کنید.");
        }
    }

    /**
     * Validates the recovery code entered by the user.
     *
     * @param event the action event that triggered the method.
     * @throws IOException if an error occurs during email recovery.
     */
    public void RecoverEmail(ActionEvent event) throws IOException {
        validator.createCheck().withMethod(c -> {
            if (!code.equals(c.get("recovery"))) {
                c.error("please enter a valid code!");
            }
        }).dependsOn("recovery", recovercode.textProperty()).decorates(recovercode).immediate();

        if (validator.validate()) {
            Database.EmailLogin(event, Email.getText());
        }
    }

    /**
     * Toggles the state of email recovery fields.
     *
     * @param enable whether to enable or disable the fields.
     */
    private void toggleEmailRecoveryFields(boolean enable) {
        Email.setDisable(!enable);
        sendbtn.setDisable(!enable);
        recoverbtn.setDisable(enable);
        recovercode.setDisable(enable);
    }

    /**
     * Shows a success alert with the given message.
     *
     * @param message the message to display in the alert.
     */
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.setTitle("بازیابی رمز عبور");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows an error alert with the given message.
     *
     * @param message the message to display in the alert.
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.setTitle("خطا");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
