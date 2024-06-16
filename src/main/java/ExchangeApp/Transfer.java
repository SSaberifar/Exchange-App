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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("تبادل ارز");
        alert.setHeaderText("تایید عملیات");
        alert.setContentText("کارمزد عملیات شما 10 دلار می باشد! آیا مایل به ادامه هستید؟");

        alert.showAndWait().ifPresent(buttonType -> {
            if (User.user.getpD() >= 10) {
                switch (tokens.getValue()) {
                    case "Ethereum":
                        if (User.user.getEth() >= Double.parseDouble(tokenValue.getText())) {
                            Database.update(destinationId.getText(), tokens.getValue(), Double.valueOf(tokenValue.getText()));
                            User.user.setEth(User.user.getEth() - Double.parseDouble(tokenValue.getText()));
                            User.user.setpD(User.user.getpD() - 10);
                            Database.update("admin2024", "`profit(USD)`", 10);
                            Database.showAlert(Alert.AlertType.INFORMATION, "تایید", "انتقال با موفقیت انجام شد");
                        }
                        break;
                    case "Dogecoin":
                        if (User.user.getDog() >= Double.parseDouble(tokenValue.getText())) {
                            Database.update(destinationId.getText(), tokens.getValue(), Double.valueOf(tokenValue.getText()));
                            User.user.setDog(User.user.getDog() - Double.parseDouble(tokenValue.getText()));
                            User.user.setpD(User.user.getpD() - 10);
                            Database.update("admin2024", "`profit(USD)`", 10);
                            Database.showAlert(Alert.AlertType.INFORMATION, "تایید", "انتقال با موفقیت انجام شد");
                        }
                        break;
                    case "Notcoin":
                        if (User.user.getNot() >= Double.parseDouble(tokenValue.getText())) {
                            Database.update(destinationId.getText(), tokens.getValue(), Double.valueOf(tokenValue.getText()));
                            User.user.setNot(User.user.getNot() - Double.parseDouble(tokenValue.getText()));
                            User.user.setpD(User.user.getpD() - 10);
                            Database.update("admin2024", "`profit(USD)`", 10);
                            Database.showAlert(Alert.AlertType.INFORMATION, "تایید", "انتقال با موفقیت انجام شد");
                        }
                        break;
                    case "Hamester":
                        if (User.user.getHam() >= Double.parseDouble(tokenValue.getText())) {
                            Database.update(destinationId.getText(), tokens.getValue(), Double.valueOf(tokenValue.getText()));
                            User.user.setHam(User.user.getHam() - Double.parseDouble(tokenValue.getText()));
                            User.user.setpD(User.user.getpD() - 10);
                            Database.update("admin2024", "`profit(USD)`", 10);
                            Database.showAlert(Alert.AlertType.INFORMATION, "تایید", "انتقال با موفقیت انجام شد");
                        }
                        break;
                }
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        tokens.setItems(observableArrayList("Ethereum", "Dogecoin", "Notcoin", "Hamester"));
        Comment.setText(User.user.getUserShow());
    }
}
