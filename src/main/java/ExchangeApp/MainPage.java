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
            new Coin("اتریوم (ETH)", Database.lastValue(1), Database.percent(1), Database.largeValue(1), Database.smallValue(1), 0.0),
            new Coin("دوج کوین (DOGE)", Database.lastValue(2), Database.percent(2), Database.largeValue(2), Database.smallValue(2), 0.0),
            new Coin("نات کوین (NOT)", Database.lastValue(3), Database.percent(3), Database.largeValue(3), Database.smallValue(3), 0.0),
            new Coin("همستر (HAM)", Database.lastValue(4), Database.percent(4), Database.largeValue(4), Database.smallValue(4), 0.0)
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
