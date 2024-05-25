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
    protected Button usershow;

    private Stage stage;

    /**
     * Switches to the Login scene.
     *
     * @param event the action event that triggered the method.
     * @throws IOException if the FXML file cannot be loaded.
     */
    public void exit(ActionEvent event) throws IOException {
        Main.stageChanger(event, "Login.fxml");
    }

    /**
     * Switches to the Exchange scene.
     *
     * @param event the action event that triggered the method.
     * @throws IOException if the FXML file cannot be loaded.
     */
    public void Exchange(ActionEvent event) throws IOException {
        Main.stageChanger(event, "Exchange.fxml");
    }

    /**
     * Switches to the Wallet scene.
     *
     * @param event the action event that triggered the method.
     * @throws IOException if the FXML file cannot be loaded.
     */
    public void wallet(ActionEvent event) throws IOException {
        Main.stageChanger(event, "Wallet.fxml");
    }

    /**
     * Switches to the Profile scene.
     *
     * @param event the action event that triggered the method.
     * @throws IOException if the FXML file cannot be loaded.
     */
    public void profile(ActionEvent event) throws IOException {
        Main.stageChanger(event, "Profile.fxml");
    }

    /**
     * Switches to the MainPage scene.
     *
     * @param event the action event that triggered the method.
     * @throws IOException if the FXML file cannot be loaded.
     */
    public void mainPage(ActionEvent event) throws IOException {
        Main.stageChanger(event, "MainPage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the user show button text
        usershow.setText(Database.userShow);

        // Start the clock update thread
        startClock();
    }

    /**
     * Starts a thread to update the clock text every second.
     */
    private void startClock() {
        final SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a");
        Thread clockThread = new Thread(() -> {
            while (true) {
                try {
                    // Update the clock text on the JavaFX Application Thread
                    javafx.application.Platform.runLater(() -> clock.setText(format.format(new Date())));
                    Thread.sleep(1000); // Update every second
                } catch (InterruptedException e) {
                    // Log and handle the exception properly
                    e.printStackTrace();
                }
            }
        });

        clockThread.setDaemon(true); // Ensure the thread will not prevent the application from exiting
        clockThread.start();
    }
}
