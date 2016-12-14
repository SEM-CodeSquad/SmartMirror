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
            System.out.println("Enter the id: ");
            String i = scan.nextLine();
            String topic = "dit029/SmartMirror/" + i + "/shoppingList";

            JSONObject sendThis = new JSONObject();
            sendThis.put("messageFrom", "test");
            sendThis.put("timestamp", "12");


            JSONObject item = new JSONObject();
            sendThis.put("contentType", "shoppingList");

            System.out.println("Enter the numeber of list: ");
            String listNum = scan.nextLine();

            if (listNum.equals("15"))
            {
                item.put("1", "milk");
                item.put("2", "bread");
                item.put("3", "fruits");
                item.put("4", "juice");
                item.put("5", "rice");
                item.put("6", "beans");
                item.put("7", "meat");
                item.put("8", "tomatoes");
                item.put("9", "onions");
                item.put("10", "potatoes");
                item.put("11", "garlic");
                item.put("12", "butter");
                item.put("13", "cheese");
                item.put("14", "ham");
                item.put("15", "yogurt");
            }
            else
            {
                item.put("1", "milk");
                item.put("2", "bread");
                item.put("3", "fruits");
                item.put("4", "juice");
                item.put("5", "rice");
                item.put("6", "beans");
                item.put("7", "meat");
                item.put("8", "tomatoes");
                item.put("9", "onions");
                item.put("10", "potatoes");
                item.put("11", "garlic");
                item.put("12", "butter");
                item.put("13", "cheese");
                item.put("14", "ham");
                item.put("15", "yogurt");

                item.put("16", "banana");
                item.put("17", "apple");
                item.put("30", "pineapple");
                item.put("44", "kiwi");
                item.put("55", "melon");
                item.put("64", "watermelon");
                item.put("74", "grapes");
                item.put("84", "carrots");
                item.put("94", "cucumber");
                item.put("104", "eggs");
                item.put("114", "frying oil");
                item.put("124", "sugar");
                item.put("134", "salt");
                item.put("144", "pepper");
                item.put("155", "peas");
            }


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


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
