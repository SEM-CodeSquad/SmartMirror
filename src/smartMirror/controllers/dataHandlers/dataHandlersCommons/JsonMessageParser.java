/*
 * Copyright 2016 CodeHigh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2016 CodeHigh.
 *     Permission is granted to copy, distribute and/or modify this document
 *     under the terms of the GNU Free Documentation License, Version 1.3
 *     or any later version published by the Free Software Foundation;
 *     with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *     A copy of the license is included in the section entitled "GNU
 *     Free Documentation License".
 */

package smartMirror.controllers.dataHandlers.dataHandlersCommons;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.applicationModels.Settings;
import smartMirror.dataModels.widgetsModels.devicesModels.Device;
import smartMirror.dataModels.widgetsModels.postItsModels.PostItAction;
import smartMirror.dataModels.widgetsModels.postItsModels.PostItNote;
import smartMirror.dataModels.widgetsModels.shoppingListModels.ShoppingList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author CodeHigh @copyright on 06/12/2016.
 *         Class responsible for parsing Json MQTT messages
 */
public class JsonMessageParser
{
    private String contentType;
    private String content;
    private String timestamp;
    private ShoppingList shoppingList;
    private Settings settings;

    /**
     * Method responsible for parsing the message
     *
     * @param message message to be parsed
     */
    public void parseMessage(String message)
    {
        try
        {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(message);
            if (json.get("messageFrom") != null)
            {
                this.timestamp = json.get("timestamp").toString();
                this.contentType = json.get("contentType").toString();
                this.content = json.get("content").toString();
            }
            else this.contentType = "external";
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method that identifies if the message was a pairing message
     *
     * @return true or false
     */
    public boolean parsePairing()
    {
        return this.contentType.equals("pairing");
    }

    /**
     * Method identifies if the message was a post-it message, builds a post-it and return it
     *
     * @return PostItNote
     * @see PostItNote
     */
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
                String senderId;
                if (jso.get("senderStyle").toString().equals("none")) senderId = "standard";
                else senderId = jso.get("senderStyle").toString();
                String stamp = jso.get("expiresAt").toString();
                long timestamp;
                if (stamp.equals("0"))
                {
                    timestamp = Instant.now().getEpochSecond() + 7200 * 60;
                }
                else
                {
                    timestamp = Long.parseLong(stamp);
                }

                return new PostItNote(postItId, bodyText, senderId, timestamp);
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Mehtod identifies if the message was a post-it action message, builds a PostItAction and return it
     *
     * @return PostItAction
     * @see PostItAction
     */
    public PostItAction parsePostItAction()
    {
        try
        {
            if (getContentType().equals("post-it action"))
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

    /**
     * Method identifies if the message was a device status message, parses the device list and return it
     *
     * @return Device list
     */
    public LinkedList parseDeviceList()
    {
        if (getContentType().equals("devices"))
        {
            LinkedList<Device> deviceList = new LinkedList<>();
            parseArray(deviceList, getContentType());

            return deviceList;
        }

        return null;
    }

    /**
     * Method identifies if the message was a settings message, parses the settings and return it
     *
     * @return Settings
     * @see Settings
     */
    public Settings parseSettings()
    {
        if (getContentType().equals("settings"))
        {
            parseArray(null, getContentType());

            return this.settings;
        }

        return null;
    }

    /**
     * Method identifies if the message was a indoors temperature sensor message, parses the data and return it
     *
     * @return indoors temperature data
     * @see Settings
     */
    public String parseIndoorsTemp()
    {
        try
        {
            if (getContentType().equals("indoorsTemp"))
            {
                JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray) parser.parse(this.content);
                String s = jsonArray.get(0).toString();
                JSONObject jso = (JSONObject) parser.parse(s);

                return jso.get("indoors").toString();
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method identifies if the message was a preferences message, parses the preferences list and return it
     *
     * @return Preferences list
     * @see Preferences
     */
    public LinkedList parsePreferenceList()
    {
        if (getContentType().equals("preferences"))
        {
            LinkedList<Preferences> preferencesList = new LinkedList<>();
            parseArray(preferencesList, getContentType());

            return preferencesList;
        }

        return null;
    }

    /**
     * Method identifies if the message was a shopping list message, parses the shopping list and return it
     *
     * @return Shopping list
     * @see ShoppingList
     */
    public ShoppingList parseShoppingList()
    {
        if (getContentType().equals("shoppingList"))
        {
            this.shoppingList = new ShoppingList();
            parseArray(null, getContentType());

            return this.shoppingList;
        }

        return null;
    }

    /**
     * Method responsible to parse Json array
     *
     * @param linkedList list to add items
     * @param type       type of the message
     */
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
                    case "devices":
                        if (value.equals("true") || value.equals("false"))
                        {
                            device = new Device(anArrayList, value);
                            linkedList.add(device);
                        }
                        break;
                    case "settings":
                        settings = new Settings(anArrayList, value);
                        break;
                    case "preferences":
                        preferences = new Preferences(anArrayList, value);
                        linkedList.add(preferences);
                        break;
                    case "shoppingList":
                        this.shoppingList.setItemList(value);
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

    /**
     * Getter that provides the content type
     *
     * @return the name of the content type
     */
    public String getContentType()
    {
        return contentType;
    }

    /**
     * Getter that provides the content
     *
     * @return the content
     */
    public String getContent()
    {
        return content;
    }

    /**
     * Getter that provides the timestamp
     *
     * @return the timestamp
     */
    public String getTimestamp()
    {
        return timestamp;
    }

}
