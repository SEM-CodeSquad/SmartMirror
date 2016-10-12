package subscriber;

import clientConnection.Client;
//import com.google.gson.Gson;
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
import java.util.Observable;

import java.util.ArrayList;

public class SmartMirror_Subscriber extends Observable implements MqttCallback
{
    private MqttMessage mqttMessage;
    Client client ;
    private JSONArray Postit;
    public String Title;
    public String Text;
    public String Color;

    public SmartMirror_Subscriber(Client client, String topic)
    {
        try
        {
            this.client = client;
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

        //this.client.startReconnect();

    }


    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

            this.mqttMessage = mqttMessage;
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(mqttMessage.toString());
            JSONArray Postit = (JSONArray) json.get("Postit");
            JSONObject obj = (JSONObject) Postit.get(0);
            setChanged();
            notifyObservers(Postit);
            Gson gson = new Gson();
            Postit postit = gson.fromJson(obj.toJSONString(), Postit.class);
            this.Title = postit.Title;
            this.Text = postit.Text;
            this.Color = postit.Color;


    }

    class Postit{
        String Title;
        String Text;
        String Color;

        public String toString() {

            return Title + " "+ Text + " " + Color;
        }

    }


    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // TODO Auto-generated method stub
    }
}


