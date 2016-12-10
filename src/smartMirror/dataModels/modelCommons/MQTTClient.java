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

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MQTTClient
{
    private MqttConnectOptions options;
    private MqttClient client;
    private String clientId;

    /**
     * Constructor sets the options for the connection, sets the hostname of the broker and client id, then connects
     *
     * @param url broker hostname
     * @param id  client id
     */
    public MQTTClient(String url, String id)
    {
        try
        {
            this.clientId = id;
            options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setKeepAliveInterval(100);
            options.setConnectionTimeout(100);
            client = new MqttClient(url, id);
            client.connect(options);
            System.out.println("Client Connected!");
        }
        catch (MqttException e)
        {
            e.printStackTrace();
            disconnect();
            reconnect();
        }
    }

    /**
     * Method that publishes the last will and ends the connection with the broker
     */
    public void disconnect()
    {
        if (this.client.isConnected())
        {
            System.out.println("Disconnecting...");
            try
            {
                String topic = "presence/" + this.clientId;
                byte[] emptyArray = new byte[0];
                this.client.publish(topic, emptyArray, 1, true);

                client.disconnect();
            }
            catch (MqttException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method responsible for reestablishing the connection
     */
    public void reconnect()
    {
        if (!client.isConnected())
        {
            try
            {
                client.connect(options);
            }
            catch (MqttException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Getter method that provides the MQTT client
     *
     * @return mqtt client
     */
    public MqttClient getClient()
    {
        return client;
    }

    /**
     * Getter method that provide the client id of the connected client
     *
     * @return client id of the connected mqtt client
     */
    String getClientId()
    {
        return this.clientId;
    }
}
