package mqttClient;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;
import java.util.Arrays;
import java.util.Observable;

public class SmartMirror_Publisher extends Observable
{
    private MQTTClient client;

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
    public void publishPresenceMessage(String version, String groupName, int groupNumber, long timestamp,
                                       String[] rfcs, String clientVersion, String clientId)
    {
        String gNum = "\"" + String.valueOf(groupNumber) + "\"";
        String ts = "\"" + String.valueOf(timestamp) + "\"";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", version);
        jsonObject.put("groupName", groupName);
        jsonObject.put("groupNumber", gNum);
        jsonObject.put("connectedAt", ts);
        jsonObject.put("rfcs", Arrays.toString(rfcs));
        jsonObject.put("clientVersion", clientVersion);
        jsonObject.put("clientSoftware", "SmartMirror");

        byte[] presenceMessage = jsonObject.toString().getBytes();

        String topic = "dit029/SmartMirror/" + clientId;
        try
        {
           this.client.getClient().publish(topic, presenceMessage, 1, true);
        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }

    }
}



