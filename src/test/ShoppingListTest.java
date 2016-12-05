package test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Scanner;

/**
 * @author Pucci on 05/12/2016.
 */
public class ShoppingListTest
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
            sendThis.put("contentType", "shoppinglist");
            item.put("1", "test");
            item.put("2", "test");
            item.put("3", "test");
            item.put("4", "test");
            item.put("5", "test");
            item.put("6", "test");
            item.put("7", "test");
            item.put("8", "test");
            item.put("9", "test");
            item.put("10", "test");
            item.put("11", "test");
            item.put("12", "test");
            item.put("13", "test");
            item.put("14", "test");
            item.put("15", "test");

//            item.put("16", "test");
//            item.put("17", "test");
//            item.put("30", "test");
//            item.put("44", "test");
//            item.put("55", "test");
//            item.put("64", "test");
//            item.put("74", "test");
//            item.put("84", "test");
//            item.put("94", "test");
//            item.put("104", "test");
//            item.put("114", "test");
//            item.put("124", "test");
//            item.put("134", "test");
//            item.put("144", "test");
//            item.put("155", "test");
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
