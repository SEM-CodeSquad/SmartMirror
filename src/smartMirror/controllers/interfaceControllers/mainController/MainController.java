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

package smartMirror.controllers.interfaceControllers.mainController;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import smartMirror.controllers.communications.CommunicationManager;
import smartMirror.controllers.dataHandlers.dataHandlersCommons.JsonMessageParser;
import smartMirror.controllers.dataHandlers.widgetsDataHandlers.timeDate.TimeDateManager;
import smartMirror.controllers.interfaceControllers.widgetsControllers.busTimetableController.BusTimetableController;
import smartMirror.controllers.interfaceControllers.widgetsControllers.devicesController.DeviceController;
import smartMirror.controllers.interfaceControllers.widgetsControllers.feedController.FeedController;
import smartMirror.controllers.interfaceControllers.widgetsControllers.greetingsController.GreetingsController;
import smartMirror.controllers.interfaceControllers.widgetsControllers.postItsController.PostItViewController;
import smartMirror.controllers.interfaceControllers.widgetsControllers.qrCodeController.QRCodeController;
import smartMirror.controllers.interfaceControllers.widgetsControllers.shoppingListController.ShoppingListViewController;
import smartMirror.controllers.interfaceControllers.widgetsControllers.timeDateController.TimeDateController;
import smartMirror.controllers.interfaceControllers.widgetsControllers.weatherController.WeatherController;
import smartMirror.dataModels.applicationModels.UUID_Generator;
import smartMirror.dataModels.widgetsModels.qrCodeModels.QRCode;
import smartMirror.dataModels.widgetsModels.weatherModels.Weather;

import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Codehigh @copyright on 06/12/2016.
 *         Class responsible for loading each application component and starting the CommunicationManager
 */
public class MainController extends Observable implements Observer
{
    public BorderPane pairingPane;
    public GridPane pairingDateTimeContainer;
    public GridPane qrPairingContainer;

    public AnchorPane mainPane;
    public GridPane widget1;
    public GridPane widget7;
    public GridPane widget6;
    public GridPane widget3;
    public GridPane gridMainQR;
    public GridPane widget2;
    public GridPane widget5;
    public GridPane widget4;

    public StackPane stackPaneWidget1;
    public StackPane stackPaneWidget7;
    public StackPane stackPaneWidget6;
    public StackPane stackPaneWidget3;
    public StackPane stackPaneWidgetQR;
    public StackPane stackPaneWidget2;
    public StackPane stackPaneWidget5;
    public StackPane stackPaneWidget4;

    private boolean systemRunning;
    private boolean paired;
    private UUID_Generator uuid;

    private TimeDateManager timeDateManager;
    private CommunicationManager communicationManager;

    /**
     * Constructor which generates a UUID (client ID), starts the CommunicationManger with this UUID and waits for
     * updates from the CommunicationManager
     */
    public MainController()
    {
        this.systemRunning = true;
        this.paired = false;
        keepScreenAlive();
        this.uuid = new UUID_Generator();
        this.communicationManager = new CommunicationManager(this.uuid.getUUID());
        this.communicationManager.addObserver(this);
        this.addObserver(this.communicationManager);
        startUp();
    }

    /**
     * This method makes the call to load every component into the Interface
     */
    private void startUp()
    {
        Platform.runLater(() ->
        {
            setUpDateTimeView();
            setUpQRCodeView();
            setUpWeatherView();
            setUpDevicesView();
            setUpFeedView();
            setUpBusTimetableView();
            setUpPostItView();
            setUpGreetingsView();
            setUpShoppingListView();

            this.pairingPane.setOpacity(1);

            setChanged();
            notifyObservers(this);
        });
    }

    /**
     * This method loads the DevicesView in the interface and adds its controller as observer to the CommunicationManager
     *
     * @see CommunicationManager
     * @see DeviceController
     */
    private void setUpDevicesView()
    {
        DeviceController deviceController = loadViewMainScreen(this.stackPaneWidget7, "/smartMirror/Views/widgetsViews/deviceStatusWidget/DeviceView.fxml").getController();
        this.communicationManager.addObserver(deviceController);
    }

