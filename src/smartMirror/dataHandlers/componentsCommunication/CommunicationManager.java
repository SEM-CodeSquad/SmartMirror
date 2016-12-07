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

package smartMirror.dataHandlers.componentsCommunication;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;
import smartMirror.controllers.mainController.MainController;
import smartMirror.dataHandlers.commons.JsonMessageParser;
import smartMirror.dataHandlers.commons.MQTTClient;
import smartMirror.dataHandlers.commons.SmartMirror_Publisher;
import smartMirror.dataHandlers.commons.SmartMirror_Subscriber;

import java.util.Observable;
import java.util.Observer;

/**
 * @author CodeHigh @copyright on 06/12/2016.
 *         Class responsible for stablishing the MQTT connection, receiving the messages for the subscribed topics and notifies
 *         its observes for each received message
 */
public class CommunicationManager extends Observable implements Observer
{
    private String clientId;
    private MQTTClient mqttClient;
    private SmartMirror_Publisher publisher;
    private boolean clientPaired;

    /**
     * Constructor method, it takes the client Id and use it as the Id to connect in the broker, then it publishes a presence
     * message, register the mirror Id in the shopping list server and subscribes to the pairing topic
     *
     * @param clientId string containing the UUID for the client
     */
    public CommunicationManager(String clientId)
    {
        this.clientId = clientId;
        this.mqttClient = new MQTTClient("tcp://codehigh.ddns.me", clientId);
        this.publisher = new SmartMirror_Publisher(this.mqttClient);
        this.publisher.publishPresenceMessage("1", "CodeHigh", 6, "1.0", this.clientId, "1", "6", "10", "12", "22");
        registerOnShoppingListServer();
        setClientPaired(false);
        listenPairing();
    }

    /**
     * Method responsible for subscribing to a topic and setting this class as the callback where the messages for this
     * topic will be arriving
     *
     * @param topic topic to be subscribed
     */
    private void listenSubscription(String topic)
    {
        try
        {
            Thread thread = new Thread(() ->
            {
                SmartMirror_Subscriber subscriber = new SmartMirror_Subscriber(this.mqttClient, topic);
                subscriber.addObserver(this);
            });
            thread.start();
            System.out.println("Listening to: " + topic);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method that subscribes to the pairing topic
     */
    private void listenPairing()
    {
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/pairing");
    }

    /**
     * Method responsible to subscribing to all topics used by the application
     */
    private void paired()
    {
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/postit");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/settings");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/device");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/preferences");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/shoppingList");
    }

    /**
     * Method responsible for publishing the echo message
     *
     * @param msg     message to be published
     * @param success b
     */
    private synchronized void publishEcho(String msg, boolean success)
    {
        if (!success)
        {
            publisher.echo("The post-it table " + msg + "is full! Please" +
                    "delete a post-it in order to add a new one.");
        }
        else
        {
            publisher.echo("The action was successfully received in the SmartMirror!");
        }
    }

    /**
     * Method responsible for registering the client Id in the shopping list server
     */
    @SuppressWarnings("unchecked")
    private void registerOnShoppingListServer()
    {
        Thread thread = new Thread(() ->
        {
            try
            {
                JSONObject sendThis = new JSONObject();
                sendThis.put("request", "register");

                JSONObject data = new JSONObject();
                data.put("email", this.clientId + "@smartmirror.com");
                data.put("password", this.clientId);
                data.put("name", this.clientId);

                sendThis.put("data", data.toJSONString());

                this.publisher.publish("Gro", sendThis.toJSONString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    /**
     * Method responsible of sending notifications to the observers
     *
     * @param msg message to be forwarded
     */
    private synchronized void notifyMessageReceived(MqttMessage msg)
    {
        setChanged();
        notifyObservers(msg);
    }

    /**
     * Method to identify if the client has already been paired to at least one device
     *
     * @return true if paired or false if not paired
     */
    private boolean isClientPaired()
    {
        return clientPaired;
    }

    /**
     * Method to set the boolean flag
     *
     * @param clientPaired boolean flag
     */
    private void setClientPaired(boolean clientPaired)
    {
        this.clientPaired = clientPaired;
    }

    /**
     * Update method where the observable classes sends notifications messages
     *
     * @param o   observable object
     * @param arg object arg
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof MqttMessage)
        {
            Thread thread = new Thread(() ->
            {
                System.out.println("Msg: " + arg.toString());
                notifyMessageReceived((MqttMessage) arg);
                JsonMessageParser parser = new JsonMessageParser();
                parser.parseMessage(arg.toString());
                if (parser.parsePairing() && !isClientPaired())
                {
                    paired();
                    setClientPaired(true);
                }
            });
            thread.start();
        }
        else if (arg instanceof MainController)
        {
            Thread thread = new Thread(() ->
            {
                setChanged();
                notifyObservers(this.mqttClient);
            });
            thread.start();
        }
    }
}
