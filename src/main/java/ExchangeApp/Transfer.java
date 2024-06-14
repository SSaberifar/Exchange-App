package ExchangeApp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class Transfer extends Menu implements Initializable {
    @FXML
    private ComboBox<String> tokens;
    @FXML
    private TextField destinationId;
    @FXML
    private TextField tokenValue;
    @FXML
    private TextField Comment;

    public void transferToken() {
        if (User.user.getpD() >= 10) {
            switch (tokens.getValue()) {
                case "Ethereum":
                    if (User.user.getEth() >= Double.parseDouble(tokenValue.getText())) {
                        Database.update(destinationId.getText(), tokens.getValue(), tokenValue.getText());
                        User.user.setEth(String.valueOf(User.user.getEth() - Double.parseDouble(tokenValue.getText())));
                        User.user.setpD(User.user.getpD() - 10);
                        Database.showAlert(Alert.AlertType.INFORMATION, "تایید", "انتقال با موفقیت انجام شد");
                    }
                    break;
                case "Dogecoin":
                    if (User.user.getDog() >= Double.parseDouble(tokenValue.getText())) {
                        Database.update(destinationId.getText(), tokens.getValue(), tokenValue.getText());
                        User.user.setDog(String.valueOf(User.user.getDog() - Double.parseDouble(tokenValue.getText())));
                        User.user.setpD(User.user.getpD() - 10);
                        Database.showAlert(Alert.AlertType.INFORMATION, "تایید", "انتقال با موفقیت انجام شد");
                    }
                    break;
                case "Notcoin":
                    if (User.user.getNot() >= Double.parseDouble(tokenValue.getText())) {
                        Database.update(destinationId.getText(), tokens.getValue(), tokenValue.getText());
                        User.user.setNot(String.valueOf(User.user.getNot() - Double.parseDouble(tokenValue.getText())));
                        User.user.setpD(User.user.getpD() - 10);
                        Database.showAlert(Alert.AlertType.INFORMATION, "تایید", "انتقال با موفقیت انجام شد");
                    }
                    break;
                case "Hamester":
                    if (User.user.getHam() >= Double.parseDouble(tokenValue.getText())) {
                        Database.update(destinationId.getText(), tokens.getValue(), tokenValue.getText());
                        User.user.setHam(String.valueOf(User.user.getHam() - Double.parseDouble(tokenValue.getText())));
                        User.user.setpD(User.user.getpD() - 10);
                        Database.showAlert(Alert.AlertType.INFORMATION, "تایید", "انتقال با موفقیت انجام شد");
                    }
                    break;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        tokens.setItems(observableArrayList("Ethereum", "Dogecoin", "Notcoin", "Hamester"));
        Comment.setText(User.user.getUserShow());
    }
}
