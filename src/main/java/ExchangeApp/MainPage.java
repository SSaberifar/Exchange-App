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


public class MainPage extends Menu implements Initializable {

    @FXML
    private TableView<Coin> mainTable;
    @FXML
    private TableColumn<Coin, String> name;
    @FXML
    private TableColumn<Coin, Double> value;
    @FXML
    private TableColumn<Coin, String> changes;
    @FXML
    private TableColumn<Coin, Double> max_value;
    @FXML
    private TableColumn<Coin, Double> min_value;

    ObservableList<Coin> coinList = FXCollections.observableArrayList(
            new Coin("بیت کوین (BTC)", 2465907360.0, "+1.31%", 2465907360.0, 2430400560.0, 0),
            new Coin("دوج کوین (DOGE)", 4525.0, "+2.22%", 4525.0, 44010., 0),
            new Coin("دش (DASH)", 1633184.0, "+4.83%", 1633184.0, 1552157.0, 0),
            new Coin("لایت کوین (LTC)", 3693200.0, "+3.27%", 3693200.0, 3567760.0, 0)
    );


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        changes.setCellValueFactory(new PropertyValueFactory<>("changes"));
        max_value.setCellValueFactory(new PropertyValueFactory<>("max_value"));
        min_value.setCellValueFactory(new PropertyValueFactory<>("min_value"));

        mainTable.setItems(coinList);
    }
}
