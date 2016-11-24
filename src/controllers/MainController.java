package controllers;

import dataModels.QRCode;
import dataModels.UUID_Generator;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import mqttClient.MQTTClient;
import widgets.TimeDateManager;

import java.awt.*;
import java.io.IOException;
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

    private boolean systemRunning;
    private UUID_Generator uuid;

    private CommunicationManager communicationManager;


    public MainController() {
        this.systemRunning = true;
        uuid = new UUID_Generator();
        Platform.runLater(this::setUp);

        communicationManager = new CommunicationManager(uuid.getUUID());
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                Platform.runLater(() -> changeScene());
//
//            }
//        }, 3000);


    }

    private void setUp() {
        setUpDateTimeView();
        setUpQRCodeView();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), this.pairingPane);

        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        setUpPostItView();
    }

    private void setUpDevicesView() {
        loadView(widget7, "/interfaceView/PostItView.fxml", 0, 0);
    }

    private void setUpPostItView() {
        loadView(widget6, "/interfaceView/PostItView.fxml", 0, 0);
    }

    private void setUpFeedView() {
        loadView(widget2, "/interfaceView/FeedsViews.fxml", 0, 0);
    }

    private void setUpTemperatureView() {
        loadView(widget4, "/interfaceView/TemperatureView.fxml", 0, 0);
    }

    private void setUpBusTimetableView() {
        loadView(widget3, "/interfaceView/BusTimetable.fxml", 0, 0);
    }

    private void setUpQRCodeView() {
        QRCode qrCode = new QRCode(uuid.getUUID());

        qrCode.addObserver(loadView(qrPairingContainer, "/interfaceView/QRCodeView.fxml",
                1, 0).getController());

        qrCode.addObserver(loadView(gridMainQR, "/interfaceView/QRCodeView.fxml",
                0, 0).getController());

        qrCode.getQRCode();


    }

    private void setUpDateTimeView() {
        TimeDateManager timeDateManager = new TimeDateManager();
        timeDateManager.bindToTime();
        timeDateManager.bindToDate();
        timeDateManager.bindToDay();
        timeDateManager.bindGreetings();

        timeDateManager.addObserver(loadView(pairingDateTimeContainer, "/interfaceView/TimeDate.fxml",
                0, 0).getController());

        timeDateManager.addObserver(loadView(widget1, "/interfaceView/TimeDate.fxml",
                0, 0).getController());

        timeDateManager.addObserver(loadView(widget5, "/interfaceView/GreetingsView.fxml",
                0, 0).getController());


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

    private FXMLLoader loadView(GridPane parent, String resource, int c, int r) {
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


    @Override
    public void update(Observable o, Object arg) {

    }
}
