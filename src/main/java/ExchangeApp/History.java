package ExchangeApp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
        initializeTableColumns();
        loadHistoryData();
    }

    private void initializeTableColumns() {
        TableColumn<Object[], String> statusColumn = createTableColumn("وضعیت", 0);
        TableColumn<Object[], String> typeColumn = createTableColumn("خرید/فروش", 1);
        TableColumn<Object[], String> tokenColumn = createTableColumn("ارز", 2);
        TableColumn<Object[], Double> amountColumn = createTableColumn("تعداد", 3);
        TableColumn<Object[], Double> valueColumn = createTableColumn("قیمت نهایی", 4);

        historyTable.getColumns().addAll(statusColumn, typeColumn, tokenColumn, amountColumn, valueColumn);
    }

    private <T> TableColumn<Object[], T> createTableColumn(String title, int index) {
        TableColumn<Object[], T> column = new TableColumn<>(title);
        column.setCellValueFactory(cellData -> new SimpleObjectProperty<>((T) cellData.getValue()[index]));
        return column;
    }

    private void loadHistoryData() {
        try {
            List<Object[]> billRecords = Database.showBills(User.user.getUserShow());
            ObservableList<Object[]> data = FXCollections.observableArrayList(billRecords);
            historyTable.setItems(data);
        } catch (Exception e) {
            Database.showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در بارگذاری داده‌ها رخ داده است.");
            e.printStackTrace();
        }
    }
}
