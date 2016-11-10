package Test;

/**
 * Created by Geoffrey on 2016/11/10.
 */


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class Retrievedata  {
    String Returnthis;
    String topic;
    HttpRequestSender post;
    String myUrl = "http://codehigh.ddns.me:5000/";

    protected String Retrievedata(String... args) {
        try{
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date()); // Now use today date.
            c.add(Calendar.DATE, 7); // Adding 5 days
            String output = dateFormat.format(c.getTime());

            JSONObject sendthis = new JSONObject();
            sendthis.put("messageFrom", "test");
            sendthis.put("timestamp", "12");

            // Please use this format when passing around a JSON obj
            // 2 diffrent outcomes if its a postit we publish to a diffrent topic if its a config we publish to a dif topic with a dif jsonobj
            if(args[1].equals("postit")) {
                JSONObject item = new JSONObject();
                sendthis.put("contentType", "post-it");
                item.put("postItID", args[6]);
                item.put("body", args[2]);
                item.put("senderStyle", args[3]);
                item.put("important", args[4]);
                item.put("expiresAt", "12");
                JSONArray jArray = new JSONArray();
                jArray.add(0, item);
                topic = "dit029/SmartMirror/" + args[0] + "/" + args[1];
                sendthis.put("content", jArray);
                String messagestring = sendthis.toJSONString();
                post = new HttpRequestSender("codehigh.ddns.me", "new client", topic, messagestring);
            }

            post.executePost(myUrl);
            System.out.println(post.getHttpResponse());
            Returnthis = post.getHttpResponse(); //execute a post http request with httprequestsenderclass
            return post.getHttpResponse();

        }
        catch(Exception e)
        {
            return "Warning: did not publish";
        }

    }


    protected void onPostExecute(String result) {

    }
}
