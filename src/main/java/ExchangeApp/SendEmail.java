package ExchangeApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;


public class SendEmail {
    Random rand = new Random();
    @FXML
    private TextField Email;
    @FXML
    private Button sendbtn;
    @FXML
    private Button recoverbtn;
    @FXML
    private TextField recovercode;

    private Stage stage;
    private final Validator validator = new Validator();
    private String code;

    public void SignUpApp() {
        validator.createCheck().withMethod(c -> {
            if (!Pattern.matches("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+", c.get("email")) || c.get("email") == null) {
                c.error("please enter valid email!");
            }
        }).dependsOn("email", Email.textProperty()).decorates(Email).immediate();

        if (validator.validate()) {
            Email.setDisable(true);
            sendbtn.setDisable(true);
            recoverbtn.setDisable(false);
            recovercode.setDisable(false);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("بازیابی رمز عبور");
            alert.setHeaderText(null);
            alert.setContentText("ایمیل بازیابی ارسال شد!");
            alert.showAndWait();

            code = String.valueOf(rand.nextInt(9000) + 1000);

            /////smtp config/////
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            String username = "fumcoin780@gmail.com";
            String password = "npux osgx ehju mtib";

            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };

            Session session = Session.getInstance(properties, authenticator);

            /////message/////
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("fumcoin780@gmail.com"));
                message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(Email.getText())));
                message.setSubject("Recovery Email");
                message.setText("با سلام و عرض ادب کد بازیابی حساب کاربری شما:" + " " + code);
                message.addHeader("FumCoin", "Recovery Email");
                Transport.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void RecoverEmail(ActionEvent event) throws IOException {
        validator.createCheck().withMethod(c -> {
            if (!code.equals(c.get("recovery")) || c.get("recovery") == null) {
                c.error("please enter valid code!");
            }
        }).dependsOn("recovery", recovercode.textProperty()).decorates(recovercode).immediate();
        if (validator.validate()) {
            Main.stageChanger(stage, event, "Profile.fxml");
        }
    }
}
