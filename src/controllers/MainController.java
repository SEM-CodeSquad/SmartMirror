package controllers;

import dataModels.QRCode;
import dataModels.UUID_Generator;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import widgets.TimeDateManager;

import java.io.IOException;

public class MainController {
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
    private Parent dateTime;
    private Parent qr;

    public MainController() {
        Platform.runLater(this::setUp);
    }

    private void setUp() {
        try {
            TimeDateManager timeDateManager = new TimeDateManager();
            timeDateManager.bindToTime();
            timeDateManager.bindToDate();
            timeDateManager.bindToDay();
            timeDateManager.bindGreetings();

            timeDateManager.addObserver(loadView(pairingDateTimeContainer, "/interfaceView/TimeDate.fxml",
                    0, 0).getController());

            widget1.add(dateTime, 0, 0);


            UUID_Generator uuid = new UUID_Generator();
            QRCode qrCode = new QRCode(uuid.getUUID());

            qrCode.addObserver(loadView(qrPairingContainer, "/interfaceView/QRCodeView.fxml",
                    1, 0).getController());
            gridMainQR.add(qr, 0, 0);
            qrCode.getQRCode();

            loadView(widget3, "/interfaceView/BusTimetable.fxml", 0, 0);

            timeDateManager.addObserver(loadView(widget5, "/interfaceView/GreetingsView.fxml",
                    0, 0).getController());

            loadView(widget4, "/interfaceView/TemperatureView.fxml", 0, 0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private FXMLLoader loadView(GridPane parent, String resource, int c, int r) {
        FXMLLoader myLoader = null;
        try {
            myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            parent.add(loadScreen, c, r);

            if (resource.equals("/interfaceView/TimeDate.fxml")) {
                dateTime = loadScreen;
            } else if (resource.equals("/interfaceView/QRCodeView.fxml")) {
                qr = loadScreen;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return myLoader;
    }
}
