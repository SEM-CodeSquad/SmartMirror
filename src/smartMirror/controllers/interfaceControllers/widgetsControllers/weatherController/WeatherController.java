/*
 * Copyright 2016 CodeHigh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2016 CodeHigh.
 *     Permission is granted to copy, distribute and/or modify this document
 *     under the terms of the GNU Free Documentation License, Version 1.3
 *     or any later version published by the Free Software Foundation;
 *     with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *     A copy of the license is included in the section entitled "GNU
 *     Free Documentation License".
 */

package smartMirror.controllers.interfaceControllers.widgetsControllers.weatherController;

import smartMirror.controllers.dataHandlers.dataHandlersCommons.JsonMessageParser;
import smartMirror.dataModels.modelCommons.SmartMirror_Publisher;
import smartMirror.controllers.dataHandlers.dataHandlersCommons.TimeNotificationControl;
import smartMirror.dataModels.modelCommons.MQTTClient;
import smartMirror.controllers.dataHandlers.widgetsDataHandlers.weather.JSONWeatherParser;
import smartMirror.controllers.dataHandlers.widgetsDataHandlers.weather.WeatherFetcher;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.applicationModels.Settings;
import smartMirror.dataModels.widgetsModels.weatherModels.Town;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author CodeHigh @copyright on 06/12/2016.
 *         Class responsible for updating the WeatherView
 */
public class WeatherController extends Observable implements Observer
{
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

    private SmartMirror_Publisher publisher;

    private boolean visible = false;

    /**
     * Constructor initializes the WeatherFetcher and the WeatherParser, then it starts the time monitoring
     * @see WeatherFetcher
     * @see JSONWeatherParser
     * @see TimeNotificationControl
     */
    public WeatherController()
    {
        Platform.runLater(() ->
                this.temperatureView.visibleProperty().addListener((observableValue, aBoolean, aBoolean2) ->
                        visible = temperatureView.isVisible()));

        this.weatherFetcher = new WeatherFetcher();
        this.weatherParser = new JSONWeatherParser();
        this.weatherFetcher.addObserver(this.weatherParser);
        TimeNotificationControl notificationControl = new TimeNotificationControl();
        notificationControl.addObserver(this);
        notificationControl.bind("HH:mm:ss", 3600, "weather");
    }

    /**
     * Method responsible for updating the weather for the desired town
     * @param town the name of the tow to fetch the weather
     * @see WeatherFetcher
     * @see JSONWeatherParser
     */
    private synchronized void updateWeather(String town)
    {
        this.townName = town;
        Platform.runLater(() ->
        {
            try
            {
                String queryParameterForecast = "forecast/daily";
                weatherFetcher.fetchWeather(queryParameterForecast, town);
                weatherFetcher.fetchWeather("weather", town);

                this.town.setText(town.substring(0, town.indexOf(",")));

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

                setChanged();
                notifyObservers(weatherParser.getWeather(0));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }

    // TODO: 06/12/2016
//    public synchronized void setInTemp(String temp)
//    {
//        this.inTemp.setText(temp + "°");
//    }

    /**
     * Method responsible for setting the widget holder visible
     */
    private synchronized void setParentVisible()
    {
        Platform.runLater(() ->
        {
            GridPane gridPane = (GridPane) this.temperatureView.getParent().getParent();
            if (gridPane.getOpacity() != 1)
            {
                gridPane.setVisible(true);
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), gridPane);

                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();
            }
        });
    }

    /**
     * Method responsible for setting this widget visible
     *
     * @param b true for visible false for not visible
     */
    private synchronized void setVisible(boolean b)
    {
        Platform.runLater(() ->
        {
            this.temperatureView.setVisible(b);
            StackPane parentPane = (StackPane) this.temperatureView.getParent();
            GridPane parentGrid = (GridPane) parentPane.getParent();

            monitorWidgetVisibility(b, parentGrid);
        });
        if (b && this.townName != null)
        {
            updateWeather(this.townName);
        }
    }

    /**
     * Method responsible for setting the parent visibility. In case of all the widgets in the parent are not visible
     * the parent also shall be not visible and vice-versa
     *
     * @param b boolean
     * @param gridPane  parent parent component
     */
    private synchronized void monitorWidgetVisibility(boolean b, GridPane gridPane)
    {
        gridPane.setVisible(b);
    }

    /**
     * Method responsible to ensure that only one widget is showing at the time in the parent
     */
    private void enforceView()
    {
        if (!visible)
        {
            StackPane sPane = (StackPane) this.temperatureView.getParent();

            ObservableList<Node> list = sPane.getChildren();
            for (Node node : list)
            {
                node.setVisible(false);
            }

            this.temperatureView.setVisible(true);
        }
    }

    /**
     * Update method where the observable classes sends notifications messages
     *
     * @param o   observable object
     * @param arg object arg
     */
    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object arg)
    {
        if (arg.equals("Update Weather and News") && this.temperatureView.isVisible()
                && this.townName != null)
        {
            updateWeather(this.townName);
        }
        else if (arg instanceof MqttMessage)
        {
            Thread thread = new Thread(() ->
            {
                JsonMessageParser parser = new JsonMessageParser();
                parser.parseMessage(arg.toString());

                if (parser.getContentType().equals("settings"))
                {

                    Settings settings = parser.parseSettings();
                    if (settings.getObject() instanceof Town)
                    {
                        setParentVisible();
                        enforceView();
                        updateWeather(((Town) settings.getObject()).getTown());
                        publisher.echo("Town name for the weather received");
                    }
                }
                else if (parser.getContentType().equals("preferences"))
                {
                    LinkedList<Preferences> list = parser.parsePreferenceList();

                    list.stream().filter(pref -> pref.getName().equals("weather")).forEach(pref ->
                            setVisible(pref.getValue().equals("true")));
                    publisher.echo("Weather preference changed");
                }
            });
            thread.start();

        }
        else if (arg instanceof MQTTClient)
        {
            MQTTClient mqttClient = (MQTTClient) arg;
            this.publisher = new SmartMirror_Publisher(mqttClient);
        }
    }
}
