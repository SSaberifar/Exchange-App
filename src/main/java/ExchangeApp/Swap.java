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

    public void convert() {
        firstcoin = firstToken.getValue();
        secondcoin = secondToken.getValue();
        firstprice = Database.lastValue(firstcoin);
        secondprice = Database.lastValue(secondcoin);
        firstinput.setPromptText(String.valueOf(firstprice));
        secondinput.setPromptText(String.valueOf(secondprice));

        secondinput.setText(String.valueOf((Double.parseDouble(firstinput.getText()) * firstprice) / secondprice));

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("تبادل ارز");
        alert.setHeaderText("تایید عملیات");
        alert.setContentText("کارمزد عملیات شما 10 دلار می باشد! آیا مایل به ادامه هستید؟");

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                User.user.setpD(String.valueOf(User.user.getpD() - 10));
                User.user.setFee(User.user.getFee() + 10);
                switch (firstcoin) {
                    case "Ethereum":
                        User.user.setEth(String.valueOf(User.user.getEth() - Double.parseDouble(firstinput.getText())));
                        break;
                    case "Dogecoin":
                        User.user.setDog(String.valueOf(User.user.getDog() - Double.parseDouble(firstinput.getText())));
                        break;
                    case "Notcoin":
                        User.user.setNot(String.valueOf(User.user.getNot() - Double.parseDouble(firstinput.getText())));
                        break;
                    case "Hamester":
                        User.user.setHam(String.valueOf(User.user.getHam() - Double.parseDouble(firstinput.getText())));
                        break;
                }
                switch (secondcoin) {
                    case "Ethereum":
                        User.user.setEth(String.valueOf(User.user.getEth() + Double.parseDouble(secondinput.getText())));
                        break;
                    case "Dogecoin":
                        User.user.setDog(String.valueOf(User.user.getDog() + Double.parseDouble(secondinput.getText())));
                        break;
                    case "Notcoin":
                        User.user.setNot(String.valueOf(User.user.getNot() + Double.parseDouble(secondinput.getText())));
                        break;
                    case "Hamester":
                        User.user.setHam(String.valueOf(User.user.getHam() + Double.parseDouble(secondinput.getText())));
                        break;
                }
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("تبادل ارز");
                alert2.setHeaderText("موفقیت عملیات");
                alert2.setContentText("تبادل ارز با موفقیت انجام شد!");
                alert2.show();
            }
        });


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        firstToken.setItems(observableArrayList("Ethereum", "Dogecoin", "Notcoin", "Hamester"));
        secondToken.setItems(observableArrayList("Ethereum", "Dogecoin", "Notcoin", "Hamester"));

        firstprice = Database.lastValue(firstcoin);
        secondprice = Database.lastValue(secondcoin);
        firstinput.setPromptText(String.valueOf(firstprice));
        secondinput.setPromptText(String.valueOf(secondprice));
    }

}
