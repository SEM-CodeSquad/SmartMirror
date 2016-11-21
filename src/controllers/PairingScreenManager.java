package controllers;

import dataModels.*;
import interfaceView.PairingScreen;
import javafx.scene.layout.GridPane;
import widgets.TimeDateManager;

import java.util.Observable;
import java.util.Observer;

public class PairingScreenManager extends Observable implements Observer {

    private GridPane pairingDateTimeContainer;
    private GridPane gridQR;
    private QRCode qr;
    private TimeDateManager timeDate;
    private PairingScreen pairingScreen;

    public PairingScreenManager(GridPane dateTime, GridPane gridQR, UUID_Generator uuid, TimeDateManager timeDate) {
        this.pairingDateTimeContainer = dateTime;
        this.gridQR = gridQR;
        this.timeDate = timeDate;
        this.timeDate.addObserver(this);
        this.qr = new QRCode(uuid.getUUID());
        this.build();
    }

    private void build() {
        pairingScreen = new PairingScreen(this.pairingDateTimeContainer, this.gridQR);
        this.addObserver(pairingScreen);
        pairingScreen.addObserver(this);
    }

    private void setUp() {
        setChanged();
        notifyObservers(this.qr.getQRCode());
        timeDate.bindToTime();
        timeDate.bindToDate();
        timeDate.bindToDay();
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("PairingScreen Done!")) {
            this.setUp();
        } else if (arg instanceof TimeS) {
            TimeS time = (TimeS) arg;
            setChanged();
            notifyObservers(time);
        } else if (arg instanceof DateS) {
            DateS date = (DateS) arg;
            setChanged();
            notifyObservers(date);
        } else if (arg instanceof Day) {
            Day day = (Day) arg;
            setChanged();
            notifyObservers(day);
        }

    }
}
