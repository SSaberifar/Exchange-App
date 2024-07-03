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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        tokens.setItems(observableArrayList("Ethereum", "Dogecoin", "Notcoin", "Hamester", "$"));
        Comment.setText(User.user.getUserShow());
    }

    public void transferToken() {
        if (!validateInputs()) {
            Database.showAlert(Alert.AlertType.ERROR, "خطا", "ورودی‌های نامعتبر! لطفا تمام فیلدها را به درستی پر کنید.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("تبادل ارز");
        confirmationAlert.setHeaderText("تایید عملیات");
        confirmationAlert.setContentText("کارمزد عملیات شما 10 دلار می باشد! آیا مایل به ادامه هستید؟");

        confirmationAlert.showAndWait().ifPresent(buttonType -> {
            if (User.user.getpD() >= 10 || tokens.getValue().equals("$")) {
                performTransfer();
            } else {
                Database.showAlert(Alert.AlertType.ERROR, "خطا", "موجودی دلار کافی نیست!");
            }
        });
    }

    private boolean validateInputs() {
        return tokens.getValue() != null && !destinationId.getText().isEmpty() && !tokenValue.getText().isEmpty() && isNumeric(tokenValue.getText());
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void performTransfer() {
        double tokenAmount = Double.parseDouble(tokenValue.getText());
        String selectedToken = tokens.getValue();

        if (hasSufficientBalance(selectedToken, tokenAmount)) {
            updateDatabase(selectedToken, tokenAmount);
            deductBalance(selectedToken, tokenAmount);
            Database.update("admin2024", "profit", 10);
            Database.showAlert(Alert.AlertType.INFORMATION, "تایید", "انتقال با موفقیت انجام شد");
        } else {
            Database.showAlert(Alert.AlertType.ERROR, "خطا", "موجودی کافی نیست!");
        }
    }

    private boolean hasSufficientBalance(String token, double amount) {
        return switch (token) {
            case "Ethereum" -> User.user.getEth() >= amount;
            case "Dogecoin" -> User.user.getDog() >= amount;
            case "Notcoin" -> User.user.getNot() >= amount;
            case "Hamester" -> User.user.getHam() >= amount;
            case "$" -> true;
            default -> false;
        };
    }

    private void updateDatabase(String token, double amount) {
        if (token.equals("$")) {
            Database.update(destinationId.getText(), "profit", amount - 10.0);
        } else {
            Database.update(destinationId.getText(), token, amount);
        }
    }

    private void deductBalance(String token, double amount) {
        switch (token) {
            case "Ethereum":
                User.user.setEth(User.user.getEth() - amount);
                User.user.setpD(User.user.getpD() - 10);
                break;
            case "Dogecoin":
                User.user.setDog(User.user.getDog() - amount);
                User.user.setpD(User.user.getpD() - 10);
                break;
            case "Notcoin":
                User.user.setNot(User.user.getNot() - amount);
                User.user.setpD(User.user.getpD() - 10);
                break;
            case "Hamester":
                User.user.setHam(User.user.getHam() - amount);
                User.user.setpD(User.user.getpD() - 10);
                break;
            case "$":
                if (User.user.getpD() == 0) {
                    User.user.setpD(User.user.getpD() + amount - 10.0);
                } else {
                    User.user.setpD(User.user.getpD() - amount);
                }
                break;
        }
    }
}
