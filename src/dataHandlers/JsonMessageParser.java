package dataHandlers;

import dataModels.Content;
import dataModels.PostItAction;
import dataModels.PostItNote;
import controllers.PairingManager;
import controllers.PostItGuiManager;
import controllers.SettingsManager;
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
    private LinkedList<Content> deviceList;
    private LinkedList<Content> settingsList;
    private LinkedList<Content> pairingList;
    private LinkedList<PostItNote> postItNotes;
    private LinkedList<PostItAction> postItActions;

    public JsonMessageParser(SettingsManager settingsManager, PairingManager authenticationManager,
                             PostItGuiManager postitGuiManager)
    {
        addObserver(settingsManager);
        addObserver(authenticationManager);
        addObserver(postitGuiManager);
        this.postItNotes = new LinkedList<>();
        this.postItActions = new LinkedList<>();
        this.settingsList = new LinkedList<>();
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
            if (this.postItNotes.size() > 0)
            {
                setChanged();
                notifyObservers("post-it");
            }
            else if (this.postItActions.size() > 0)
            {
                setChanged();
                notifyObservers("post-it action");
            }
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
                    this.postItNotes.add(postItNote);
                    setChanged();
                    notifyObservers("post-it");
                    break;

                case "device":
                    this.deviceList = new LinkedList<>();
                    parseArray(this.deviceList);
                    break;

                case "settings":
                    parseArray(this.settingsList);
                    setChanged();
                    notifyObservers("settings");
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
                    this.postItActions.add(postItAction);
                    setChanged();
                    notifyObservers("post-it action");
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
    private void parseArray(LinkedList linkedList)
    {
        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(this.content);
            String s = jsonArray.get(0).toString();
            JSONObject jso = (JSONObject) parser.parse(s);
            ArrayList<String> arrayList = new ArrayList<>(jso.keySet());
            Content content;
            for (String anArrayList : arrayList)
            {
                String value = jso.get(anArrayList).toString();
                content = new Content(anArrayList, value);
                linkedList.add(content);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Content getPairing() {
        return this.pairingList.remove();
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

    public PostItNote getPostItNote()
    {
        return postItNotes.remove();
    }

    public PostItAction getPostItAction()
    {
        return postItActions.remove();
    }

    public Content getDeviceList()
    {
        return this.deviceList.remove();
    }

    public Content getSettings()
    {
        return this.settingsList.remove();
    }

}
