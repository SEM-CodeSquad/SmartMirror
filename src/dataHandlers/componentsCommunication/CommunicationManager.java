package dataHandlers.componentsCommunication;

import controllers.widgetsControllers.postItsController.PostItsController;
import dataHandlers.mqttClient.MQTTClient;
import dataHandlers.mqttClient.SmartMirror_Publisher;
import dataHandlers.mqttClient.SmartMirror_Subscriber;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Observable;
import java.util.Observer;

public class CommunicationManager extends Observable implements Observer
{
    private String clientId;
    private MQTTClient mqttClient;
    private SmartMirror_Publisher publisher;
    private boolean clientPaired;
    private JsonMessageParser parser;

    public CommunicationManager(String clientId)
    {
        this.clientId = clientId;
        this.mqttClient = new MQTTClient("tcp://codehigh.ddns.me", clientId);
        this.publisher = new SmartMirror_Publisher(this.mqttClient);
        this.publisher.publishPresenceMessage("1", "CodeHigh", 6, "1.0", this.clientId, "1", "6", "10", "11", "12");
        this.clientPaired = false;
        listenPairing();
    }

    private void listenSubscription(String topic)
    {
        try
        {
            Thread thread = new Thread(() ->
            {
                SmartMirror_Subscriber subscriber = new SmartMirror_Subscriber(this.mqttClient, topic);
                subscriber.addObserver(this);
            });
            thread.start();
            System.out.println("Listening to: " + topic);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void listenPairing()
    {
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/pairing");
    }

    private void paired()
    {
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/postit");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/settings");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/device");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/preferences");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/shoppingList");
    }

    private synchronized void publishEcho(String msg, boolean success)
    {
        if (!success)
        {
            publisher.echo("The post-it table " + msg + "is full! Please" +
                    "delete a post-it in order to add a new one.");
        }
        else
        {
            publisher.echo("The action was successfully received in the SmartMirror!");
        }
    }

    private synchronized void notifyMessageReceived(MqttMessage msg)
    {
        setChanged();
        notifyObservers(msg);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof MqttMessage)
        {
            Thread thread = new Thread(() ->
            {
                System.out.println("Msg: " + arg.toString());
                notifyMessageReceived((MqttMessage) arg);
                JsonMessageParser parser = new JsonMessageParser();
                parser.parseMessage(arg.toString());
                if (parser.parsePairing())
                {
                    paired();
                    this.clientPaired = true;
                }
            });
            thread.start();
        }
        else if (arg instanceof PostItsController)
        {
            Thread thread = new Thread(() ->
            {
                PostItsController postItsController = (PostItsController) arg;
                publishEcho(postItsController.getTableColor(), false);
            });
            thread.start();
        }
        else if (arg.equals("success"))
        {
            Thread thread = new Thread(() ->
            {
                publishEcho("", true);
            });
            thread.start();
        }
    }
}
