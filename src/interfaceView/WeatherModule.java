package interfaceView;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import widgets.TimeDateManager;



public class WeatherModule {
    private Label day1,day1Temp,day1MaxTemp, day1MinTemp;
    private Label day2,day2Temp,day2MaxTemp, day2MinTemp;
    private Label day3,day3Temp,day3MaxTemp, day3MinTemp;
    private ImageView iconDay1, iconDay2, iconDay3;
    private GridPane weatherForecast;

    public WeatherModule(GridPane weatherForecast){
        this.weatherForecast = weatherForecast;
        Platform.runLater(this::setUp);
    }

    public void setUp(){
        TimeDateManager manager = new TimeDateManager();
        day1 = new Label("Mon");
        day1Temp = new Label("18" + "\u2103");
        day1MaxTemp = new Label("24" + "\u2103");
        day1MinTemp = new Label("12" + "\u2103");
        day1Temp.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 14.0));
        day1MaxTemp.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 13.0));
        day1MinTemp.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 13.0));
        day1.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 13.0));

        day2 = new Label("Tue");
        day2Temp = new Label("18" + "\u2103");
        day2MaxTemp = new Label("23" + "\u2103");
        day2MinTemp = new Label("12" + "\u2103");
        day2Temp.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 13.0));
        day2MaxTemp.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 12.0));
        day2MinTemp.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 12.0));
        day2.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 12.0));

        day3 = new Label("Wed");
        day3Temp = new Label("15" + "\u2103");
        day3MaxTemp = new Label("21" + "\u2103");
        day3MinTemp = new Label("10" + "\u2103");
        day3Temp.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 13.0));
        day3MaxTemp.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 12.0));
        day3MinTemp.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 12.0));
        day3.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 12.0));

        weatherForecast.prefHeight(400);
        weatherForecast.prefHeight(600);
        final int numCols = 1;
        final int numRows = 3;


        for (int i = 0; i < numCols; i++){
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0/numCols);
            weatherForecast.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++){
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0/numRows);
            weatherForecast.getRowConstraints().add(rowConst);
        }

        iconDay1.setFitHeight(110.0);
        iconDay1.setFitWidth(100.0);
        iconDay1.setPickOnBounds(true);
        weatherForecast.add(iconDay1,0,0);
        GridPane.setValignment(iconDay1, VPos.CENTER);
        GridPane.setHalignment(iconDay1, HPos.RIGHT);

        weatherForecast.add(day1Temp,0,0);
        GridPane.setMargin(day1Temp, new Insets(15, 0, 0, 0));
        GridPane.setValignment(day1Temp,VPos.TOP);
        GridPane.setHalignment(day1Temp,HPos.CENTER);

        weatherForecast.add(day1MaxTemp,0,0);
        GridPane.setMargin(day1MaxTemp,new Insets(0,30,30,0));
        GridPane.setValignment(day1MaxTemp,VPos.CENTER);
        GridPane.setHalignment(day1MaxTemp,HPos.CENTER);

        weatherForecast.add(day1MinTemp,0,0);
        GridPane.setMargin(day1MinTemp,new Insets(0,30,0,30));
        GridPane.setValignment(day1MinTemp,VPos.CENTER);
        GridPane.setHalignment(day1MinTemp,HPos.CENTER);

        weatherForecast.add(day1,0,0);
        GridPane.setHalignment(day1,HPos.CENTER);
        GridPane.setValignment(day1,VPos.CENTER);


        iconDay2.setFitHeight(80.0);
        iconDay2.setFitWidth(80.0);
        iconDay2.setPickOnBounds(true);

        weatherForecast.add(iconDay2,0,1);
        GridPane.setValignment(iconDay2, VPos.CENTER);
        GridPane.setHalignment(iconDay2, HPos.RIGHT);

        weatherForecast.add(day2Temp,0,1);
        GridPane.setMargin(day2Temp, new Insets(30, 0, 0, 0));
        GridPane.setValignment(day2Temp,VPos.TOP);
        GridPane.setHalignment(day2Temp,HPos.CENTER);

        weatherForecast.add(day2MaxTemp,0,1);
        GridPane.setMargin(day2MaxTemp,new Insets(0,30,0,0));
        GridPane.setValignment(day2MaxTemp,VPos.CENTER);
        GridPane.setHalignment(day2MaxTemp,HPos.CENTER);

        weatherForecast.add(day2MinTemp,0,1);
        GridPane.setMargin(day2MinTemp,new Insets(0,0,0,30));
        GridPane.setValignment(day2MinTemp,VPos.CENTER);
        GridPane.setHalignment(day2MinTemp,HPos.CENTER);

        weatherForecast.add(day2,0,1);
        GridPane.setMargin(day2,new Insets(30,0,0,0));
        GridPane.setHalignment(day2,HPos.CENTER);
        GridPane.setValignment(day2,VPos.CENTER);


        iconDay3.setFitHeight(80.0);
        iconDay3.setFitWidth(80.0);
        iconDay3.setPickOnBounds(true);

        weatherForecast.add(iconDay3,0,2);
        GridPane.setValignment(iconDay3, VPos.CENTER);
        GridPane.setHalignment(iconDay3, HPos.RIGHT);

        weatherForecast.add(day3Temp,0,2);
        GridPane.setMargin(day3Temp, new Insets(30, 0, 0, 0));
        GridPane.setValignment(day3Temp,VPos.TOP);
        GridPane.setHalignment(day3Temp,HPos.CENTER);

        weatherForecast.add(day3MaxTemp,0,2);
        GridPane.setMargin(day3MaxTemp,new Insets(0,30,0,0));
        GridPane.setValignment(day3MaxTemp,VPos.CENTER);
        GridPane.setHalignment(day3MaxTemp,HPos.CENTER);

        weatherForecast.add(day3MinTemp,0,2);
        GridPane.setMargin(day3MinTemp,new Insets(0,0,0,30));
        GridPane.setValignment(day3MinTemp,VPos.CENTER);
        GridPane.setHalignment(day3MinTemp,HPos.CENTER);

        weatherForecast.add(day3,0,2);
        GridPane.setMargin(day3,new Insets(30,0,0,0));
        GridPane.setHalignment(day3,HPos.CENTER);
        GridPane.setValignment(day3,VPos.CENTER);


    }




}
