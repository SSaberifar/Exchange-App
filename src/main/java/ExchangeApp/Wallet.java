package ExchangeApp;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    ObservableList<Coin> walletCoin = FXCollections.observableArrayList(
            new Coin("بیت کوین", 2465907360.0, null, 0, 0, 20),
            new Coin("دوج کوین", 4525.0, null, 0, 0, 10),
            new Coin("دش", 1633184.0, null, 0, 0, 30),
            new Coin("لایت کوین", 3693200.0, null, 0, 0, 100)
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        Coins.setCellValueFactory(new PropertyValueFactory<>("name"));
        Count.setCellValueFactory(new PropertyValueFactory<>("Count"));
        Price.setCellValueFactory(new PropertyValueFactory<>("value"));

        walletCoins.setItems(walletCoin);
    }

}
