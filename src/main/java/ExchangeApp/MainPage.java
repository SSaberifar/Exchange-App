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
            new Coin("اتریوم (ETH)", 0.865, "-3.888888889%", 1.149, 0.474, 0.0),
            new Coin("دوج کوین (DOGE)", 83734.284, "+99.36734286%", 84934.622, 30027.455, 0.0),
            new Coin("نات کوین (NOT)", 142.541, "+29.58272727%", 154.823, 89.649, 0.0),
            new Coin("همستر (HAM)", 0.973, "+21.625%", 1.127, 0.421, 0.0)
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
