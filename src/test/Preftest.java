package test;

import smartMirror.dataHandlers.commons.MQTTClient;
import smartMirror.dataHandlers.commons.SmartMirror_Publisher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Scanner;

/**
 * @author Pucci on 30/11/2016.
 */
public class Preftest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter the topic: ");
        String topic = scan.nextLine();

        Preftest preftest = new Preftest();

        MQTTClient client = new MQTTClient("tcp://codehigh.ddns.me", "test");
        SmartMirror_Publisher publisher = new SmartMirror_Publisher(client);
        publisher.publish(topic, preftest.sendPreferenceShow());
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
    private String sendPreferenceShowOnly() {
        String messageString;
        try {
            JSONObject sendThis = new JSONObject();
            sendThis.put("messageFrom", "test");
            sendThis.put("timestamp", "12");
            sendThis.put("contentType", "preferences");

            JSONObject item = new JSONObject();
            item.put("showOnly", "1");

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
