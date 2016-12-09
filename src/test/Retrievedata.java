package test;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


class Retrievedata {

    @SuppressWarnings("unchecked")
    String retrieve(String... args) {
        try{

            JSONObject sendThis = new JSONObject();
            sendThis.put("messageFrom", "test");
            sendThis.put("timestamp", "12");


                JSONObject item = new JSONObject();
            sendThis.put("contentType", "post-it");
            item.put("postItID", args[1]);
                item.put("body", args[2]);
                item.put("senderStyle", args[3]);
                item.put("important", args[4]);
                item.put("expiresAt", "12");
                JSONArray jArray = new JSONArray();
                jArray.add(0, item);
            String topic = args[0];
            sendThis.put("content", jArray);
            String messageString = sendThis.toJSONString();
            //54.154.153.243
            //codehigh.ddns.me
            HttpRequestSender post = new HttpRequestSender("54.154.153.243", topic, messageString, "0", "false");

            String testUrl = "http://localhost:8080/";
            String myUrl = "http://codehigh.ddns.me:8080/";
            post.executePost(myUrl);
            return post.getHttpResponse();

        }
        catch(Exception e)
        {
            return "Warning: did not publish";
        }

    }
}
