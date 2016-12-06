package smartMirror.dataHandlers.commons;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Observable;

/**
 * @author Pucci @copyright on 06/12/2016.
 *         Class responsible for subscribing to topics in the broker
 */
public class SmartMirror_Subscriber extends Observable implements MqttCallback
{
    private MQTTClient client;
    private String topic;

    /**
     * Constructor sets this class to be receiving the call back (receiving the messages) and subscribe to a topic
     *
     * @param client mqtt client to be used
     * @param topic  topic to subscribe to
     */
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

    /**
     * Method responsible to monitor if the connection were lost, in case of lost connection
     * it attempts reconnecting the mqtt client
     *
     * @param throwable all errors and exceptions in the Java language
     */
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

    /**
     * Method responsible for receiving the messages and forwarding it to its observe
     *
     * @param topic       topic that the message come from
     * @param mqttMessage the received message
     */
    public void messageArrived(String topic, MqttMessage mqttMessage)
    {
        setChanged();
        notifyObservers(mqttMessage);
    }

    /**
     * Method called when delivery for a message has been completed
     *
     * @param iMqttDeliveryToken the delivery token associated with the message
     */
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken)
    {
        // Not used
    }

}


