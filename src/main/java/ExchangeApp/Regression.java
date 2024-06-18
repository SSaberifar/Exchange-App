package ExchangeApp;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Regression {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/fumcoin";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static HashMap<String, Double> getPredict(int typeCoin) {
        List<String[]> data = fetchDataFromDatabase();
        return processData(data, typeCoin);
    }

    private static List<String[]> fetchDataFromDatabase() {
        List<String[]> data = new ArrayList<>();
        String query = "SELECT time, ethereum, dogecoin, notcoin, hamster FROM token_price";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String[] row = new String[5];
                row[0] = rs.getString("time");
                row[1] = rs.getString("ethereum");
                row[2] = rs.getString("dogecoin");
                row[3] = rs.getString("notcoin");
                row[4] = rs.getString("hamster");
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    private static HashMap<String, Double> processData(List<String[]> data, int typeCoin) {
        Date startTime;
        try {
            startTime = DATE_FORMAT.parse(data.get(0)[0]);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        List<Double> timeData = new ArrayList<>();
        List<Double> selectedCoinData = new ArrayList<>();

        for (String[] row : data) {
            try {
                Date date = DATE_FORMAT.parse(row[0]);
                double timeDiff = (date.getTime() - startTime.getTime()) / 1000.0; // Convert to seconds
                timeData.add(timeDiff);

                switch (typeCoin) {
                    case 1:
                        selectedCoinData.add(Double.parseDouble(row[1]));
                        break;
                    case 2:
                        selectedCoinData.add(Double.parseDouble(row[2]));
                        break;
                    case 3:
                        selectedCoinData.add(Double.parseDouble(row[3]));
                        break;
                    case 4:
                        selectedCoinData.add(Double.parseDouble(row[4]));
                        break;
                    default:
                        System.out.println("Error! Coin Not Found {Regression/processData}");
                        return null;
                }
            } catch (ParseException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return predict(timeData, selectedCoinData, startTime);
    }

    private static HashMap<String, Double> predict(List<Double> timeData, List<Double> yData, Date startTime) {
        double[][] xData = new double[timeData.size()][1];
        double[] timeArray = timeData.stream().mapToDouble(Double::doubleValue).toArray();
        double[] yArray = yData.stream().mapToDouble(Double::doubleValue).toArray();

        for (int i = 0; i < timeArray.length; i++) {
            xData[i][0] = timeArray[i];
        }

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(yArray, xData);

        double[] coefficients = regression.estimateRegressionParameters();

        Date currentTime = new Date();
        double newTime = (currentTime.getTime() - startTime.getTime()) / 1000.0; // Convert to seconds

        double predictValue = coefficients[0] + coefficients[1] * newTime;

        String formattedDate = DATE_FORMAT.format(currentTime);

        HashMap<String, Double> predictionResult = new HashMap<>();
        predictionResult.put(formattedDate, predictValue);

        return predictionResult;
    }
}
