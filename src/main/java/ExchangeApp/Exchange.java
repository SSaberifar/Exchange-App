package ExchangeApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Exchange extends Menu implements Initializable {

    @FXML
    public ComboBox recordscmbx;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        recordscmbx.setItems(FXCollections.observableArrayList("Bitcoin","Ethereum","NotCOin"));
    }
}
