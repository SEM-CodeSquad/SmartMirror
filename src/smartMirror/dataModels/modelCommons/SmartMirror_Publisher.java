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

package smartMirror.dataModels.modelCommons;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.Observable;

public class SmartMirror_Publisher extends Observable
{
    private MQTTClient client;
    private Timestamp timestamp;

    /**
     * Constructor
     *
     * @param client mqtt client
     */
    public SmartMirror_Publisher(MQTTClient client)
    {
        this.client = client;
    }

    /**
     * Method responsible for publishing messages to the broker under a specified topic
     *
     * @param topic   topic to publish to
     * @param content the message to be published
     */
    public void publish(String topic, String content)
    {
        try
        {
            byte[] payload = content.getBytes();
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(payload);
            this.client.getClient().publish(topic, mqttMessage);
        }
        catch (MqttException e)
        {
            e.printStackTrace();
            setChanged();
            notifyObservers(this);
        }
    }

    /**
     * Presence message to be published to the broker every time the client is connected
     *
     * @param version       version
     * @param groupName     group name
     * @param groupNumber   group number
     * @param clientVersion software version
     * @param clientId      id of the connected client
     * @param rfcs          implemented rfcs
     */
    @SuppressWarnings({"unchecked", "MismatchedQueryAndUpdateOfCollection"})
    public void publishPresenceMessage(String version, String groupName, int groupNumber, String clientVersion,
                                       String clientId, String... rfcs)
    {
        String gNum = String.valueOf(groupNumber);
        this.timestamp = new Timestamp();
        String ts = String.valueOf(this.timestamp.getTimestamp());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", version);
        jsonObject.put("groupName", groupName);
        jsonObject.put("groupNumber", gNum);
        jsonObject.put("connectedAt", ts);
        JSONArray rfcArray = new JSONArray();
        Collections.addAll(rfcArray, rfcs);
        jsonObject.put("rfcs", rfcArray.toString());
        jsonObject.put("clientVersion", clientVersion);
        jsonObject.put("clientSoftware", "SmartMirror");

        byte[] presenceMessage = jsonObject.toString().getBytes();

        String topic = "presence/" + clientId;
        try
        {
            this.client.getClient().publish(topic, presenceMessage, 1, true);
        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Method echo sends an echo message to a device that has sent messages to this client
     *
     * @param msg echo message to be published
     */
    @SuppressWarnings("unchecked")
    public void echo(String msg)
    {
        String clientID = this.client.getClientId();
        String echoTopic = "dit029/SmartMirror/" + clientID + "/echo";

        timestamp = new Timestamp();
        String ts = String.valueOf(timestamp.getTimestamp());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageFrom", clientID);
        jsonObject.put("timestamp", ts);
        jsonObject.put("contentType", "echo");
        jsonObject.put("content", msg);

        String echoMessage = jsonObject.toString();
        System.err.println("Sending Echo");
        publish(echoTopic, echoMessage);


    }
}



