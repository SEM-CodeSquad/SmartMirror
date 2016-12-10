package test;

import smartMirror.dataModels.modelCommons.MQTTClient;
import smartMirror.dataModels.modelCommons.SmartMirror_Publisher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Scanner;

/**
 * @author Pucci on 30/11/2016.
 */
public class Preftest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter the id: ");
        String i = scan.nextLine();
        String topic = "dit029/SmartMirror/" + i + "/preferences";

        Preftest preftest = new Preftest();

        //54.154.153.243
        //codehigh.ddns.me
        MQTTClient client = new MQTTClient("tcp://54.154.153.243", "test");
        SmartMirror_Publisher publisher = new SmartMirror_Publisher(client);

        System.out.println("Enter what you want [standard, blue, green, purple, orange,\n" +
                "                pink, yellow]: ");
        String action = scan.nextLine();

        switch (action)
        {
            case "show":
                publisher.publish(topic, preftest.sendPreferenceShow());
                break;
            case "hide":
                publisher.publish(topic, preftest.sendPreferenceHide());
                break;
            default:
                publisher.publish(topic, preftest.sendPreferenceShowOnly(action));
                break;
        }
        client.disconnect();

    }

    @SuppressWarnings("unchecked")
    private String sendPreferenceShow() {
        String messageString;
        try {
            JSONObject sendThis = new JSONObject();
            sendThis.put("messageFrom", "test");
            sendThis.put("timestamp", "12");
            sendThis.put("contentType", "preferences");

            JSONObject item = new JSONObject();
            item.put("bus", "true");
            item.put("weather", "true");
            item.put("device", "true");
            item.put("news", "true");
            item.put("postits", "true");
            item.put("clock", "true");
            item.put("greetings", "true");

            JSONArray jArray = new JSONArray();
            jArray.add(0, item);
            sendThis.put("content", jArray);
            messageString = sendThis.toJSONString();

        } catch (Exception e) {
            return "Warning: did not publish";
        }
        return messageString;
    }

    @SuppressWarnings("unchecked")
    private String sendPreferenceHide() {
        String messageString;
        try {
            JSONObject sendThis = new JSONObject();
            sendThis.put("messageFrom", "test");
            sendThis.put("timestamp", "12");
            sendThis.put("contentType", "preferences");

            JSONObject item = new JSONObject();
            item.put("bus", "false");
            item.put("weather", "false");
            item.put("device", "false");
            item.put("news", "false");
            item.put("postits", "false");
            item.put("clock", "false");
            item.put("greetings", "false");

            JSONArray jArray = new JSONArray();
            jArray.add(0, item);
            sendThis.put("content", jArray);
            messageString = sendThis.toJSONString();

        } catch (Exception e) {
            return "Warning: did not publish";
        }
        return messageString;
    }

    @SuppressWarnings("unchecked")
    private String sendPreferenceShowOnly(String i)
    {
        String messageString;
        try {
            JSONObject sendThis = new JSONObject();
            sendThis.put("messageFrom", "test");
            sendThis.put("timestamp", "12");
            sendThis.put("contentType", "preferences");

            JSONObject item = new JSONObject();
            item.put("showOnly", i);

            JSONArray jArray = new JSONArray();
            jArray.add(0, item);
            sendThis.put("content", jArray);
            messageString = sendThis.toJSONString();

        } catch (Exception e) {
            return "Warning: did not publish";
        }
        return messageString;
    }
}
