package controllers.widgetsControllers.greetingsController;


import dataModels.applicationModels.Preferences;
import dataModels.widgetsModels.greetingsModels.Greetings;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Observable;
import java.util.Observer;

public class GreetingsController implements Observer {
    public GridPane greetingsPane;
    public Label greetings;
    public Label secondaryMessage;

    private synchronized void setVisible(boolean b) {
        Platform.runLater(() -> this.greetingsPane.setVisible(b));
    }

    private void setGreetings(String greetings) {
        this.greetings.setText(greetings);
    }

    private void setSecondaryMessage(String message) {
        this.secondaryMessage.setText(message);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Greetings) {
            Greetings greetings = (Greetings) arg;
            setGreetings(greetings.getGreetings());
        } else if (arg instanceof Preferences && ((Preferences) arg).getName().equals("widget5")) {
            Thread thread = new Thread(() -> setVisible(((Preferences) arg).getValue().equals("true")));
            thread.start();
        }
    }
}
