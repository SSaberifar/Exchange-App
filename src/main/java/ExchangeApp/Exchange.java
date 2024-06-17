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

    public void showPrice() {
        finalprice.setText(String.valueOf(Double.parseDouble(tokennum.getText()) * Double.parseDouble(tokenprice.getText())));
    }

    public void createBills() {
        ToggleButton selected = (ToggleButton) buyToggle.getSelectedToggle();
        Database.saveBills("pending", selected.getText(), User.user.getUserShow(), tokencombo.getValue(), Double.parseDouble(tokennum.getText()), Double.parseDouble(finalprice.getText()));
    }

    public void tokenBills() {
        List<Object[]> billRecordes = Database.showBills(tokencombo.getValue());
        ObservableList<Object[]> data = FXCollections.observableArrayList(billRecordes);
        exchangeTable.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        finalprice.setText("0.0");
        tokencombo.setItems(observableArrayList("Ethereum", "Dogecoin", "Notcoin", "Hamester"));
        if (buying == false) {
            storebtn.setDisable(true);
        }
        TableColumn<Object[], Double> valueColumn = new TableColumn<>("قیمت نهایی");
        TableColumn<Object[], Double> amountColumn = new TableColumn<>("تعداد");
        TableColumn<Object[], String> typeColumn = new TableColumn<>("خرید/فروش");

        typeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue()[0]));
        amountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Double) cellData.getValue()[1]));
        valueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Double) cellData.getValue()[2]));

        exchangeTable.getColumns().add(typeColumn);
        exchangeTable.getColumns().add(amountColumn);
        exchangeTable.getColumns().add(valueColumn);
    }
}
