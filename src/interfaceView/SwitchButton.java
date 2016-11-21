package interfaceView;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

public class SwitchButton extends Label {
    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(true);

    public SwitchButton() {
        Button switchBtn = new Button();
        switchBtn.setPrefWidth(40);
        switchBtn.setOnAction(t -> switchedOn.set(!switchedOn.get()));

        setGraphic(switchBtn);

        switchedOn.addListener((ov, t, t1) -> {
            if (t1) {
                setText("ON");
                setStyle("-fx-background-color: green;-fx-text-fill:white;");
                setContentDisplay(ContentDisplay.RIGHT);
            } else {
                setText("OFF");
                setStyle("-fx-background-color: grey;-fx-text-fill:black;");
                setContentDisplay(ContentDisplay.LEFT);
            }
        });

        switchedOn.set(false);
    }

    public SimpleBooleanProperty switchOnProperty() {
        return switchedOn;
    }
}