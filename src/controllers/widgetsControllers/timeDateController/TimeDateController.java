package controllers.widgetsControllers.timeDateController;

import dataHandlers.componentsCommunication.JsonMessageParser;
import dataHandlers.mqttClient.MQTTClient;
import dataModels.applicationModels.Preferences;
import dataModels.widgetsModels.dateTimeModels.DateS;
import dataModels.widgetsModels.dateTimeModels.Day;
import dataModels.widgetsModels.dateTimeModels.TimeS;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.LinkedList;
import java.util.Observer;

public class TimeDateController implements Observer
{
    public GridPane dateTime;
    public Label time;
    public Label date;
    public Label dayName;

    private synchronized void setVisible(boolean b)
    {
        Platform.runLater(() ->
        {
            this.dateTime.setVisible(b);
            StackPane parentPane = (StackPane) this.dateTime.getParent();
            GridPane parentGrid = (GridPane) parentPane.getParent();

            monitorWidgetVisibility(parentPane, parentGrid);
        });
    }

    private synchronized void monitorWidgetVisibility(StackPane stackPane, GridPane gridPane)
    {
        boolean visible = false;
        ObservableList<Node> list = stackPane.getChildren();
        for (Node node : list)
        {
            visible = node.isVisible();
        }
        gridPane.setVisible(visible);
    }

    private void setTime(String time)
    {
        this.time.setText(time);
    }

    private void setDate(String date)
    {
        this.date.setText(date);
    }

    private void setDayName(String dayName)
    {
        this.dayName.setText(dayName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(java.util.Observable o, Object arg)
    {

        if (arg instanceof TimeS)
        {
            TimeS now = (TimeS) arg;
            setTime(now.getTime());
        }
        else if (arg instanceof DateS)
        {
            DateS now = (DateS) arg;
            setDate(now.getDate());
        }
        else if (arg instanceof Day)
        {
            Day now = (Day) arg;
            setDayName(now.getDayName());
        }
        else if (arg instanceof MqttMessage)
        {
            Thread thread = new Thread(() ->
            {
                JsonMessageParser parser = new JsonMessageParser();
                parser.parseMessage(arg.toString());

                if (parser.getContentType().equals("preferences"))
                {
                    LinkedList<Preferences> list = parser.parsePreferenceList();

                    list.stream().filter(pref -> pref.getName().equals("clock")).forEach(pref ->
                            setVisible(pref.getValue().equals("true")));
                }
            });
            thread.start();
        }
    }
}
