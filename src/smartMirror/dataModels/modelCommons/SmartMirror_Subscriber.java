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

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Observable;

public class SmartMirror_Subscriber extends Observable implements MqttCallback
{
    private MQTTClient client;
    private String topic;

    /**
     * Constructor sets this class to be receiving the call back (receiving the messages) and subscribe to a topic
     *
     * @param client mqtt client to be used
     * @param topic  topic to subscribe to
     */
    public SmartMirror_Subscriber(MQTTClient client, String topic)
    {
        try
        {
            this.client = client;
            this.topic = topic;
            client.getClient().setCallback(this);
            client.getClient().subscribe(topic);
        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method responsible to monitor if the connection were lost, in case of lost connection
     * it attempts reconnecting the mqtt client
     *
     * @param throwable all errors and exceptions in the Java language
     */
    public void connectionLost(Throwable throwable)
    {
        try
        {
            System.out.println("Lost Connection");
            throwable.printStackTrace();
            this.client.reconnect();
            client.getClient().setCallback(this);
            client.getClient().subscribe(topic);
        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Method responsible for receiving the messages and forwarding it to its observe
     *
     * @param topic       topic that the message come from
     * @param mqttMessage the received message
     */
    public void messageArrived(String topic, MqttMessage mqttMessage)
    {
        setChanged();
        notifyObservers(mqttMessage);
    }

    /**
     * Method called when delivery for a message has been completed
     *
     * @param iMqttDeliveryToken the delivery token associated with the message
     */
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken)
    {
        // Not used
    }

}


