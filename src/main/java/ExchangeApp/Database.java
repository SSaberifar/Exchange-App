package ExchangeApp;

import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.*;

public class Database {

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/fumcoin", "root", "");
    }

    public static void SignUpDB(ActionEvent event, String fname, String lname, String uname, String upass, String uemail, String uphone, String imgPath) throws IOException {
        String checkSQL = "SELECT * FROM users WHERE user_name = ? OR email = ? OR phone_number = ?";
        String insertSQL = "INSERT INTO users (first_name, last_name, user_name, password, email, phone_number, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement checkStmt = connection.prepareStatement(checkSQL); PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {

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
                insertStmt.setString(7, imgPath);
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

        try (Connection connection = getConnection(); PreparedStatement psmt = connection.prepareStatement(selectSQL)) {

            psmt.setString(1, uname);
            psmt.setString(2, upass);
            ResultSet result = psmt.executeQuery();
            if (result.next()) {
                User.user = new User(result.getString("user_name"), result.getString("first_name"), result.getString("last_name"), result.getString("password"), result.getString("email"), result.getString("phone_number"), result.getString("imagePath"), result.getDouble("profit(USD)"), result.getString("Ethereum"), result.getString("Dogecoin"), result.getString("Notcoin"), result.getString("Hamester"));
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

        try (Connection connection = getConnection(); PreparedStatement psmt = connection.prepareStatement(selectSQL)) {

            psmt.setString(1, email);
            ResultSet result = psmt.executeQuery();
            if (result.next()) {
                User.user = new User(result.getString("user_name"), result.getString("first_name"), result.getString("last_name"), result.getString("password"), result.getString("email"), result.getString("phone_number"), result.getString("imagePath"), result.getDouble("profit(USD)"), result.getString("Ethereum"), result.getString("Dogecoin"), result.getString("Notcoin"), result.getString("Hamester"));
                Main.stageChanger(event, "Profile.fxml");
            } else {
                showAlert(Alert.AlertType.INFORMATION, "ورود کاربر", "کاربری با این ایمیل حساب کاربری ندارد!");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در ورود رخ داد: " + e.getMessage());
        }
    }

    public static Double lastValue(String type) {
        Double value = 0.0;
        String query = "SELECT " + type + " FROM token_price LIMIT 1 OFFSET 1439";

        try (Connection connection = getConnection(); PreparedStatement psmt = connection.prepareStatement(query)) {

            ResultSet result = psmt.executeQuery();
            if (result.next()) {
                value = result.getDouble(type);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در پیدا کردن مقدار: " + e.getMessage());
        }
        return value;
    }

    public static Double largeValue(int num) {
        Double value = 0.0;
        String column = getColumn(num);
        if (column == null) return value;

        String query = "SELECT MAX(" + column + ") AS max_price FROM token_price";

        try (Connection connection = getConnection(); PreparedStatement psmt = connection.prepareStatement(query)) {

            ResultSet result = psmt.executeQuery();
            if (result.next()) {
                value = result.getDouble("max_price");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در پیدا کردن مقدار: " + e.getMessage());
        }
        return value;
    }

    public static Double smallValue(int num) {
        Double value = 0.0;
        String column = getColumn(num);
        if (column == null) return value;

        String query = "SELECT MIN(" + column + ") AS min_price FROM token_price";

        try (Connection connection = getConnection(); PreparedStatement psmt = connection.prepareStatement(query)) {

            ResultSet result = psmt.executeQuery();
            if (result.next()) {
                value = result.getDouble("min_price");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در پیدا کردن مقدار: " + e.getMessage());
        }
        return value;
    }

    public static Double percent(int num) {
        Double value = 0.0;
        Double firstRow = 0.0;
        Double lastRow = 0.0;
        String column = getColumn(num);
        if (column == null) return value;

        String query1 = "SELECT " + column + " FROM token_price ORDER BY time LIMIT 1";
        String query2 = "SELECT " + column + " FROM token_price LIMIT 1 OFFSET 1439";

        try (Connection connection = getConnection(); PreparedStatement stmt1 = connection.prepareStatement(query1); PreparedStatement stmt2 = connection.prepareStatement(query2)) {

            ResultSet result1 = stmt1.executeQuery();
            ResultSet result2 = stmt2.executeQuery();

            if (result1.next()) {
                firstRow = result1.getDouble(column);
            }
            if (result2.next()) {
                lastRow = result2.getDouble(column);
            }
            if (firstRow != 0) {
                value = ((lastRow - firstRow) / firstRow) * 100;
            } else {
                showAlert(Alert.AlertType.ERROR, "خطا", "مقدار اولیه صفر است");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در پیدا کردن مقدار: " + e.getMessage());
        }

        return value;
    }

    public static void update(String desUser, String token, String value) {
        String query = "UPDATE users SET " + token + " = " + token + " + ? WHERE user_name = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, Double.parseDouble(value));
            stmt.setString(2, desUser);
            stmt.executeUpdate();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "خطا در آپدیت اطلاعات: " + e.getMessage());
        }
    }

    public static void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void updateInfo(String fname, String lname, String uname, String upass, String uemail, String uphone) {
        String updateSql = "UPDATE users SET first_name=?, last_name=?, password=?, email=?, phone_number=? WHERE user_name=?";

        try (Connection connection = getConnection(); PreparedStatement psmt = connection.prepareStatement(updateSql)) {

            psmt.setString(1, fname);
            psmt.setString(2, lname);
            psmt.setString(3, upass);
            psmt.setString(4, uemail);
            psmt.setString(5, uphone);
            psmt.setString(6, uname);

            int result = psmt.executeUpdate();
            if (result > 0) {
                showAlert(Alert.AlertType.INFORMATION, "اطلاعات کاربر", "اطلاعات کاربر با موفقیت بروزرسانی شد!");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در بروزرسانی رخ داد: " + e.getMessage());
        }
    }

    private static String getColumn(int num) {
        switch (num) {
            case 1:
                return "Ethereum";
            case 2:
                return "Dogecoin";
            case 3:
                return "Notcoin";
            case 4:
                return "Hamester";
            default:
                showAlert(Alert.AlertType.ERROR, "خطا", "نوع نامعتبر");
                return null;
        }
    }
}
