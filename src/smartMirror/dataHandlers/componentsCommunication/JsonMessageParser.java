package smartMirror.dataHandlers.componentsCommunication;

import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.applicationModels.Settings;
import smartMirror.dataModels.widgetsModels.devicesModels.Device;
import smartMirror.dataModels.widgetsModels.postItsModels.PostItAction;
import smartMirror.dataModels.widgetsModels.postItsModels.PostItNote;
import smartMirror.dataModels.widgetsModels.shoppingListModels.ShoppingList;
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
    private LinkedList<Device> deviceList;
    private LinkedList<Preferences> preferencesList;
    private ShoppingList shoppingList;
    private Settings settings;

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

    public boolean parsePairing()
    {
        return this.contentType.equals("pairing");
    }

    public PostItNote parsePostIt()
    {
        try
        {
            if (getContentType().equals("post-it"))
            {
                JSONParser parser = new JSONParser();
                JSONArray jsonArray;
                JSONObject jso;
                String postItId;

                jsonArray = (JSONArray) parser.parse(this.content);
                jso = (JSONObject) parser.parse(jsonArray.get(0).toString());
                postItId = jso.get("postItID").toString();
                String bodyText = jso.get("body").toString();
                String senderId = jso.get("senderStyle").toString();
                int timestamp = Integer.parseInt(jso.get("expiresAt").toString());

                return new PostItNote(postItId, bodyText, senderId, timestamp);
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public PostItAction parsePostItAction()
    {
        try
        {
            if (getContentType().equals("postIt action"))
            {
                JSONParser parser = new JSONParser();
                JSONArray jsonArray;
                JSONObject jso;
                String postItId;

                jsonArray = (JSONArray) parser.parse(this.content);
                jso = (JSONObject) parser.parse(jsonArray.get(0).toString());
                postItId = jso.get("postItID").toString();
                String action = jso.get("action").toString();
                String modification = jso.get("modification").toString();

                return new PostItAction(postItId, action, modification);
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public LinkedList parseDeviceList()
    {
        if (getContentType().equals("device"))
        {
            this.deviceList = new LinkedList<>();
            parseArray(this.deviceList, getContentType());

            return this.deviceList;
        }

        return null;
    }

    public Settings parseSettings()
    {
        if (getContentType().equals("settings"))
        {
            parseArray(null, getContentType());

            return this.settings;
        }

        return null;
    }

    public LinkedList parsePreferenceList()
    {
        if (getContentType().equals("preferences"))
        {
            this.preferencesList = new LinkedList<>();
            parseArray(this.preferencesList, getContentType());

            return this.preferencesList;
        }

        return null;
    }


    public ShoppingList parseShoppingList()
    {
        if (getContentType().equals("shoppinglist"))
        {
            this.shoppingList = new ShoppingList();
            parseArray(null, getContentType());

            return this.shoppingList;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private void parseArray(LinkedList linkedList, String type)
    {
        try
        {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(this.content);
            String s = jsonArray.get(0).toString();
            JSONObject jso = (JSONObject) parser.parse(s);
            ArrayList<String> arrayList = new ArrayList<>(jso.keySet());
            Device device;
            Preferences preferences;
            for (String anArrayList : arrayList)
            {
                String value = jso.get(anArrayList).toString();
                switch (type)
                {
                    case "device":
                        device = new Device(anArrayList, value);
                        linkedList.add(device);
                        break;
                    case "settings":
                        settings = new Settings(anArrayList, value);
                        break;
                    case "preferences":
                        preferences = new Preferences(anArrayList, value);
                        linkedList.add(preferences);
                        break;
                    case "shoppinglist":
                        this.shoppingList.setItemList(anArrayList, value);
                        break;
                    default:
                        System.out.println("Could Not be Parsed!");
                        break;
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

    public Device getDeviceList()
    {
        return this.deviceList.remove();
    }
}
