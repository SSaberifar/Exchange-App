package ExchangeApp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class Swap extends Menu implements Initializable {
    @FXML
    private ComboBox firstToken;
    @FXML
    private ComboBox secondToken;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        firstToken.setItems(observableArrayList("Ethereum", "Dogecoin", "Notcoin", "Hamester"));
        secondToken.setItems(observableArrayList("Ethereum", "Dogecoin", "Notcoin", "Hamester"));
    }

}
