package test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Scanner;

/**
 * @author Pucci on 03/12/2016.
 */
public class TestPairing
{
    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        try
        {

            Scanner scan = new Scanner(System.in);  // Reading from System.in
            System.out.println("Enter the id: ");
            String i = scan.nextLine();
            String topic = "dit029/SmartMirror/" + i + "/pairing";

            JSONObject sendThis = new JSONObject();
            sendThis.put("messageFrom", "test");
            sendThis.put("timestamp", "12");


            JSONObject item = new JSONObject();
            sendThis.put("contentType", "pairing");
            item.put("id", "test");
            JSONArray jArray = new JSONArray();
            jArray.add(0, item);

            sendThis.put("content", jArray);
            String messageString = sendThis.toJSONString();
            //54.154.153.243
            //codehigh.ddns.me
            HttpRequestSender post = new HttpRequestSender("54.154.153.243", topic, messageString, "0", "false");

            String testUrl = "http://localhost:8080/";
            String myUrl = "http://codehigh.ddns.me:8080/";
            post.executePost(myUrl);
            System.out.println(post.getHttpResponse());


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