    /**
     * This method loads the ShoppingListView in the interface and adds its controller as observer to the CommunicationManager
     *
     * @see CommunicationManager
     * @see ShoppingListViewController
     */
    private void setUpShoppingListView()
    {
        ShoppingListViewController shoppingListController = loadViewMainScreen(this.stackPaneWidget3, "/smartMirror/Views/widgetsViews/shoppingListWidget/ShoppingListView.fxml").getController();
        this.communicationManager.addObserver(shoppingListController);
    }

    /**
     * This method loads the PostItView in the interface and adds its controller as observer to the CommunicationManager
     *
     * @see CommunicationManager
     * @see PostItViewController
     */
    private void setUpPostItView()
    {
        PostItViewController postItViewController = loadViewMainScreen(this.stackPaneWidget6, "/smartMirror/Views/widgetsViews/postItWidget/PostitView.fxml").getController();
        this.communicationManager.addObserver(postItViewController);

    }

    /**
     * This method loads the FeedView in the interface and adds its controller as observer to the CommunicationManager
     *
     * @see CommunicationManager
     * @see FeedController
     */
    private void setUpFeedView()
    {
        FeedController feedController = loadViewMainScreen(this.stackPaneWidget2, "/smartMirror/Views/widgetsViews/feedsWidget/FeedsViews.fxml").getController();
        this.communicationManager.addObserver(feedController);

    }

    /**
     * This method loads the WeatherView in the interface and adds its controller as observer to the CommunicationManager
     *
     * @see CommunicationManager
     * @see WeatherController
     */
    private void setUpWeatherView()
    {
        WeatherController temperatureController = loadViewMainScreen(this.stackPaneWidget4, "/smartMirror/Views/widgetsViews/weatherWidget/WeatherView.fxml").getController();
        this.communicationManager.addObserver(temperatureController);
        temperatureController.addObserver(this);
    }

    /**
     * This method loads the BusTimetableView in the interface and adds its controller as observer to the CommunicationManager
     *
     * @see CommunicationManager
     * @see BusTimetableController
     */
    private void setUpBusTimetableView()
    {
        BusTimetableController busTimetableController = loadViewMainScreen(this.stackPaneWidget3, "/smartMirror/Views/widgetsViews/busTimetableWidget/BusTimetable.fxml").getController();
        this.communicationManager.addObserver(busTimetableController);
    }

    /**
     * This method loads the QRCodeView in the pairing interface and main interface then adds its controller as observer to the QRCode
     *
     * @see QRCode
     * @see QRCodeController
     */
    private void setUpQRCodeView()
    {
        QRCode qrCode = new QRCode(this.uuid.getUUID());

        qrCode.addObserver(loadViewPairingScreen(this.qrPairingContainer, "/smartMirror/Views/widgetsViews/qrCode/QRCodeView.fxml",
                1, 0).getController());
        setComponentVisible(this.qrPairingContainer);
        qrCode.addObserver(loadViewMainScreen(this.stackPaneWidgetQR, "/smartMirror/Views/widgetsViews/qrCode/QRCodeView.fxml").getController());
        setComponentVisible(this.gridMainQR);
        qrCode.getQRCode();


    }

    /**
     * This method loads the DateTimeView in the pairing interface and main interface then adds its controller as observer to the TimeDateManager
     *
     * @see TimeDateManager
     * @see TimeDateController
     */
    private void setUpDateTimeView()
    {
        this.timeDateManager = new TimeDateManager();
        this.timeDateManager.bindToTime();
        this.timeDateManager.bindToDate();
        this.timeDateManager.bindToDay();
        this.timeDateManager.bindGreetings();

        this.timeDateManager.addObserver(loadViewPairingScreen(this.pairingDateTimeContainer, "/smartMirror/Views/widgetsViews/timeDateWidget/TimeDate.fxml",
                0, 0).getController());
        setComponentVisible(this.pairingDateTimeContainer);
        TimeDateController timeDateController = loadViewMainScreen(this.stackPaneWidget1, "/smartMirror/Views/widgetsViews/timeDateWidget/TimeDate.fxml").getController();
        this.timeDateManager.addObserver(timeDateController);
        this.communicationManager.addObserver(timeDateController);
    }

