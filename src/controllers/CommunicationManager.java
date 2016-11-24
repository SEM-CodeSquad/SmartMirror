package controllers;

import dataHandlers.JsonMessageParser;
import dataModels.PostItAction;
import dataModels.PostItNote;
import dataModels.Timestamp;
import mqttClient.MQTTClient;
import mqttClient.SmartMirror_Publisher;
import mqttClient.SmartMirror_Subscriber;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class CommunicationManager extends Observable implements Observer {
    private String clientId;
    private MQTTClient mqttClient;
    private SmartMirror_Publisher publisher;
    private boolean clientPaired;
    private JsonMessageParser parser;

    public CommunicationManager(String clientId) {
        this.clientId = clientId;
        this.mqttClient = new MQTTClient("tcp://codehigh.ddns.me", clientId);
        this.publisher = new SmartMirror_Publisher(this.mqttClient);
        this.publisher.publishPresenceMessage("1", "CodeHigh", 6, "1.0", this.clientId, "1", "6", "10", "12");
        this.parser = new JsonMessageParser();
        this.parser.addObserver(this);
        this.clientPaired = false;
        listenPairing();
    }

    private void listenSubscription(String topic) {
        try {
            Thread thread = new Thread(() -> {
                SmartMirror_Subscriber subscriber = new SmartMirror_Subscriber(this.mqttClient, topic);
                subscriber.addObserver(this);
            });
            thread.start();
            System.out.println("Listening to: " + topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listenPairing() {
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/pairing");
    }

    private void paired() {
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/postit");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/settings");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/device");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof MqttMessage) {
            Thread thread = new Thread(() -> {
                String str = arg.toString();
                System.out.println(str);

                parser.parseMessage(str);
                parser.parseContent();
            });
            thread.start();
        } else if (arg.equals("pairing")) {
            if (!this.clientPaired) {
                paired();
                this.clientPaired = true;
                setChanged();
                notifyObservers(this);
            }
        } else if (arg instanceof LinkedList) {
            LinkedList list = (LinkedList) arg;

        } else if (arg instanceof PostItNote) {
            PostItNote postItNote = (PostItNote) arg;

        } else if (arg instanceof PostItAction) {
            PostItAction postItAction = (PostItAction) arg;

        }
    }
}
