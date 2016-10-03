package subscriber;

import clientConnection.Client;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SmartMirror_Subscriber implements MqttCallback
{
    private MqttMessage mqttMessage;

    public SmartMirror_Subscriber(Client client, String topic)
    {
        try
        {
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
        // TODO Auto-generated method stub
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println(mqttMessage);
        this.mqttMessage = mqttMessage;
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // TODO Auto-generated method stub
    }
}