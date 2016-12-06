package smartMirror.controllers.widgetsControllers.greetingsController;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import smartMirror.dataHandlers.commons.JsonMessageParser;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.widgetsModels.greetingsModels.Greetings;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Pucci @copyright on 06/12/2016.
 *         Class responsible for updating the GreetingsView
 */
public class GreetingsController implements Observer
{
    public GridPane greetingsPane;
    public Label greetings;
    public Label secondaryMessage;

    /**
     * Method responsible for setting this widget visible
     *
     * @param b true for visible false for not visible
     */
    private synchronized void setVisible(boolean b)
    {
        Platform.runLater(() ->
        {
            this.greetingsPane.setVisible(b);
            StackPane parentPane = (StackPane) this.greetingsPane.getParent();
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
     * Method responsible to set the specified text in the greetings component
     *
     * @param greetings text to be set
     */
    private void setGreetings(String greetings)
    {
        this.greetings.setText(greetings);
    }

    // TODO: 06/12/2016
//    private void setSecondaryMessage(String message)
//    {
//        this.secondaryMessage.setText(message);
//    }

    /**
     * Update method where the observable classes sends notifications messages
     *
     * @param o   observable object
     * @param arg object arg
     */
    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object arg)
    {
        if (arg instanceof Greetings)
        {
            Greetings greetings = (Greetings) arg;
            setGreetings(greetings.getGreetings());
        }
        else if (arg instanceof MqttMessage)
        {
            Thread thread = new Thread(() ->
            {
                JsonMessageParser parser = new JsonMessageParser();
                parser.parseMessage(arg.toString());

                if (parser.getContent().equals("preferences"))
                {
                    LinkedList<Preferences> list = parser.parsePreferenceList();

                    list.stream().filter(pref -> pref.getName().equals("greetings")).forEach(pref ->
                            setVisible(pref.getValue().equals("true")));
                }

            });
            thread.start();
        }
    }
}
