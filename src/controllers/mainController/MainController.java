package controllers.mainController;

import controllers.widgetsControllers.busTimetableController.BusTimetableController;
import controllers.widgetsControllers.devicesController.DeviceController;
import controllers.widgetsControllers.feedController.FeedController;
import controllers.widgetsControllers.greetingsController.GreetingsController;
import controllers.widgetsControllers.postItsController.PostItViewController;
import controllers.widgetsControllers.temperatureController.TemperatureController;
import controllers.widgetsControllers.timeDateController.TimeDateController;
import dataHandlers.componentsCommunication.CommunicationManager;
import dataHandlers.componentsCommunication.JsonMessageParser;
import dataHandlers.widgetsDataHandlers.timeDate.TimeDateManager;
import dataModels.applicationModels.Preferences;
import dataModels.applicationModels.UUID_Generator;
import dataModels.widgetsModels.busTimetableModels.BusStop;
import dataModels.widgetsModels.devicesModels.Device;
import dataModels.widgetsModels.feedModels.NewsSource;
import dataModels.widgetsModels.qrCodeModels.QRCode;
import dataModels.widgetsModels.weatherModels.Town;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class MainController extends Observable implements Observer {
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
    private UUID_Generator uuid;

    private CommunicationManager communicationManager;
    private TimeDateManager timeDateManager;
    private JsonMessageParser jsonMessageParser;

    private FeedController feedController;
    private TemperatureController temperatureController;
    private BusTimetableController busTimetableController;
    private DeviceController deviceController;
    private TimeDateController timeDateController;
    private GreetingsController greetingsController;


    public MainController() {
        this.systemRunning = true;
        uuid = new UUID_Generator();
        communicationManager = new CommunicationManager(uuid.getUUID(), this);
    }

    private void startUp() {
        setUpDateTimeView();
        setUpQRCodeView();
        setUpTemperatureView();
        setUpDevicesView();
        setUpFeedView();
        setUpBusTimetableView();

        this.jsonMessageParser.addObserver(this.busTimetableController);
        this.jsonMessageParser.addObserver(this.deviceController);
        this.jsonMessageParser.addObserver(this.feedController);
        this.jsonMessageParser.addObserver(this.temperatureController);
        this.jsonMessageParser.addObserver(this.timeDateController);

        this.pairingPane.setOpacity(1);

        keepScreenAlive();
    }

    private void setUpDevicesView() {
        deviceController = loadViewMainScreen(stackPaneWidget7, "/interfaceViews/widgetsViews/deviceStatusWidget/DeviceView.fxml").getController();
        this.jsonMessageParser.addObserver(deviceController);
    }

    private void setUpPostItView() {
        PostItViewController postItViewController = loadViewMainScreen(stackPaneWidget6, "/interfaceViews/widgetsViews/postItWidget/PostitView.fxml").getController();
        this.jsonMessageParser.addObserver(postItViewController);
        postItViewController.addObserver(communicationManager);
        setComponentVisible(widget6);
    }

    private void setUpFeedView() {
        this.feedController = loadViewMainScreen(stackPaneWidget2, "/interfaceViews/widgetsViews/feedsWidget/FeedsViews.fxml").getController();
        this.timeDateManager.addObserver(feedController);
        this.addObserver(feedController);
    }

    private void setUpTemperatureView() {
        this.temperatureController = loadViewMainScreen(stackPaneWidget4, "/interfaceViews/widgetsViews/weatherWidget/TemperatureView.fxml").getController();
        timeDateManager.addObserver(temperatureController);
        this.addObserver(temperatureController);

    }

    private void setUpBusTimetableView() {
        this.busTimetableController = loadViewMainScreen(stackPaneWidget3, "/interfaceViews/widgetsViews/busTimetableWidget/BusTimetable.fxml").getController();
        timeDateManager.addObserver(busTimetableController);
        this.addObserver(busTimetableController);
    }

    private void setUpQRCodeView() {
        QRCode qrCode = new QRCode(uuid.getUUID());

        qrCode.addObserver(loadViewPairingScreen(qrPairingContainer, "/interfaceViews/widgetsViews/qrCode/QRCodeView.fxml",
                1, 0).getController());

        qrCode.addObserver(loadViewMainScreen(stackPaneWidgetQR, "/interfaceViews/widgetsViews/qrCode/QRCodeView.fxml").getController());

        qrCode.getQRCode();


    }

    private void setUpDateTimeView() {
        timeDateManager = new TimeDateManager();
        timeDateManager.bindToTime();
        timeDateManager.bindToDate();
        timeDateManager.bindToDay();
        timeDateManager.bindGreetings();

        timeDateManager.addObserver(loadViewPairingScreen(pairingDateTimeContainer, "/interfaceViews/widgetsViews/timeDateWidget/TimeDate.fxml",
                0, 0).getController());
        timeDateController = loadViewMainScreen(stackPaneWidget1, "/interfaceViews/widgetsViews/timeDateWidget/TimeDate.fxml").getController();
        timeDateManager.addObserver(timeDateController);
    }

    private void setUpGreetingsView() {
        greetingsController = loadViewMainScreen(stackPaneWidget5, "/interfaceViews/widgetsViews/greetingsWidget/GreetingsView.fxml").getController();
        timeDateManager.addObserver(greetingsController);
        this.jsonMessageParser.addObserver(greetingsController);
    }

    private void changeScene() {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), this.pairingPane);

        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), this.mainPane);

        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        fadeOut.setOnFinished(event -> fadeIn.play());
        fadeOut.play();

    }

    private synchronized void setComponentVisible(GridPane gridPane) {
        Platform.runLater(() -> {
            gridPane.setVisible(true);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), gridPane);

            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
    }

    private synchronized void setComponentInvisible(GridPane gridPane) {
        Platform.runLater(() -> {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), gridPane);

            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> gridPane.setVisible(false));
            fadeOut.play();
        });
    }

    private void keepScreenAlive() {
        Thread thread = new Thread(() -> {
            while (systemRunning) {
                try {
                    Thread.sleep(80000);//this is how long before it moves
                    Point point = MouseInfo.getPointerInfo().getLocation();
                    Robot robot = new Robot();
                    robot.mouseMove(point.x, point.y);
                } catch (InterruptedException | AWTException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private FXMLLoader loadViewPairingScreen(GridPane parent, String resource, int c, int r) {
        FXMLLoader myLoader = null;
        try {
            myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            parent.add(loadScreen, c, r);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return myLoader;
    }

    private FXMLLoader loadViewMainScreen(StackPane parent, String resource) {
        FXMLLoader myLoader = null;
        try {
            myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            parent.getChildren().add(loadScreen);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return myLoader;
    }

    // TODO: 01/12/2016   
    private synchronized void monitorWidgetVisibility(StackPane stackPane, GridPane gridPane) {
        boolean visible = false;
        ObservableList<Node> list = stackPane.getChildren();
        for (Node node : list) {
            visible = node.isVisible();
        }
        gridPane.setVisible(visible);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof JsonMessageParser) {
            this.jsonMessageParser = (JsonMessageParser) arg;
            this.jsonMessageParser.addObserver(this);
            Thread thread = new Thread(() -> {
                Platform.runLater(this::startUp);
                Platform.runLater(this::setUpPostItView);
            });
            thread.start();
        } else if (arg instanceof CommunicationManager) {
            Thread thread = new Thread(() -> Platform.runLater(this::setUpGreetingsView));
            thread.start();
            changeScene();
            setComponentVisible(widget1);
            setComponentVisible(gridMainQR);
            setComponentVisible(widget5);

        } else if (arg instanceof BusStop) {
            Thread thread = new Thread(() -> this.setComponentVisible(this.widget3));
            thread.start();

        } else if (arg instanceof Town) {
            Thread thread = new Thread(() -> this.setComponentVisible(this.widget4));
            thread.start();

        } else if (arg instanceof NewsSource) {
            Thread thread = new Thread(() -> this.setComponentVisible(this.widget2));
            thread.start();

        } else if (arg instanceof LinkedList && ((LinkedList) arg).peek() instanceof Device) {
            Thread thread = new Thread(() -> setComponentVisible(widget7));
            thread.start();
        } else if (arg instanceof Preferences && ((Preferences) arg).getName().equals("widget3")) {
            Thread thread = new Thread(() -> Platform.runLater(() -> {
                if (((Preferences) arg).getValue().equals("true")) {
                    setComponentVisible(widget3);
                } else {
                    setComponentInvisible(widget3);
                }
            }));
            thread.start();
        }

    }
}
