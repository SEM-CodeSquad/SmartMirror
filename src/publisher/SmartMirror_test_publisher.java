package publisher;

import clientConnection.Client;
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

    public SmartMirror_test_publisher(Client client, String topic) {
        try {
            JSONObject json = new JSONObject();
            JSONArray array = new JSONArray();
            JSONObject item = new JSONObject();
            item.put("Color", "Red");
            item.put("Title", "Dishes");
            item.put("Text", "We need to do our dishes");
            array.add(item);

            json.put("Postit", array);
            mqttMessage = new MqttMessage();
            mqttMessage.setPayload(
                    json.toString()
                            .getBytes());

            client.getClient().publish("test", mqttMessage);


        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void connectionLost(Throwable throwable) {
        // TODO Auto-generated method stub
    }


    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}



