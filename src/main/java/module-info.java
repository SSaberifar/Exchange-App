module Login_SignUp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires javafx.swing;
    requires captcha;


    opens ExchangeApp.UserAccount to javafx.fxml;
    exports ExchangeApp.UserAccount;
    opens ExchangeApp to javafx.fxml;
    exports ExchangeApp;
}