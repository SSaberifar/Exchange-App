package ExchangeApp;

import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.*;

public class Database {
    static Connection connection;
    static String usershow;

    public static void SignUpDB(ActionEvent event, String fname, String lname, String uname, String upass, String uemail, String uphone) throws IOException {
        {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fumcoin", "root", "");
                PreparedStatement psmt = connection.prepareStatement("INSERT INTO users (first_name,last_name,user_name,password,email,phone_number) VALUES (?,?,?,?,?,?)");
                psmt.setString(1, fname);
                psmt.setString(2, lname);
                psmt.setString(3, uname);
                psmt.setString(4, upass);
                psmt.setString(5, uemail);
                psmt.setString(6, uphone);
                psmt.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setTitle("ثبت نام کاربر");
                alert.setHeaderText(null);
                alert.setContentText("ثبت نام با موفقیت انجام شد!");
                alert.showAndWait();
                Main.stageChanger(event, "Login.fxml");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void LoginDB(ActionEvent event, String uname, String upass) throws IOException {
        {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fumcoin", "root", "");
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("Select * FROM users");
                while (result.next()) {
                    if (result.getString("user_name").equals(uname) && result.getString("password").equals(upass)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                        alert.setTitle("ورود کاربر");
                        alert.setHeaderText(null);
                        alert.setContentText("با موفقیت وارد شدید!");
                        alert.showAndWait();
                        usershow = uname;
                        Main.stageChanger(event, "Profile.fxml");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                        alert.setTitle("ورود کاربر");
                        alert.setHeaderText(null);
                        alert.setContentText("رمز عبور اشتباه است یا کاربری با این نام وجود ندارد!");
                        alert.showAndWait();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void EmailLogin(ActionEvent event,String email) throws IOException{
        {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fumcoin", "root", "");
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("Select * FROM users");
                while (result.next()) {
                    if (result.getString("email").equals(email)) {
                        usershow = result.getString("user_name");
                        Main.stageChanger(event, "Profile.fxml");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                        alert.setTitle("ورود کاربر");
                        alert.setHeaderText(null);
                        alert.setContentText("کاربری با این ایمیل حساب کاربری ندارد!");
                        alert.showAndWait();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
