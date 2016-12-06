package smartMirror.dataHandlers.mqttClient;

import smartMirror.dataHandlers.commons.Timestamp;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.Observable;

public class SmartMirror_Publisher extends Observable
{
    private MQTTClient client;
    private Timestamp timestamp;

    public SmartMirror_Publisher(MQTTClient client)
    {
        this.client = client;
    }

    public void publish(String topic, String content)
    {
        try
        {
            byte [] payload = content.getBytes();
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(payload);
            this.client.getClient().publish(topic, mqttMessage);
        }
        catch (MqttException e)
        {
            e.printStackTrace();
            setChanged();
            notifyObservers(this);
        }
    }



    @SuppressWarnings({"unchecked", "MismatchedQueryAndUpdateOfCollection"})
    public void publishPresenceMessage(String version, String groupName, int groupNumber, String clientVersion,
                                       String clientId, String... rfcs)
    {
        String gNum = String.valueOf(groupNumber);
        this.timestamp = new Timestamp();
        String ts = String.valueOf(this.timestamp.getTimestamp());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", version);
        jsonObject.put("groupName", groupName);
        jsonObject.put("groupNumber", gNum);
        jsonObject.put("connectedAt", ts);
        JSONArray rfcArray = new JSONArray();
        Collections.addAll(rfcArray, rfcs);
        jsonObject.put("rfcs", rfcArray.toString());
        jsonObject.put("clientVersion", clientVersion);
        jsonObject.put("clientSoftware", "SmartMirror");

        byte[] presenceMessage = jsonObject.toString().getBytes();

        String topic = "presence/" + clientId;
        try
        {
           this.client.getClient().publish(topic, presenceMessage, 1, true);
        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Method echo sends an echo message to a device that has sent messages to this client
     *
     * @param msg t
     */
    @SuppressWarnings("unchecked")
    public void echo(String msg) {
        String clientID = this.client.getClientId();
        String echoTopic = "dit029/SmartMirror/" + clientID + "/echo";

        timestamp = new Timestamp();
        String ts = String.valueOf(timestamp.getTimestamp());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageFrom", clientID);
        jsonObject.put("timestamp", ts);
        jsonObject.put("contentType", "echo");
        jsonObject.put("content", msg);

        String echoMessage = jsonObject.toString();

        publish(echoTopic, echoMessage);



    }
}