    /**
     * This method loads the GreetingsView in the interface then adds its controller as observer to the TimeDateManager
     *
     * @see TimeDateManager
     * @see GreetingsController
     */
    private void setUpGreetingsView()
    {
        GreetingsController greetingsController = loadViewMainScreen(stackPaneWidget5, "/smartMirror/Views/widgetsViews/greetingsWidget/GreetingsView.fxml").getController();
        timeDateManager.addObserver(greetingsController);
        this.communicationManager.addObserver(greetingsController);
        this.addObserver(greetingsController);
    }

    /**
     * This method makes the transition between the paring interface and the main interface
     */
    private void changeScene()
    {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), this.pairingPane);

        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), this.mainPane);

        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        fadeOut.setOnFinished(event -> fadeIn.play());
        fadeOut.play();

    }

    /**
     * This method sets a component to visible by changing its opacity value from 0 to 1. This process is made as an animation
     * to give a nice effect in the interface
     *
     * @param gridPane component to be set to visible
     */
    private synchronized void setComponentVisible(GridPane gridPane)
    {
        Platform.runLater(() ->
        {
            gridPane.setVisible(true);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), gridPane);

            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
    }

    /**
     * This method is responsible to keep the screen alive. The screen saver from the running machine can switch off the display in
     * case of inactivate, with this method the pointer will be moved every 80000 milliseconds to avoid screen blackout
     */
    private void keepScreenAlive()
    {
        Thread thread = new Thread(() ->
        {
            try
            {
                Robot robotStart = new Robot();
                robotStart.mouseMove(0, 0);
                while (systemRunning)
                {

                    Thread.sleep(80000);//this is how long before it moves
                    Point point = MouseInfo.getPointerInfo().getLocation();
                    Robot robot = new Robot();
                    robot.mouseMove(point.x, point.y);
                }
            }
            catch (InterruptedException | AWTException e)
            {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    /**
     * Method responsible for loading components in the pairing interface. It loads the FXML file and set in the specified
     * GridPane in the specified position
     *
     * @param parent   parent component
     * @param resource FXML path resource
     * @param c        column number
     * @param r        row number
     * @return FXMLLoader
     */
    private FXMLLoader loadViewPairingScreen(GridPane parent, String resource, int c, int r)
    {
        FXMLLoader myLoader = null;
        try
        {
            myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            parent.add(loadScreen, c, r);
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
        return myLoader;
    }


    /**
     * Method responsible for loading components in the main interface. It loads the FXML file in the specified StackPane
     *
     * @param parent   parent component
     * @param resource FXML path resource
     * @return FXMLLoader
     */
    private FXMLLoader loadViewMainScreen(StackPane parent, String resource)
    {
        FXMLLoader myLoader = null;
        try
        {
            myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            parent.getChildren().add(loadScreen);
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
        return myLoader;
    }

    /**
     * Update method where the observable classes sends notifications messages
     *
     * @param o   observable object
     * @param arg object arg
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof MqttMessage)
        {
            if (!paired)
            {
                Thread thread = new Thread(() ->
                {
                    JsonMessageParser parser = new JsonMessageParser();
                    parser.parseMessage(arg.toString());
                    if (parser.parsePairing())
                    {
                        setComponentVisible(this.widget1);
                        setComponentVisible(this.widget5);
                        changeScene();
                        this.paired = true;
                    }
                });
                thread.start();
            }
        }
        else if (arg instanceof Weather)
        {
            Thread thread = new Thread(() ->
            {
                setChanged();
                notifyObservers(arg);
            });
            thread.start();
        }
    }
}
