package dataHandlers.mqttClient;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Observable;

public class SmartMirror_Subscriber extends Observable implements MqttCallback
{
    private MQTTClient client;
    private String topic;

    public SmartMirror_Subscriber(MQTTClient client, String topic)
    {
        try
        {
            this.client = client;
            this.topic = topic;
            client.getClient().setCallback(this);
            client.getClient().subscribe(topic);
        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }
    }

    public void connectionLost(Throwable throwable)
    {
        try
        {
            System.out.println("Lost Connection");
            throwable.printStackTrace();
            this.client.reconnect();
            client.getClient().setCallback(this);
            client.getClient().subscribe(topic);
        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }

    }

    public void messageArrived(String topic, MqttMessage mqttMessage)
    {
        setChanged();
        notifyObservers(mqttMessage);
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken)
    {
        // TODO Auto-generated method stub
    }

}


