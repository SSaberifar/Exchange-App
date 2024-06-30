package ExchangeApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.io.IOException;
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
            new Coin("اتریوم (ETH)", Database.lastValue("Ethereum"), Database.percent(1), Database.largeValue(1), Database.smallValue(1), 0.0),
            new Coin("دوج کوین (DOGE)", Database.lastValue("Dogecoin"), Database.percent(2), Database.largeValue(2), Database.smallValue(2), 0.0),
            new Coin("نات کوین (NOT)", Database.lastValue("Notcoin"), Database.percent(3), Database.largeValue(3), Database.smallValue(3), 0.0),
            new Coin("همستر (HAM)", Database.lastValue("Hamester"), Database.percent(4), Database.largeValue(4), Database.smallValue(4), 0.0)
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

        EventHandler<ActionEvent> actionEventHandler = this::handleAction;

        mainTable.setRowFactory(tv -> {
            TableRow<Coin> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    Coin clickedCoin = row.getItem();
                    handleRowClick(clickedCoin);
                    ActionEvent actionEvent = new ActionEvent(row, null);
                    actionEventHandler.handle(actionEvent);
                }
            });
            return row;
        });
    }

    private void handleRowClick(Coin coin) {
        System.out.println("Clicked coin: " + coin.getName() + ", Value: " + coin.getValue());
    }

    private void handleAction(ActionEvent event) {
        try {
            Token(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
