package controllers;


import dataModels.BusInfo;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import widgets.BusTimetable;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

public class BusTimetableController implements Observer {
    private final double duration = 3.0;
    public StackPane busTimetables;
    public GridPane timetable1;
    public Label busStopName;
    public Label busName1;
    public Label busName2;
    public Label busName3;
    public Label busName4;
    public Label busName5;
    public Label nextDepart1;
    public Label to1;
    public Label depart1;
    public Label nextDepart2;
    public Label to2;
    public Label depart2;
    public Label nextDepart3;
    public Label to3;
    public Label depart3;
    public Label nextDepart4;
    public Label to4;
    public Label depart4;
    public Label nextDepart5;
    public Label to5;
    public Label depart5;
    public GridPane timetable2;
    public Label busName7;
    public Label busName8;
    public Label busName9;
    public Label busName10;
    public Label busName11;
    public Label nextDepart7;
    public Label to7;
    public Label depart7;
    public Label nextDepart8;
    public Label to8;
    public Label depart8;
    public Label nextDepart9;
    public Label to9;
    public Label depart9;
    public Label nextDepart10;
    public Label to10;
    public Label depart10;
    public Label nextDepart11;
    public Label to11;
    public Label depart11;
    public Label nextDepart6;
    public Label to6;
    public Label depart6;
    public Label busName6;
    public GridPane timetable3;
    public Label busName13;
    public Label busName14;
    public Label busName15;
    public Label busName16;
    public Label busName17;
    public Label nextDepart13;
    public Label to13;
    public Label depart13;
    public Label nextDepart14;
    public Label to14;
    public Label depart14;
    public Label nextDepart15;
    public Label to15;
    public Label depart15;
    public Label nextDepart16;
    public Label to16;
    public Label depart16;
    public Label nextDepart17;
    public Label to17;
    public Label depart17;
    public Label nextDepart12;
    public Label to12;
    public Label depart12;
    public Label busName12;
    private boolean visible = true;
    private BusTimetable busTimetable;
    private Label[] stopNames;
    private Label[] directions;
    private Label[] departures;
    private Label[] nextDepartures;

    public BusTimetableController() {
        Platform.runLater(this::animation);
        Platform.runLater(this::setUp);
        busTimetable = new BusTimetable();
        busTimetable.addObserver(this);
        busTimetable.setBusTimetable("nordstan");
    }

    private void setUp() {
        stopNames = new Label[]{busName1, busName2, busName3, busName4, busName5, busName6, busName7, busName8, busName9
                , busName10, busName11, busName12, busName13, busName14, busName15, busName16, busName17};

        directions = new Label[]{to1, to2, to3, to4, to5, to6, to7, to8, to9, to10, to11, to12, to13, to14, to15, to16, to17};

        departures = new Label[]{depart1, depart2, depart3, depart4, depart5, depart6, depart7, depart8, depart9, depart10
                , depart11, depart12, depart13, depart14, depart15, depart16, depart17};
    }

    private void animation() {
        GridPane[] gridPanes = {timetable1, timetable2, timetable3};

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(
                fadeIn(gridPanes[0]),
                pauseEffect(),
                fadeOut(gridPanes[0]),
                fadeIn(gridPanes[1]),
                pauseEffect(),
                fadeOut(gridPanes[1]),
                fadeIn(gridPanes[2]),
                pauseEffect(),
                fadeOut(gridPanes[2]));
        sequentialTransition.setCycleCount(Timeline.INDEFINITE);
        sequentialTransition.setAutoReverse(false);
        sequentialTransition.play();

    }

    private FadeTransition fadeIn(GridPane node) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(duration), node);

        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        return fadeIn;
    }

    private FadeTransition fadeOut(GridPane node) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(duration), node);

        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        return fadeOut;
    }

    private PauseTransition pauseEffect() {
        return new PauseTransition(Duration.seconds(1));
    }

    private void setInfo(Label l, String text) {
        Platform.runLater(() -> l.setText(text));
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof BusInfo[]) {
            BusInfo[] busInfos = (BusInfo[]) arg;

            for (int i = 0; i < stopNames.length; i++) {
                System.out.println(busInfos[i].getBusName());
                setInfo(stopNames[i], busInfos[i].getBusName());
                setInfo(directions[i], busInfos[i].getBusDirection());
                setInfo(departures[i], String.valueOf(busInfos[i].getBusDeparture()));
            }
        }
    }
}
