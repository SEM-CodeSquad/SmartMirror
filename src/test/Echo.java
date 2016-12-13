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

package test;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import smartMirror.dataModels.modelCommons.MQTTClient;
import smartMirror.dataModels.modelCommons.SmartMirror_Subscriber;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * @author Pucci on 13/12/2016.
 */
public class Echo implements Observer
{
    private int counter = 0;

    public static void main(String[] args)
    {
        try
        {

            Scanner scan = new Scanner(System.in);  // Reading from System.in
            System.out.println("Enter the id: ");
            String i = scan.nextLine();
            String topic = "dit029/SmartMirror/" + i + "/echo";
            Echo echo = new Echo();
            MQTTClient client = new MQTTClient("tcp://54.154.153.243", "EchoTest");
            SmartMirror_Subscriber subscriber = new SmartMirror_Subscriber(client, topic);
            subscriber.addObserver(echo);
            //54.154.153.243


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof MqttMessage)
        {
            counter++;
            System.out.println("ECHO: " + counter + " " + arg.toString());
        }
    }
}
