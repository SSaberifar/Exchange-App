package Graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class result {
    @FXML
    Label input1;

    public void Display(String username) {
        input1.setText("Hello " + username);
    }
}
