package ExchangeApp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class Exchange extends Menu implements Initializable {
    @FXML
    private ComboBox recordscmbx;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        recordscmbx.setItems(observableArrayList("Ethereum", "Dogecoin","Notcoin", "Hamester"));
    }
}
