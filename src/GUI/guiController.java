package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.io.IOException;

/**
 * Created by Axel on 10/11/2016.
 */

public class guiController {

    public Label postit;
    private MqttMessage mqttMessage;

    public void mousePressed(MouseEvent event) throws IOException{

        postit.setText("/toString(mqttMessage)");
    }

    private void setMqttMessage(MqttMessage msg){
        this.mqttMessage = msg;
    }

}
