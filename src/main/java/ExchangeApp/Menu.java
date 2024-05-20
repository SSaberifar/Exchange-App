package ExchangeApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Menu implements Initializable {
    @FXML
    private Text clock;
    @FXML
    private Button usershow;

    private Stage stage;

    public void exit(ActionEvent event) throws IOException {
        Main.stageChanger(event, "Login.fxml");
    }

    public void wallet(ActionEvent event) throws IOException {
        Main.stageChanger(event, "Wallet.fxml");
    }

    public void profile(ActionEvent event) throws IOException {
        Main.stageChanger(event, "Profile.fxml");
    }

    public void mainPage(ActionEvent event) throws IOException {
        Main.stageChanger(event, "MainPage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usershow.setText(Database.usershow);
        final SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a");
        new Thread(() -> {
            while (true) {
                clock.setText(format.format(new Date()));
                try {
                    Thread.sleep(1000);//60 seconds interval
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
