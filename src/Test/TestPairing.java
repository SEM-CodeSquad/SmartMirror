package Test;

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
            System.out.println("Enter the topic: ");
            String topic = scan.nextLine();

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
            HttpRequestSender post = new HttpRequestSender("codehigh.ddns.me", topic, messageString, "0", "false");

            String testUrl = "http://localhost:8080/";
            String myUrl = "http://codehigh.ddns.me:8080/";
            post.executePost(myUrl);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
