package ExchangeApp;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Regression {
    public static HashMap<String, Double> getPredict(int typeCoin) {
        List<String[]> data = new ArrayList<>();

        String query = "SELECT time, ethereum, dogecoin, notcoin, hamster FROM token_price";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fumcoin", "root", "");
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

        return processData(data, typeCoin);
    }

    private static HashMap<String, Double> processData(List<String[]> data, int typeCoin) {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date startTime;
        try {
            startTime = sdformat.parse(data.get(0)[0]);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        List<Double> timeData = new ArrayList<>();
        List<Double> ethereumData = new ArrayList<>();
        List<Double> dogecoinData = new ArrayList<>();
        List<Double> notcoinData = new ArrayList<>();
        List<Double> hamsterData = new ArrayList<>();

        for (String[] row : data) {
            try {
                Date date = sdformat.parse(row[0]);
                double timeDiff = (date.getTime() - startTime.getTime()) / 1000.0; // Convert to seconds
                timeData.add(timeDiff);
                ethereumData.add(Double.parseDouble(row[1]));
                dogecoinData.add(Double.parseDouble(row[2]));
                notcoinData.add(Double.parseDouble(row[3]));
                hamsterData.add(Double.parseDouble(row[4]));
            } catch (ParseException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        switch (typeCoin) {
            case 1:
                return predict(timeData, ethereumData, startTime);
            case 2:
                return predict(timeData, dogecoinData, startTime);
            case 3:
                return predict(timeData, notcoinData, startTime);
            case 4:
                return predict(timeData, hamsterData, startTime);
            default:
                System.out.println("Error! Coin Not Found {Regression/processData}");
                return null;
        }
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

        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = outputFormat.format(currentTime);

        HashMap<String, Double> predictionResult = new HashMap<>();
        predictionResult.put(formattedDate, predictValue);

        return predictionResult;
    }
}
