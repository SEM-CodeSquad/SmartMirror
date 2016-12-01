package controllers.widgetsControllers.timeDateController;

import dataModels.applicationModels.Preferences;
import dataModels.widgetsModels.dateTimeModels.DateS;
import dataModels.widgetsModels.dateTimeModels.Day;
import dataModels.widgetsModels.dateTimeModels.TimeS;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Observer;

public class TimeDateController implements Observer {
    public GridPane dateTime;
    public Label time;
    public Label date;
    public Label dayName;

    private synchronized void setVisible(boolean b) {
        Platform.runLater(() -> this.dateTime.setVisible(b));
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
        } else if (arg instanceof Preferences && ((Preferences) arg).getName().equals("widget1")) {
            Thread thread = new Thread(() -> setVisible(((Preferences) arg).getValue().equals("true")));
            thread.start();
        }
    }
}
