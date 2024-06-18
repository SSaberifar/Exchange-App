package ExchangeApp;

import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/fumcoin";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private static boolean userExists(PreparedStatement checkStmt, String uname, String uemail, String uphone) throws SQLException {
        checkStmt.setString(1, uname);
        checkStmt.setString(2, uemail);
        checkStmt.setString(3, uphone);
        try (ResultSet result = checkStmt.executeQuery()) {
            return result.next();
        }
    }

    public static void SignUpDB(ActionEvent event, String fname, String lname, String uname, String upass, String uemail, String uphone, String imgPath) throws IOException {
        String checkSQL = "SELECT * FROM users WHERE user_name = ? OR email = ? OR phone_number = ?";
        String insertSQL = "INSERT INTO users (first_name, last_name, user_name, password, email, phone_number, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(checkSQL);
             PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {

            if (userExists(checkStmt, uname, uemail, uphone)) {
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

        try (Connection connection = getConnection();
             PreparedStatement psmt = connection.prepareStatement(selectSQL)) {

            psmt.setString(1, uname);
            psmt.setString(2, upass);
            try (ResultSet result = psmt.executeQuery()) {
                if (result.next()) {
                    User.user = new User(
                            result.getString("user_name"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getString("password"),
                            result.getString("email"),
                            result.getString("phone_number"),
                            result.getString("imagePath"),
                            result.getDouble("profit(USD)"),
                            result.getDouble("Ethereum"),
                            result.getDouble("Dogecoin"),
                            result.getDouble("Notcoin"),
                            result.getDouble("Hamester")
                    );
                    showAlert(Alert.AlertType.INFORMATION, "ورود کاربر", "با موفقیت وارد شدید!");
                    Main.stageChanger(event, "Profile.fxml");
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "ورود کاربر", "رمز عبور اشتباه است یا کاربری با این نام وجود ندارد!");
                }
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
            try (ResultSet result = psmt.executeQuery()) {
                if (result.next()) {
                    User.user = new User(
                            result.getString("user_name"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getString("password"),
                            result.getString("email"),
                            result.getString("phone_number"),
                            result.getString("imagePath"),
                            result.getDouble("profit(USD)"),
                            result.getDouble("Ethereum"),
                            result.getDouble("Dogecoin"),
                            result.getDouble("Notcoin"),
                            result.getDouble("Hamester")
                    );
                    Main.stageChanger(event, "Profile.fxml");
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "ورود کاربر", "کاربری با این ایمیل حساب کاربری ندارد!");
                }
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در ورود رخ داد: " + e.getMessage());
        }
    }

    public static Double lastValue(String type) {
        Double value = 0.0;
        String query = "SELECT " + type + " FROM token_price LIMIT 1 OFFSET 1439";

        try (Connection connection = getConnection();
             PreparedStatement psmt = connection.prepareStatement(query);
             ResultSet result = psmt.executeQuery()) {
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

        try (Connection connection = getConnection();
             PreparedStatement psmt = connection.prepareStatement(query);
             ResultSet result = psmt.executeQuery()) {
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

        try (Connection connection = getConnection();
             PreparedStatement psmt = connection.prepareStatement(query);
             ResultSet result = psmt.executeQuery()) {
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
        String column = getColumn(num);
        if (column == null) return value;

        String queryFirstRow = "SELECT " + column + " FROM token_price ORDER BY time LIMIT 1";
        String queryLastRow = "SELECT " + column + " FROM token_price LIMIT 1 OFFSET 1439";

        try (Connection connection = getConnection();
             PreparedStatement stmtFirstRow = connection.prepareStatement(queryFirstRow);
             PreparedStatement stmtLastRow = connection.prepareStatement(queryLastRow);
             ResultSet resultFirstRow = stmtFirstRow.executeQuery();
             ResultSet resultLastRow = stmtLastRow.executeQuery()) {

            Double firstRow = resultFirstRow.next() ? resultFirstRow.getDouble(column) : 0.0;
            Double lastRow = resultLastRow.next() ? resultLastRow.getDouble(column) : 0.0;

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

    public static void update(String desUser, String token, double value) {
        String query = "UPDATE users SET " + token + " = " + token + " + ? WHERE user_name = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, value);
            stmt.setString(2, desUser);
            stmt.executeUpdate();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "خطا در آپدیت اطلاعات: " + e.getMessage());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "مقدار وارد شده معتبر نمی‌باشد: " + e.getMessage());
        }
    }

    public static void exitUpdate() {
        String query = "UPDATE users SET `profit(USD)` = ?, Ethereum = ?, Dogecoin = ?, Notcoin = ?, Hamester = ? WHERE user_name = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, User.user.getpD());
            stmt.setDouble(2, User.user.getEth());
            stmt.setDouble(3, User.user.getDog());
            stmt.setDouble(4, User.user.getNot());
            stmt.setDouble(5, User.user.getHam());
            stmt.setString(6, User.user.getUserShow());
            stmt.executeUpdate();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "خطا در آپدیت اطلاعات: " + e.getMessage());
        }
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

    public static void saveBills(String status, String type, String sender, String token, double amount, double value) {
        String insertSQL = "INSERT INTO bills (status, type, sender, token, amount, value) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
            insertStmt.setString(1, status);
            insertStmt.setString(2, type);
            insertStmt.setString(3, sender);
            insertStmt.setString(4, token);
            insertStmt.setDouble(5, amount);
            insertStmt.setDouble(6, value);
            insertStmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "خرید/فروش ارز", "سفارش شما با موفقیت ثبت گردید!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در عملیات رخ داد: " + e.getMessage());
        }
    }

    public static List<Object[]> showBills(String par) {
        List<Object[]> bills = new ArrayList<>();
        String selectSQL;
        if (par.equals(User.user.getUserShow())) {
            selectSQL = "SELECT * FROM bills WHERE sender = ?";
            try (Connection connection = getConnection();
                 PreparedStatement psmt = connection.prepareStatement(selectSQL)) {
                psmt.setString(1, par);
                try (ResultSet result = psmt.executeQuery()) {
                    while (result.next()) {
                        Object[] record = new Object[5];
                        record[0] = result.getString("status");
                        record[1] = result.getString("type");
                        record[2] = result.getString("token");
                        record[3] = result.getDouble("amount");
                        record[4] = result.getDouble("value");
                        bills.add(record);
                    }
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در اطلاعات رخ داد: " + e.getMessage());
            }
        } else {
            selectSQL = "SELECT * FROM bills WHERE token = ?";
            try (Connection connection = getConnection();
                 PreparedStatement psmt = connection.prepareStatement(selectSQL)) {
                psmt.setString(1, par);
                try (ResultSet result = psmt.executeQuery()) {
                    while (result.next()) {
                        Object[] record = new Object[3];
                        record[0] = result.getString("type");
                        record[1] = result.getDouble("amount");
                        record[2] = result.getDouble("value");
                        bills.add(record);
                    }
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "خطا", "خطایی در اطلاعات رخ داد: " + e.getMessage());
            }
        }
        return bills;
    }
}
