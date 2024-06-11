package ExchangeApp;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Wallet extends Menu implements Initializable {

    @FXML
    private TableColumn<Coin, String> Coins;

    @FXML
    private TableColumn<Coin, Integer> Count;

    @FXML
    private TableColumn<Coin, Double> Price;

    @FXML
    private TableView<Coin> walletCoins;
    @FXML
    private Label profit_d;
    @FXML
    private Label profit_b;

    ObservableList<Coin> walletCoin = FXCollections.observableArrayList(
            new Coin("اتریوم(ETH)", Database.lastValue(1), null, 0, 0, Database.eth),
            new Coin("دوج کوین(DOGE)", Database.lastValue(2), null, 0, 0, Database.dog),
            new Coin("نات کوین(NOT)", Database.lastValue(3), null, 0, 0, Database.not),
            new Coin("همستر(HAM)", Database.lastValue(4), null, 0, 0, Database.ham)
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        Coins.setCellValueFactory(new PropertyValueFactory<>("name"));
        Count.setCellValueFactory(new PropertyValueFactory<>("Count"));
        Price.setCellValueFactory(new PropertyValueFactory<>("value"));
        profit_d.setText(Database.pD + " USD");
        profit_b.setText(Database.pB + " BTC");
        walletCoins.setItems(walletCoin);
    }

}
