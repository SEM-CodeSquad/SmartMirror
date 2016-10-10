package subscriber;

import clientConnection.Client;
import com.google.gson.Gson;
import javafx.geometry.Pos;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import publisher.SmartMirror_test_publisher;

import java.util.ArrayList;

public class SmartMirror_Subscriber implements MqttCallback
{
    private MqttMessage mqttMessage;

    public SmartMirror_Subscriber(Client client, String topic)
    {
        try
        {

            client.getClient().setCallback(this);
            client.getClient().subscribe(topic);
            JSONObject json = new JSONObject();
            // Please use this format when passing around a JSON obj
            //just for testing purposes
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

    public MqttMessage getMqttMessage()
    {
       return this.mqttMessage;
    }

    public void connectionLost(Throwable throwable) {
        System.out.println("Lost Connection");
        System.out.println(throwable);
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        this.mqttMessage = mqttMessage;
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(mqttMessage.toString());
        JSONArray Postit = (JSONArray ) json.get("Postit");
        Gson gson = new Gson();
        //example: final Postit postit = gson.fromJson(Postit, Postit.class);
        System.out.println(Postit);
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // TODO Auto-generated method stub
    }
}


