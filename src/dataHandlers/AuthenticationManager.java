package dataHandlers;

import gui.InterfaceController;
import javafx.application.Platform;
import mqttClient.MQTTClient;
import mqttClient.SmartMirror_Subscriber;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class AuthenticationManager extends Observable implements Observer
{
    private String clientId;
    private String content;
    private MQTTClient client;
    private InterfaceController gui;
    private boolean clientAuthenticated;

    public AuthenticationManager(String clientId, MQTTClient client, InterfaceController gui)
    {
        this.clientId = clientId;
        this.client = client;
        this.gui = gui;
        this.clientAuthenticated = false;
        addObserver(this.gui);
    }

    private void listenSubscription(String topic)
    {
        try
        {
            //TODO set pairing code back after testing
            Thread thread = new Thread(() ->{
                SmartMirror_Subscriber subscriber = new SmartMirror_Subscriber(this.client, topic);
                subscriber.addObserver(this.gui);
            });
            thread.start();
            System.out.println("Listening to: " + topic);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void listenAuthentication()
    {
       listenSubscription("dit029/SmartMirror/authentication");
    }

    private void authenticated()
    {
        listenSubscription("dit029/SmartMirror/postit");
        listenSubscription("dit029/SmartMirror/settings");
        listenSubscription("dit029/SmartMirror/device");
    }

    private void sendHeartBeat()
    {

    }

    @Override
    public void update(Observable o, Object arg)
    {
       if (arg.equals("authentication"))
       {
           if (!this.clientAuthenticated)
           {
               authenticated();
               setChanged();
               notifyObservers(this);
           }
           this.clientAuthenticated = true;
       }
    }
}
