package controllers;


import dataModels.Greetings;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Observable;
import java.util.Observer;

public class GreetingsController implements Observer {
    public GridPane greetingsPane;
    public Label greetings;
    public Label secondaryMessage;

    private void setVisible(Boolean b) {
        this.greetingsPane.setVisible(b);
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
        }
    }
}
