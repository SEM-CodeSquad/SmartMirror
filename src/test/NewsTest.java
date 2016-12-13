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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Scanner;

/**
 * @author Pucci on 09/12/2016.
 */
@SuppressWarnings("unchecked")
public class NewsTest
{
    public static void main(String[] args)
    {
        try
        {

            Scanner scan = new Scanner(System.in);  // Reading from System.in
            System.out.println("Enter the id: ");
            String i = scan.nextLine();
            String topic = "dit029/SmartMirror/" + i + "/settings";

            JSONObject sendThis = new JSONObject();
            sendThis.put("messageFrom", "test");
            sendThis.put("timestamp", "12");


            JSONObject item = new JSONObject();
            sendThis.put("contentType", "settings");
            item.put("news", "SVT");

            JSONArray jArray = new JSONArray();
            jArray.add(0, item);

            sendThis.put("content", jArray);
            String messageString = sendThis.toJSONString();
            //54.154.153.243
            //codehigh.ddns.me
            HttpRequestSender post = new HttpRequestSender("54.154.153.243", topic, messageString, "0", "false");

            String testUrl = "http://localhost:8080/";
            String myUrl = "http://codehigh.ddns.me:8080/";
            post.executePost(myUrl);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
