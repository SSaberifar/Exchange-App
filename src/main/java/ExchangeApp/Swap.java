package ExchangeApp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class Swap extends Menu implements Initializable {
    @FXML
    private ComboBox<String> firstToken;
    @FXML
    private ComboBox<String> secondToken;
    @FXML
    private TextField firstinput;
    @FXML
    private TextField secondinput;

    private String firstcoin = "Ethereum";
    private String secondcoin = "Ethereum";
    private Double firstprice = 0.0;
    private Double secondprice = 0.0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        firstToken.setItems(observableArrayList("Ethereum", "Dogecoin", "Notcoin", "Hamester"));
        secondToken.setItems(observableArrayList("Ethereum", "Dogecoin", "Notcoin", "Hamester"));

        updatePrices();
    }

    private void updatePrices() {
        firstprice = Database.lastValue(firstcoin);
        secondprice = Database.lastValue(secondcoin);
        firstinput.setPromptText(String.valueOf(firstprice));
        secondinput.setPromptText(String.valueOf(secondprice));
    }

    public void convert() {
        if (!validateInputs()) {
            Database.showAlert(Alert.AlertType.ERROR, "خطا", "ورودی‌های نامعتبر! لطفا تمام فیلدها را به درستی پر کنید.");
            return;
        }

        firstcoin = firstToken.getValue();
        secondcoin = secondToken.getValue();
        firstprice = Database.lastValue(firstcoin);
        secondprice = Database.lastValue(secondcoin);

        try {
            double firstAmount = Double.parseDouble(firstinput.getText());
            double secondAmount = (firstAmount * firstprice) / secondprice;
            secondinput.setText(String.valueOf(secondAmount));

            showConfirmationAlert(firstAmount, secondAmount);
        } catch (NumberFormatException e) {
            Database.showAlert(Alert.AlertType.ERROR, "خطا", "لطفاً مقدار معتبری را وارد کنید.");
        }
    }

    private boolean validateInputs() {
        return firstToken.getValue() != null && secondToken.getValue() != null && !firstinput.getText().isEmpty() && isNumeric(firstinput.getText());
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showConfirmationAlert(double firstAmount, double secondAmount) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("تبادل ارز");
        alert.setHeaderText("تایید عملیات");
        alert.setContentText("کارمزد عملیات شما 10 دلار می باشد! آیا مایل به ادامه هستید؟");

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK && User.user.getpD() >= 10) {
                performSwap(firstAmount, secondAmount);
            } else {
                Database.showAlert(Alert.AlertType.ERROR, "خطا", "موجودی دلار کافی نیست!");
            }
        });
    }

    private void performSwap(double firstAmount, double secondAmount) {
        if (hasSufficientBalance(firstcoin, firstAmount)) {
            deductFirstCoinBalance(firstAmount);
            addSecondCoinBalance(secondAmount);
            User.user.setpD(User.user.getpD() - 10);
            Database.update("admin2024", "`profit(USD)`", 10);
            Database.showAlert(Alert.AlertType.INFORMATION, "تایید", "عملیات تبدیل با موفقیت انجام شد!");
        } else {
            Database.showAlert(Alert.AlertType.ERROR, "خطا", "موجودی کافی نیست!");
        }
    }

    private boolean hasSufficientBalance(String coin, double amount) {
        return switch (coin) {
            case "Ethereum" -> User.user.getEth() >= amount;
            case "Dogecoin" -> User.user.getDog() >= amount;
            case "Notcoin" -> User.user.getNot() >= amount;
            case "Hamester" -> User.user.getHam() >= amount;
            default -> false;
        };
    }

    private void deductFirstCoinBalance(double amount) {
        switch (firstcoin) {
            case "Ethereum":
                User.user.setEth(User.user.getEth() - amount);
                break;
            case "Dogecoin":
                User.user.setDog(User.user.getDog() - amount);
                break;
            case "Notcoin":
                User.user.setNot(User.user.getNot() - amount);
                break;
            case "Hamester":
                User.user.setHam(User.user.getHam() - amount);
                break;
        }
    }

    private void addSecondCoinBalance(double amount) {
        switch (secondcoin) {
            case "Ethereum":
                User.user.setEth(User.user.getEth() + amount);
                break;
            case "Dogecoin":
                User.user.setDog(User.user.getDog() + amount);
                break;
            case "Notcoin":
                User.user.setNot(User.user.getNot() + amount);
                break;
            case "Hamester":
                User.user.setHam(User.user.getHam() + amount);
                break;
        }
    }
}
