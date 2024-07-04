package ExchangeApp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class Exchange extends Menu implements Initializable {
    public static boolean buying = true;

    @FXML
    private ComboBox<String> tokencombo;
    @FXML
    public Button storebtn;
    @FXML
    public TextField tokennum;
    @FXML
    public TextField tokenprice;
    @FXML
    public Text finalprice;
    @FXML
    public ToggleGroup buyToggle;
    @FXML
    private TableView<Object[]> exchangeTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        finalprice.setText("0.0");
        tokencombo.setItems(observableArrayList("Ethereum", "Dogecoin", "Notcoin", "Hamester"));
        if (!buying) {
            storebtn.setDisable(true);
        }
        initializeTableColumns();
    }

    @FXML
    private void showPrice() {
        try {
            double numTokens = Double.parseDouble(tokennum.getText());
            double pricePerToken = Double.parseDouble(tokenprice.getText());
            double totalPrice = numTokens * pricePerToken;
            finalprice.setText(String.valueOf(totalPrice));
        } catch (NumberFormatException e) {
            Database.showAlert(Alert.AlertType.ERROR, "خطا", "لطفاً مقادیر معتبر وارد کنید.");
        }
    }

    @FXML
    private void createBills() {
        if (!validateInputs()) {
            Database.showAlert(Alert.AlertType.ERROR, "خطا", "لطفاً تمام فیلدها را به درستی پر کنید.");
            return;
        }
        ToggleButton selected = (ToggleButton) buyToggle.getSelectedToggle();
        String type = selected.getText();
        String token = tokencombo.getValue();
        double numTokens = Double.parseDouble(tokennum.getText());
        double totalPrice = Double.parseDouble(finalprice.getText());

        Database.saveBills("pending", type, Database.user.getUserShow(), token, numTokens, totalPrice);
        double money = Database.checkBills(type, Database.user.getUserShow(), token, numTokens, totalPrice);
        if (money != 0.0) {
            if (type.equals("خرید")) {
                Database.user.setpD(Database.user.getpD() - money);
                switch (token) {
                    case "Ethereum":
                        Database.user.setEth(Database.user.getEth() + numTokens);
                        break;
                    case "Dogecoin":
                        Database.user.setDog(Database.user.getDog() + numTokens);
                        break;
                    case "Notcoin":
                        Database.user.setNot(Database.user.getNot() + numTokens);
                        break;
                    case "Hamester":
                        Database.user.setHam(Database.user.getHam() + numTokens);
                        break;
                }
            } else {
                Database.user.setpD(Database.user.getpD() + money);
                switch (token) {
                    case "Ethereum":
                        Database.user.setEth(Database.user.getEth() - numTokens);
                        break;
                    case "Dogecoin":
                        Database.user.setDog(Database.user.getDog() - numTokens);
                        break;
                    case "Notcoin":
                        Database.user.setNot(Database.user.getNot() - numTokens);
                        break;
                    case "Hamester":
                        Database.user.setHam(Database.user.getHam() - numTokens);
                        break;
                }
            }
        }
    }

    @FXML
    private void tokenBills() {
        List<Object[]> billRecords = Database.showBills(tokencombo.getValue());
        ObservableList<Object[]> data = FXCollections.observableArrayList(billRecords);
        exchangeTable.setItems(data);
    }

    private void initializeTableColumns() {
        TableColumn<Object[], String> typeColumn = new TableColumn<>("خرید/فروش");
        TableColumn<Object[], Double> amountColumn = new TableColumn<>("تعداد");
        TableColumn<Object[], Double> valueColumn = new TableColumn<>("قیمت نهایی");

        typeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue()[0]));
        amountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Double) cellData.getValue()[1]));
        valueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Double) cellData.getValue()[2]));

        exchangeTable.getColumns().addAll(typeColumn, amountColumn, valueColumn);
    }

    private boolean validateInputs() {
        return tokencombo.getValue() != null &&
                !tokennum.getText().isEmpty() &&
                !tokenprice.getText().isEmpty() &&
                isNumeric(tokennum.getText()) &&
                isNumeric(tokenprice.getText());
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
