package controllers;


import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import widgets.JSONWeatherParser;
import widgets.WeatherFetcher;

public class TemperatureController {
    private final String queryParameterForecast = "/daily";
    public GridPane temperatureView;

    public Label forecast1;
    public Label maxForecast1;
    public Label minForecast1;
    public Label dayNameForecast1;

    public Label inTemp;

    public ImageView imgTodayTemp;
    public ImageView imgForecast1;
    public ImageView imgForecast2;

    public Label todayTem;
    public Label minToday;
    public Label maxToday;
    public Label dayNameToday;

    public Label forecast2;
    public Label minForecast2;
    public Label maxForecast2;
    public Label dayNameForecast2;

    private WeatherFetcher weatherFetcher;
    private JSONWeatherParser weatherParser;

    public TemperatureController() {
        this.weatherFetcher = new WeatherFetcher();
        this.weatherParser = new JSONWeatherParser();
        this.weatherFetcher.addObserver(this.weatherParser);
        Platform.runLater(() -> this.updateWeather("gothenburg,se"));
    }

    public void updateWeather(String town) {
        weatherFetcher.fetchWeather(queryParameterForecast, town);
        weatherFetcher.fetchWeather("", town);

        this.todayTem.setText(String.valueOf(weatherParser.getWeather(0).getTemp()) + "°");
        this.minToday.setText(String.valueOf(weatherParser.getWeather(0).getMinTemp()) + "°");
        this.maxToday.setText(String.valueOf(weatherParser.getWeather(0).getMaxTemp()) + "°");
        this.dayNameToday.setText(weatherParser.getWeather(0).getDayName());
        this.imgTodayTemp.setImage(weatherFetcher.getImage(weatherParser.getWeather(0).getIcon()));

        this.forecast1.setText(String.valueOf(weatherParser.getWeather(1).getTemp()) + "°");
        this.minForecast1.setText(String.valueOf(weatherParser.getWeather(1).getMinTemp()) + "°");
        this.maxForecast1.setText(String.valueOf(weatherParser.getWeather(1).getMaxTemp()) + "°");
        this.dayNameForecast1.setText(weatherParser.getWeather(1).getDayName());
        this.imgForecast1.setImage(weatherFetcher.getImage(weatherParser.getWeather(1).getIcon()));

        this.forecast2.setText(String.valueOf(weatherParser.getWeather(2).getTemp()) + "°");
        this.minForecast2.setText(String.valueOf(weatherParser.getWeather(2).getMinTemp()) + "°");
        this.maxForecast2.setText(String.valueOf(weatherParser.getWeather(2).getMaxTemp()) + "°");
        this.dayNameForecast2.setText(weatherParser.getWeather(2).getDayName());
        this.imgForecast2.setImage(weatherFetcher.getImage(weatherParser.getWeather(2).getIcon()));
    }
}
