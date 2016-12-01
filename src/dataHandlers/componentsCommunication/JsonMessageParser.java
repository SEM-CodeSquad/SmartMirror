package dataHandlers.componentsCommunication;

import dataModels.applicationModels.Preferences;
import dataModels.widgetsModels.devicesModels.Device;
import dataModels.widgetsModels.postItsModels.PostItAction;
import dataModels.widgetsModels.postItsModels.PostItNote;
import dataModels.applicationModels.Settings;
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

    public JsonMessageParser()
    {
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
                    int timestamp = Integer.parseInt(jso.get("expiresAt").toString());
                    PostItNote postItNote = new PostItNote(postItId, bodyText, senderId, timestamp);

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
                    parseArray(null, "settings");
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

                case "preferences":
                    parseArray(null, "preferences");
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
            Preferences preferences;
            for (String anArrayList : arrayList)
            {
                String value = jso.get(anArrayList).toString();
                switch (type) {
                    case "device":
                        device = new Device(anArrayList, value);
                        linkedList.add(device);
                        break;
                    case "settings":
                        settings = new Settings(anArrayList, value);
                        if (value != null) {
                            setChanged();
                            notifyObservers(settings.getObject());
                        }
                        break;
                    case "preferences":
                        preferences = new Preferences(anArrayList, value);
                        setChanged();
                        notifyObservers(preferences);
                        break;
                    default:
                        System.out.println("Could Not be Parsed!");
                        break;
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
}
