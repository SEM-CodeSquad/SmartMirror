package gui;

import javafx.scene.control.DateCell;
import mqttHandler.MQTTClient;
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
import mqttHandler.SmartMirror_Subscriber;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

//TODO: pre-construct the post-its and update data + make visible when message arrives.
//TODO: Dynamically add pictures to the post-it.

public class InterfaceController implements Observer {
    @FXML
    private TextArea postNote,postNote1,postNote2,postNote3,postNote4,postNote5,postNote6;
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

    //TODO: Thread the parser of the object

    @Override
    public void update(Observable o, Object obj) {

        Thread newPostitThread = new Thread(()->{
            parseMessage(obj);
        });
        newPostitThread.start();
    }

    private void parseMessage(Object recievedMessage){
        try {
            MqttReceivedMessage received = (MqttReceivedMessage) recievedMessage;

            String str = received.toString();
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(str);
            JSONArray Postit = (JSONArray) json.get("Postit");
            JSONObject jsonObject = (JSONObject) Postit.get(0);


            String title = jsonObject.get("Title").toString();
            String msg = jsonObject.get("Text").toString();
            String color = jsonObject.get("Color").toString();
            Platform.runLater(() -> setPost(title, msg, color));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setPost(String title, String msg, String color){
        String newLine = System.getProperty("line.separator");
        postNote.setPrefColumnCount(4);
        postNote.setPrefRowCount(4);
        postNote.setText(title + newLine + msg + newLine + color);
        postNote.setVisible(true);
    }

    private void bindToTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> time.setText(LocalTime.now().format(SHORT_TIME_FORMATTER))),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
