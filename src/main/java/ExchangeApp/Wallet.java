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

    ObservableList<Coin> walletCoin = FXCollections.observableArrayList(
            new Coin("اتریوم(ETH)", Database.lastValue("Ethereum"), 0, 0, 0, Database.user.getEth()),
            new Coin("دوج کوین(DOGE)", Database.lastValue("Dogecoin"), 0, 0, 0, Database.user.getDog()),
            new Coin("نات کوین(NOT)", Database.lastValue("Notcoin"), 0, 0, 0, Database.user.getNot()),
            new Coin("همستر(HAM)", Database.lastValue("Hamester"), 0, 0, 0, Database.user.getHam())
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        Coins.setCellValueFactory(new PropertyValueFactory<>("name"));
        Count.setCellValueFactory(new PropertyValueFactory<>("Count"));
        Price.setCellValueFactory(new PropertyValueFactory<>("value"));
        profit_d.setText(Database.user.getpD() + " USD");
        walletCoins.setItems(walletCoin);
    }

}
