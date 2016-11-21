package controllers;

import dataModels.DateS;
import dataModels.Day;
import dataModels.TimeS;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Observer;

public class TimeDateController implements Observer {
    public GridPane dateTime;
    public Label time;
    public Label date;
    public Label dayName;

    private void setVisible(Boolean b) {
        this.dateTime.setVisible(b);
    }

    private void setTime(String time) {
        this.time.setText(time);
    }

    private void setDate(String date) {
        this.date.setText(date);
    }

    private void setDayName(String dayName) {
        this.dayName.setText(dayName);
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        if (arg instanceof TimeS) {
            TimeS now = (TimeS) arg;
            setTime(now.getTime());
        } else if (arg instanceof DateS) {
            DateS now = (DateS) arg;
            setDate(now.getDate());
        } else if (arg instanceof Day) {
            Day now = (Day) arg;
            setDayName(now.getDayName());
        }
    }
}
