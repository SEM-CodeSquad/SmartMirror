package dataHandlers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.LinkedList;

public class JsonMessageParser
{
    private String message;
    private String contentType;
    private String content;
    private String messageFrom;
    private String timestamp;
    private boolean encrypted;
    private String encryptionMethod;
    private LinkedList<DeviceStatus> deviceList;
    private PostitNote postitNote;

    public JsonMessageParser(String message)
    {
       this.message = message;
       deviceList = new LinkedList<>();
    }

    public void parseMessage()
    {
        try
        {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(this.message);
            this.messageFrom = json.get("messageFrom").toString();
            this.timestamp = json.get("timestamp").toString();
            String enc = json.get("encrypted").toString();
            this.encrypted = enc.equals("true");
            this.encryptionMethod = json.get("encryptionMethod").toString();
            this.contentType = json.get("contentType").toString();
            this.content = json.get("content").toString();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void parseContent()
    {
        try
        {
            if (getContentType().equals("postit"))
            {
                JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray) parser.parse(this.content);
                JSONObject jso = (JSONObject) parser.parse(jsonArray.get(0).toString());
                String title = jso.get("Header").toString();
                String bodytext = jso.get("Body").toString();
                String senderId = jso.get("SenderID").toString();
                boolean important = jso.get("important").toString().equals("true");
                int timestamp = Integer.parseInt(jso.get("expiresAt").toString());
                this.postitNote = new PostitNote(title, bodytext, senderId, important, timestamp);
            }
            else
            {
                JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray) parser.parse(this.content);
                String s = jsonArray.get(0).toString();
                JSONObject jso = (JSONObject) parser.parse(s);
                ArrayList<String> arrayList = new ArrayList<>(jso.keySet());
                System.out.println(arrayList.get(0));
                DeviceStatus deviceStatus;
                for (String anArrayList : arrayList) {
                    String status = jso.get(anArrayList).toString();
                    deviceStatus = new DeviceStatus(anArrayList, status);
                    deviceList.add(deviceStatus);
                }
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    public String getMessage()
    {
        return message;
    }

    public String getContentType()
    {
        return contentType;
    }

    public String getContent()
    {
        return content;
    }

    public String getMessageFrom()
    {
        return messageFrom;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public boolean isEncrypted()
    {
        return encrypted;
    }

    public String getEncryptionMethod()
    {
        return encryptionMethod;
    }

    public PostitNote getPostitNote()
    {
        return this.postitNote;
    }

    public LinkedList<DeviceStatus> getDeviceList()
    {
        return deviceList;
    }
}
