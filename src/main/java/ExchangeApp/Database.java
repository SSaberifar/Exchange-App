package ExchangeApp;

import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.sql.*;

public class Database {
    static Connection connection;
    static String usershow;

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/fumcoin", "root", "");
    }

    public static void SignUpDB(ActionEvent event, String fname, String lname, String uname, String upass, String uemail, String uphone) throws IOException {
        String checkSQL = "SELECT * FROM users WHERE user_name = ? OR email = ? OR phone_number = ?";
        String insertSQL = "INSERT INTO users (first_name, last_name, user_name, password, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(checkSQL);
             PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {

            checkStmt.setString(1, uname);
            checkStmt.setString(2, uemail);
            checkStmt.setString(3, uphone);
            ResultSet result = checkStmt.executeQuery();

            if (result.next()) {
                showAlert(Alert.AlertType.WARNING, "ثبت نام کاربر", "کاربری با این نام کاربری، ایمیل یا شماره تلفن وجود دارد!");
            } else {
                insertStmt.setString(1, fname);
                insertStmt.setString(2, lname);
                insertStmt.setString(3, uname);
                insertStmt.setString(4, upass);
                insertStmt.setString(5, uemail);
                insertStmt.setString(6, uphone);
                insertStmt.executeUpdate();

                showAlert(Alert.AlertType.INFORMATION, "ثبت نام کاربر", "ثبت نام با موفقیت انجام شد!");
                Main.stageChanger(event, "Login.fxml");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در ثبت نام رخ داد: " + e.getMessage());
        }
    }

    public static void loginDB(ActionEvent event, String uname, String upass) throws IOException {
        String selectSQL = "SELECT * FROM users WHERE user_name = ? AND password = ?";

        try (Connection connection = getConnection();
             PreparedStatement psmt = connection.prepareStatement(selectSQL)) {

            psmt.setString(1, uname);
            psmt.setString(2, upass);
            ResultSet result = psmt.executeQuery();
            if (result.next()) {
                usershow = uname;
                showAlert(Alert.AlertType.INFORMATION, "ورود کاربر", "با موفقیت وارد شدید!");
                Main.stageChanger(event, "Profile.fxml");
            } else {
                showAlert(Alert.AlertType.INFORMATION, "ورود کاربر", "رمز عبور اشتباه است یا کاربری با این نام وجود ندارد!");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در ورود رخ داد: " + e.getMessage());
        }
    }

    public static void EmailLogin(ActionEvent event, String email) throws IOException {
        String selectSQL = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = getConnection();
             PreparedStatement psmt = connection.prepareStatement(selectSQL)) {

            psmt.setString(1, email);
            ResultSet result = psmt.executeQuery();
            if (result.next()) {
                usershow = result.getString("user_name");
                Main.stageChanger(event, "Profile.fxml");
            } else {
                showAlert(Alert.AlertType.INFORMATION, "ورود کاربر", "کاربری با این ایمیل حساب کاربری ندارد!");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در ورود رخ داد: " + e.getMessage());
        }
    }

    private static void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}