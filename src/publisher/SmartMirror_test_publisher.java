package publisher;

import clientConnection.MQTTClient;
//import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by adinH on 2016-10-09.
 * just for testing purposes
 */

public class SmartMirror_test_publisher {

    private MqttMessage mqttMessage;

    public SmartMirror_test_publisher(MQTTClient client, String topic) {

    }

    public void connectionLost(Throwable throwable) {
        // TODO Auto-generated method stub
    }


    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}



