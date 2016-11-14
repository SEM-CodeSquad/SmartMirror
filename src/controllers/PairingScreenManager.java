package controllers;

import dataModels.QRCode;
import dataModels.UUID_Generator;
import interfaceView.PairingScreen;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import widgets.TimeDateManager;

import java.util.Observable;
import java.util.Observer;

public class PairingScreenManager extends Observable implements Observer {

    public GridPane pairingDateTimeContainer;
    public GridPane pairingContainer1;
    private QRCode qr;
    private TimeDateManager timeDate;
    private PairingScreen pairingScreen;

    public PairingScreenManager() {
        this.timeDate = new TimeDateManager();
        UUID_Generator uuid = new UUID_Generator();
        this.qr = new QRCode(uuid.getUUID());
        Platform.runLater(this::build);
    }

    private void build() {
        pairingScreen = new PairingScreen(this.pairingDateTimeContainer, this.pairingContainer1);
        this.addObserver(pairingScreen);
        pairingScreen.addObserver(this);
    }

    private void setUp() {
        setChanged();
        notifyObservers(this.qr.getQRCode());
        timeDate.bindToTime(pairingScreen.getTime());
        timeDate.bindToDate(pairingScreen.getDate());
        timeDate.bindToDay(pairingScreen.getDayName());
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("PairingScreen Done!")) {
            Platform.runLater(this::setUp);
        }

    }
}
