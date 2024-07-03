package ExchangeApp;

<<<<<<< HEAD
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import javax.xml.transform.Result;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class Token extends Menu implements Initializable {

    @FXML
    private LineChart chart;
    @FXML
    private ToggleGroup timePeriodGroup;

    private final String DB_URL = "jdbc:mysql://localhost:3306/fumcoin";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";
    private String query = "SELECT * FROM token_price ORDER BY time ASC LIMIT 60";
    private List<XYChart.Data<String ,Double>> dataList = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        // Fix DrawChart Parameter AmirAli :)
        DrawChart("ethereum"); // ethereum is example
    }

    private int getChartStatus(){
        String periodtype = ((RadioButton) timePeriodGroup.getSelectedToggle()).getUserData().toString();

        switch (periodtype){
            case "minute":query = "SELECT * FROM token_price ORDER BY time DESC LIMIT 60"; return 1;
            case "hour":query = "SELECT * FROM token_price ORDER BY time DESC LIMIT 1440"; return 2 ;
            case "day" :query = "SELECT * FROM token_price ORDER BY time DESC LIMIT 1440"; return 3;
            case "week" : query = "SELECT * FROM token_price ORDER BY time DESC LIMIT 1400";return 4;
            case "month" : query = "SELECT * FROM token_price ORDER BY time DESC LIMIT 1440";return 5;
            case "year" : query = "SELECT * FROM token_price ORDER BY time DESC LIMIT 1440";return 6;
            default:query = "SELECT * FROM token_price ORDER BY time ASC LIMIT 60"; return 1;
        }

    }

    private void DrawChart(String typeCoin ) {

        try(Connection con = DriverManager.getConnection(DB_URL); Statement stmt = con.createStatement()) {

            getChartStatus();

            ResultSet result = stmt.executeQuery(query);

            XYChart.Series<String ,Double> series = new XYChart.Series<>();
            CategoryAxis timeAxis = new CategoryAxis();
            NumberAxis priceAxis = new NumberAxis();

            chart = new LineChart<>(timeAxis,priceAxis);

            while(result.next()) {

                String timeLable = getTimeLable(result,getChartStatus());
                dataList.add( new XYChart.Data<>(timeLable,
                        result.getDouble(typeCoin)));
            }
            Collections.reverse(dataList);

            chart.getData().add(getSeries(getChartStatus()));

            con.close();
            stmt.close();
            result.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private XYChart.Series<String , Double> getSeries( int  status){

        XYChart.Series<String , Double> series = new XYChart.Series<>();

        int groupSize = 1;
        int dataSize = dataList.size();
        switch (status){
            case 1: groupSize = 1; break;
            case 2: groupSize = 60;break;
            case 3: groupSize = 288;break;
            case 4: groupSize = 200;break;
            case 5: groupSize = 48;break;
            case 6: groupSize = 144;break;
            default: groupSize = 1;break;
        }

        for (int i = 0 ; i < dataSize ;i += groupSize){
            int end = Math.min(i + groupSize,dataSize);
            double sum = 0;
            for (int j = i ; j < end ; j++){
                sum += dataList.get(j).getYValue();
            }

            double avg = sum / (end - i);
            series.getData().add(new XYChart.Data<>(dataList.get((end+i)/2).getXValue(),avg));
        }
        return series;
    }

    private String getTimeLable(ResultSet resultSet , int chartStatus) throws SQLException {

        switch (chartStatus){
            case 1: return resultSet.getString("time").split(" ")[1].split(":")[1];
            case 2: return resultSet.getString("time").split(" ")[1].split(":")[0];
            case 3: return resultSet.getString("time").split(" ")[0];
            case 4: return resultSet.getString("time").split(" ")[0].split(":")[0];
            case 5: return resultSet.getString("time").split(" ")[1].split(":")[1];
            case 6: return resultSet.getString("time").split(" ")[0];
            default: return resultSet.getString("time").split(" ")[1].split(":")[1];
        }
=======
import java.net.URL;
import java.util.ResourceBundle;

public class Token extends Menu {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
>>>>>>> bc00b1e066f3aa6b403679c1840dc9996313345e
    }
}
