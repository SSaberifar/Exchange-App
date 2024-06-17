package ExchangeApp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class History extends Menu implements Initializable {
    @FXML
    private TableView<Object[]> historyTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        TableColumn<Object[], String> statusColumn = new TableColumn<>("وضعیت");
        TableColumn<Object[], String> typeColumn = new TableColumn<>("خرید/فروش");
        TableColumn<Object[], String> tokenColumn = new TableColumn<>("ارز");
        TableColumn<Object[], Double> amountColumn = new TableColumn<>("تعداد");
        TableColumn<Object[], Double> valueColumn = new TableColumn<>("قیمت نهایی");

        statusColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue()[0]));
        typeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue()[1]));
        tokenColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue()[2]));
        amountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Double) cellData.getValue()[3]));
        valueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Double) cellData.getValue()[4]));

        historyTable.getColumns().add(statusColumn);
        historyTable.getColumns().add(typeColumn);
        historyTable.getColumns().add(tokenColumn);
        historyTable.getColumns().add(amountColumn);
        historyTable.getColumns().add(valueColumn);

        List<Object[]> billRecordes = Database.showBills(User.user.getUserShow());
        ObservableList<Object[]> data = FXCollections.observableArrayList(billRecordes);
        historyTable.setItems(data);
    }
}
