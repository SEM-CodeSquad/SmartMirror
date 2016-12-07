package test;

import smartMirror.dataHandlers.commons.MQTTClient;
import smartMirror.dataHandlers.commons.SmartMirror_Publisher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Scanner;

/**
 * @author Pucci on 30/11/2016.
 */
public class DeviceTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter the topic: ");
        String topic = scan.nextLine();

        DeviceTest deviceTest = new DeviceTest();

        MQTTClient client = new MQTTClient("tcp://codehigh.ddns.me", "test");
        SmartMirror_Publisher publisher = new SmartMirror_Publisher(client);
        publisher.publish(topic, deviceTest.sendDevice());
    }

    @SuppressWarnings("unchecked")
    private String sendDevice() {
        String messageString;
        try {
            JSONObject sendThis = new JSONObject();
            sendThis.put("messageFrom", "test");
            sendThis.put("timestamp", "12");
            sendThis.put("contentType", "device");

            JSONObject item = new JSONObject();
            item.put("Living room lamp", "true");
            item.put("Kitchen lamp", "false");
            item.put("Toilet lamp", "false");
            item.put("Bedroom lamp", "true");
            item.put("Stove", "false");
            item.put("TV", "true");
            item.put("Computer", "true");
            item.put("Stereo", "false");
            item.put("Alarm", "false");
            item.put("Microwave", "true");
            item.put("Coffee Machine", "true");
            item.put("Toaster", "false");
            item.put("Apple Tv", "true");
            item.put("PS4", "false");
            item.put("Garage lamp", "false");
            item.put("Hall lamp", "true");
            item.put("Outside lamp", "true");
//            item.put("", "");
//            item.put("", "");
//            item.put("", "");
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