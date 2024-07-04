package ExchangeApp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class History extends Menu implements Initializable {
    @FXML
    private TableView<Object[]> historyTable;
    @FXML
    private Button printBtn;

    private List<Object[]> billRecords;

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

    public void print() throws IOException {
        try (FileWriter fileWriter = new FileWriter("bills.csv");
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println("status,type,token,amount,value");

            for (Object[] bill : billRecords) {
                printWriter.printf("%s,%s,%s,%f,%f%n",
                        bill[0], bill[1], bill[2], bill[3], bill[4]);
            }

            System.out.println("CSV file created successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    private void loadHistoryData() {
        try {
            billRecords = Database.showBills(Database.user.getUserShow());
            ObservableList<Object[]> data = FXCollections.observableArrayList(billRecords);
            historyTable.setItems(data);
        } catch (Exception e) {
            Database.showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در بارگذاری داده‌ها رخ داده است.");
            e.printStackTrace();
        }
    }
}
