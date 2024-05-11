package ExchangeApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Profile {

    @FXML
    Button menubtn;
    @FXML
    GridPane menu;
    int sw=1;

    public void menu(ActionEvent event){
        if(sw==1){
            menu.setVisible(false);
        }else{
            menu.setVisible(true);
        }
        sw*=-1;
    }

}
