package controllers;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class MainInterfaceController implements Observer {
    public BorderPane pairingPane;
    public GridPane pairingDateTimeContainer;
    public GridPane gridPaneQR;
    public AnchorPane mainPane;
    public GridPane gridTimeDate;
    public GridPane gridDevices;
    public GridPane gridPost;
    public GridPane gridTimetable;
    public GridPane gridMainQR;
    public GridPane gridNewsAndNotification;
    public GridPane gridGreetings;
    public GridPane gridTemperature;
    private boolean systemRunning;

    public MainInterfaceController() {
        systemRunning = true;
    }

    private void changeScene(String s) {
        if (s.equals("Paired")) {
            this.pairingPane.setVisible(false);
            this.mainPane.setVisible(true);
        } else {
            this.pairingPane.setVisible(true);
            this.mainPane.setVisible(false);
        }
    }

    private void keepScreenAlive() {
        Thread thread = new Thread(() ->
        {
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


    @Override
    public void update(Observable o, Object arg) {

    }
}
