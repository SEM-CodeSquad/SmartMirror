package dataHandlers;

import dataModels.Device;
import dataModels.PostItAction;
import dataModels.PostItNote;
import dataModels.Settings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

public class JsonMessageParser extends Observable
{
    private String message;
    private String contentType;
    private String content;
    private String messageFrom;
    private String timestamp;
    private LinkedList<Device> deviceList;
    private LinkedList<Device> settingsList;

    public JsonMessageParser()
    {
        this.settingsList = new LinkedList<>();
        this.deviceList = new LinkedList<>();
    }

    public void parseMessage(String message)
    {
        try
        {
            this.message = message;
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(this.message);
            this.messageFrom = json.get("messageFrom").toString();
            this.timestamp = json.get("timestamp").toString();
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
            JSONParser parser = new JSONParser();
            JSONArray jsonArray;
            JSONObject jso;
            String postItId;


            switch (getContentType()) {
                case "post-it":
                    jsonArray = (JSONArray) parser.parse(this.content);
                    jso = (JSONObject) parser.parse(jsonArray.get(0).toString());
                    postItId = jso.get("postItID").toString();
                    String bodyText = jso.get("body").toString();
                    String senderId = jso.get("senderStyle").toString();
                    boolean important = jso.get("important").toString().equals("true");
                    int timestamp = Integer.parseInt(jso.get("expiresAt").toString());
                    PostItNote postItNote = new PostItNote(postItId, bodyText, senderId, important, timestamp);

                    setChanged();
                    notifyObservers(postItNote);
                    break;

                case "device":
                    this.deviceList = new LinkedList<>();
                    parseArray(this.deviceList, "device");
                    setChanged();
                    notifyObservers(this.deviceList);
                    break;

                case "settings":
                    parseArray(this.settingsList, "settings");
                    setChanged();
                    notifyObservers(this.settingsList);
                    break;

                case "pairing":
                    setChanged();
                    notifyObservers("pairing");
                    break;

                case "postIt action":
                    jsonArray = (JSONArray) parser.parse(this.content);
                    jso = (JSONObject) parser.parse(jsonArray.get(0).toString());
                    postItId = jso.get("postItID").toString();
                    String action = jso.get("action").toString();
                    String modification = jso.get("modification").toString();
                    PostItAction postItAction = new PostItAction(postItId, action, modification);
                    setChanged();
                    notifyObservers(postItAction);
                    break;

                default:

                    System.out.println("Could Not be Parsed!");
                    break;

            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    private void parseArray(LinkedList linkedList, String type)
    {
        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(this.content);
            String s = jsonArray.get(0).toString();
            JSONObject jso = (JSONObject) parser.parse(s);
            ArrayList<String> arrayList = new ArrayList<>(jso.keySet());
            Device device;
            Settings settings;
            for (String anArrayList : arrayList)
            {
                String value = jso.get(anArrayList).toString();
                if (type.equals("device")) {
                    device = new Device(anArrayList, value);
                    linkedList.add(device);
                } else if (type.equals("settings")) {
                    settings = new Settings(anArrayList, value);
                    linkedList.add(settings);
                }

            }
        } catch (ParseException e) {
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

    public Device getDeviceList()
    {
        return this.deviceList.remove();
    }

    public Device getSettings()
    {
        return this.settingsList.remove();
    }

}
