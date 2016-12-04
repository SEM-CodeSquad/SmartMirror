package controllers.widgetsControllers.greetingsController;


import dataHandlers.componentsCommunication.JsonMessageParser;
import dataModels.applicationModels.Preferences;
import dataModels.widgetsModels.greetingsModels.Greetings;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;


public class GreetingsController implements Observer
{
    public GridPane greetingsPane;
    public Label greetings;
    public Label secondaryMessage;

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

    private void setGreetings(String greetings)
    {
        this.greetings.setText(greetings);
    }

    private void setSecondaryMessage(String message)
    {
        this.secondaryMessage.setText(message);
    }

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
