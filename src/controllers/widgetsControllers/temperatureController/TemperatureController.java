package controllers.widgetsControllers.temperatureController;

import dataModels.applicationModels.Preferences;
import dataModels.widgetsModels.weatherModels.Town;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import dataHandlers.widgetsDataHandlers.weather.JSONWeatherParser;
import dataHandlers.widgetsDataHandlers.weather.WeatherFetcher;
import javafx.scene.layout.StackPane;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class TemperatureController implements Observer {
    public GridPane temperatureView;

    public Label inTemp;

    public ImageView imgTodayTemp;
    public ImageView imgForecast1;
    public ImageView imgForecast2;

    public Label todayTem;
    public Label minToday;
    public Label maxToday;
    public Label dayNameToday;

    public Label forecast1;
    public Label maxForecast1;
    public Label minForecast1;
    public Label dayNameForecast1;

    public Label forecast2;
    public Label minForecast2;
    public Label maxForecast2;
    public Label dayNameForecast2;

    public Label town;

    private WeatherFetcher weatherFetcher;
    private JSONWeatherParser weatherParser;

    private String townName;

    public TemperatureController() {
        this.weatherFetcher = new WeatherFetcher();
        this.weatherParser = new JSONWeatherParser();
        this.weatherFetcher.addObserver(this.weatherParser);
    }

    private void updateWeather(String town) {
        this.townName = town;
        Thread thread = new Thread(() -> Platform.runLater(() -> {
            try {
                this.updateView(URLEncoder.encode(town, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }));
        thread.start();
    }

    public void setInTemp(String temp) {
        this.inTemp.setText(temp + "°");
    }

    private void updateView(String town) {
        String queryParameterForecast = "forecast/daily";
        weatherFetcher.fetchWeather(queryParameterForecast, town);
        weatherFetcher.fetchWeather("weather", town);

        this.town.setText(town.substring(0, town.indexOf("%")));

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

    private synchronized void setVisible(boolean b) {
        Platform.runLater(() -> {
            this.temperatureView.setVisible(b);
            StackPane parentPane = (StackPane) this.temperatureView.getParent();
            GridPane parentGrid = (GridPane) parentPane.getParent();

            monitorWidgetVisibility(parentPane, parentGrid);
        });
        if (b && this.townName != null) {
            updateWeather(this.townName);
        }
    }

    private synchronized void monitorWidgetVisibility(StackPane stackPane, GridPane gridPane) {
        boolean visible = false;
        ObservableList<Node> list = stackPane.getChildren();
        for (Node node : list) {
            visible = node.isVisible();
        }
        gridPane.setVisible(visible);
    }


    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object arg) {
        if (arg.equals("Update Weather and News") && this.temperatureView.isVisible()
                && this.townName != null) {
            updateWeather(this.townName);
        } else if (arg instanceof Town) {
            Thread thread = new Thread(() -> {
                Town town = (Town) arg;
                updateWeather(town.getTown());
            });
            thread.start();

        } else if (arg instanceof LinkedList && ((LinkedList) arg).peek() instanceof Preferences) {
            Thread thread = new Thread(() -> {
                LinkedList<Preferences> preferences = (LinkedList) arg;
                preferences.stream().filter(pref -> pref.getName().equals("weather")).forEachOrdered(pref ->
                        setVisible(pref.getValue().equals("true")));
            });
            thread.start();
        }
    }
}
