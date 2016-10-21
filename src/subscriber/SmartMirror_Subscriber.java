package subscriber;

import clientConnection.MQTTClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


import java.util.Observable;

public class SmartMirror_Subscriber extends Observable implements MqttCallback
{
    private MqttMessage mqttMessage;
    private MQTTClient client;


    public SmartMirror_Subscriber(MQTTClient client, String topic)
    {
        try
        {
            this.client = client;
            client.getClient().setCallback(this);
            client.getClient().subscribe(topic);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public MqttMessage getMqttMessage()
    {
       return this.mqttMessage;
    }

    public void connectionLost(Throwable throwable) {
        System.out.println("Lost Connection");
        throwable.printStackTrace();
        this.client.startReconnect();

    }


    public void messageArrived(String s, MqttMessage mqttMessage)

    {
        setChanged();
        try {
            notifyObservers(mqttMessage);
        }
        catch(Exception e){

        }
    }


    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // TODO Auto-generated method stub
    }

}


