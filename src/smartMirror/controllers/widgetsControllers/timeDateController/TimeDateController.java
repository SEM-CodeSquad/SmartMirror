package smartMirror.controllers.widgetsControllers.timeDateController;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import smartMirror.dataHandlers.commons.JsonMessageParser;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.widgetsModels.dateTimeModels.DateS;
import smartMirror.dataModels.widgetsModels.dateTimeModels.Day;
import smartMirror.dataModels.widgetsModels.dateTimeModels.TimeS;

import java.util.LinkedList;
import java.util.Observer;

/**
 * @author Pucci @copyright on 06/12/2016.
 *         Class responsible for updating the TimeDateView
 */
public class TimeDateController implements Observer
{
    public GridPane dateTime;
    public Label time;
    public Label date;
    public Label dayName;

    /**
     * Method responsible for setting this widget visible
     *
     * @param b true for visible false for not visible
     */
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

    /**
     * Method responsible for setting the parent visibility. In case of all the widgets in the parent are not visible
     * the parent also shall be not visible and vice-versa
     *
     * @param stackPane parent component
     * @param gridPane  parent parent component
     */
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

    /**
     * Method that sets the time in the component
     *
     * @param time time to be set
     */
    private void setTime(String time)
    {
        this.time.setText(time);
    }

    /**
     * Method that sets the date tin the component
     *
     * @param date date to be set
     */
    private void setDate(String date)
    {
        this.date.setText(date);
    }

    /**
     * Method that set the day name in the component
     *
     * @param dayName day name to be set
     */
    private void setDayName(String dayName)
    {
        this.dayName.setText(dayName);
    }

    /**
     * Update method where the observable classes sends notifications messages
     *
     * @param o   observable object
     * @param arg object arg
     */
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
