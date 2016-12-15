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

package smartMirror.controllers.communications;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import smartMirror.controllers.dataHandlers.dataHandlersCommons.JsonMessageParser;
import smartMirror.controllers.interfaceControllers.mainController.MainController;
import smartMirror.dataModels.modelCommons.MQTTClient;
import smartMirror.dataModels.modelCommons.SmartMirror_Publisher;
import smartMirror.dataModels.modelCommons.SmartMirror_Subscriber;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private boolean listAdded;
    private int listRegistered;
    private Timer timer;

    /**
     * Constructor method, it takes the client Id and use it as the Id to connect in the broker, then it publishes a presence
     * message, register the mirror Id in the shopping list server and subscribes to the pairing topic
     *
     * @param clientId string containing the UUID for the client
     */
    public CommunicationManager(String clientId)
    {
        listAdded = false;
        listRegistered = 0;
        this.clientId = clientId;
        //54.154.153.243
        //codehigh.ddns.me
        this.mqttClient = new MQTTClient("tcp://54.154.153.243", clientId);
        this.publisher = new SmartMirror_Publisher(this.mqttClient);
        this.publisher.publishPresenceMessage("1", "CodeHigh", 6, "1.0", this.clientId, "1", "6", "10", "12", "22");

        setClientPaired(false);
        listenPairing();
        registerOnShoppingListServer();

        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(() ->
        {
            if (!mqttClient.getClient().isConnected())
            {
                System.out.println(new Date());
                mqttClient.reconnect();
            }
        }, 0, 60, TimeUnit.SECONDS);
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
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/deviceStatus");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/preferences");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/shoppingList");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/indoorsTemp");
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
                String topic = "Gro/" + this.clientId + "@smartmirror.com/#";
                SmartMirror_Subscriber subscriber = new SmartMirror_Subscriber(this.mqttClient, topic);
                subscriber.addObserver(this);

                System.out.println("Listening to: " + topic);

                String messageString = "{\"request\":\"register\"," +
                        "\"data\":{\"email\":\"" + this.clientId + "@smartmirror.com\"," +
                        "\"password\":\"smartMirror\"," +
                        "\"name\":\"smartMirror\"}}";

                this.publisher.publish("Gro/" + this.clientId + "@smartmirror.com", messageString);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    /**
     * Method responsible for adding the common shopping list for the smartMirror
     */
    private synchronized void addList()
    {
        try
        {
            String addList = "{\"client_id\":\"" + this.clientId + "@smartmirror.com\"," +
                    "\"request\":" + "\"add-list\"," +
                    "\"list\":\"SmartMirror Shopping list\"}";

            this.mqttClient.getClient().publish("Gro/" + this.clientId + "@smartmirror.com", addList.getBytes(),
                    1, false);

            listAdded = true;
            timer = new Timer();
            timer.schedule(new TimerTask()
            {

                @Override
                public void run()
                {
                    addList();
                }
            }, 1000);

        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method responsible for stop listing in the Gro/ topic
     */
    private synchronized void unsubscribeToShoppingList()
    {
        try
        {
            timer.cancel();
            this.mqttClient.getClient().unsubscribe("Gro/" + this.clientId + "@smartmirror.com/#");
            this.publisher.echo("Shopping List to the ID: " + this.clientId + "Added");
        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }
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
                if (arg.toString().equals("{\"reply\":\"done\"}") && listRegistered <= 1)
                {

                    if (!listAdded)
                    {
                        addList();
                    }
                    else if (listRegistered == 1)
                    {
                        unsubscribeToShoppingList();
                    }
                    listRegistered++;

                }
                else if (arg.toString().equals("{\"reply\":\"list_already_exists\"}"))
                {
                    unsubscribeToShoppingList();
                }
                else
                {
                    notifyMessageReceived((MqttMessage) arg);
                    JsonMessageParser parser = new JsonMessageParser();
                    parser.parseMessage(arg.toString());
                    if (parser.parsePairing())
                    {
                        this.publisher.echo("Paired to: " + this.clientId);
                        if (!isClientPaired())
                        {
                            paired();
                            setClientPaired(true);
                        }
                    }
                    else if (parser.getContentType().equals("Create list")
                            && parser.getContent().equals("Create new list " + this.clientId))
                    {
                        String topic = "Gro/" + this.clientId + "@smartmirror.com/#";
                        SmartMirror_Subscriber subscriber = new SmartMirror_Subscriber(this.mqttClient, topic);
                        subscriber.addObserver(this);

                        System.out.println("Listening to: " + topic);
                        listRegistered = 0;
                        listAdded = false;
                        this.publisher.publish("dit029/SmartMirror/" + this.clientId + "/shoppingList",
                                "{\"reply\":\"done\"}");
                    }
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
