package guiInterface;

import clientConnection.MQTTClient;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttReceivedMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import subscriber.SmartMirror_Subscriber;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class InterfaceController implements Observer {
    @FXML
    private TextArea postNote;
    @FXML
    private Label time;
    @FXML
    private Label date;
    private static DateTimeFormatter SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public InterfaceController()
    {
        MQTTClient client = new MQTTClient("tcp://codehigh.ddns.me", "Tester");
        SmartMirror_Subscriber sms = new SmartMirror_Subscriber(client, "test");
        sms.addObserver(this);
        bindToTime();
    }

    @Override
    public void update(Observable o, Object obj) {

        try {
            MqttReceivedMessage received = (MqttReceivedMessage) obj;
            String str = received.toString();
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(str);
            JSONArray Postit = (JSONArray) json.get("Postit");
            JSONObject jsonObject = (JSONObject) Postit.get(0);


            String title = jsonObject.get("Title").toString();
            String msg = jsonObject.get("Text").toString();
            Platform.runLater(() -> setPost(title, msg));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void setPost(String title, String msg){
        String newLine = System.getProperty("line.separator");
        postNote.setPrefColumnCount(4);
        postNote.setPrefRowCount(4);
        postNote.setText(title + newLine + msg);
    }

    private void setColor(String color){


    }
    private void setText(String text){


    }

    private void bindToTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> time.setText(LocalTime.now().format(SHORT_TIME_FORMATTER))),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


}
