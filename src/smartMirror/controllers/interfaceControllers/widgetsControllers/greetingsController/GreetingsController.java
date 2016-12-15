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

package smartMirror.controllers.interfaceControllers.widgetsControllers.greetingsController;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import smartMirror.controllers.dataHandlers.dataHandlersCommons.JsonMessageParser;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.widgetsModels.greetingsModels.Greetings;
import smartMirror.dataModels.widgetsModels.weatherModels.Weather;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author CodeHigh @copyright on 06/12/2016.
 *         Class responsible for updating the GreetingsView
 */
public class GreetingsController implements Observer
{
    public GridPane greetingsPane;
    public Label greetings;
    public Label secondaryMessage;

    /**
     * Method responsible for setting this widget visible
     *
     * @param b true for visible false for not visible
     */
    private synchronized void setVisible(boolean b)
    {
        Platform.runLater(() ->
        {
            this.greetingsPane.setVisible(b);
            StackPane parentPane = (StackPane) this.greetingsPane.getParent();
            GridPane parentGrid = (GridPane) parentPane.getParent();

            monitorWidgetVisibility(parentPane, parentGrid);
        });
    }

    /**
     * Method responsible for setting the parent visibility. In case of all the widgets in the parent are not visible
     * the parent also shall be not visible and vice-versa
     *
     * @param stackPane parent component
     * @param gridPane  parent parent component
     */
    private synchronized void monitorWidgetVisibility(StackPane stackPane, GridPane gridPane)
    {
        boolean visible = false;
        ObservableList<Node> list = stackPane.getChildren();
        for (Node node : list)
        {
            if (node.isVisible())
            {
                visible = node.isVisible();
            }
        }
        gridPane.setVisible(visible);
    }

    /**
     * Method responsible to set the specified text in the greetings component
     *
     * @param greetings text to be set
     */
    private void setGreetings(String greetings)
    {
        this.greetings.setText(greetings);
    }

    /**
     * Method that sets messages related to the weather to the users
     *
     * @param message weather description
     */
    private synchronized void setSecondaryMessage(String message)
    {
        Platform.runLater(() ->
        {
            if (message.contains("rain"))
            {
                this.secondaryMessage.setText("Better get a umbrella today!");
            }
            else if (message.contains("thunderstorm"))
            {
                this.secondaryMessage.setText("Be prepared for bad weather today!");
            }
            else if (message.contains("drizzle"))
            {
                this.secondaryMessage.setText("It might be a bit wet outside!");
            }
            else if (message.contains("snow"))
            {
                this.secondaryMessage.setText("Better get a warm jacket today!");
            }
            else if (message.contains("mist") || message.contains("fog"))
            {
                this.secondaryMessage.setText("Can you see something outside?");
            }
            else if (message.contains("clear sky"))
            {
                this.secondaryMessage.setText("What amazing day!");
            }
            else if (message.contains("clouds"))
            {
                this.secondaryMessage.setText("The weather is so boring today!");
            }
            else if (message.contains("cold"))
            {
                this.secondaryMessage.setText("Really cold outside!");
            }
            else if (message.contains("hot"))
            {
                this.secondaryMessage.setText("Are you sweating already?");
            }
            else if (message.contains("windy"))
            {
                this.secondaryMessage.setText("Better hold tight today!");
            }
            else if (message.contains("violent storm") || message.contains("hurricane"))
            {
                this.secondaryMessage.setText("If I was you I would stay home today!");
            }
        });
    }

    /**
     * Update method where the observable classes sends notifications messages
     *
     * @param o   observable object
     * @param arg object arg
     */
    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object arg)
    {
        if (arg instanceof Greetings)
        {
            Greetings greetings = (Greetings) arg;
            setGreetings(greetings.getGreetings());
        }
        else if (arg instanceof MqttMessage)
        {
            Thread thread = new Thread(() ->
            {
                JsonMessageParser parser = new JsonMessageParser();
                parser.parseMessage(arg.toString());

                if (parser.getContentType().equals("preferences"))
                {
                    LinkedList<Preferences> list = parser.parsePreferenceList();

                    list.stream().filter(pref -> pref.getName().equals("greetings")).forEach(pref ->
                            setVisible(pref.getValue().equals("true")));
                }

            });
            thread.start();
        }
        else if (arg instanceof Weather)
        {
            Thread thread = new Thread(() -> setSecondaryMessage(((Weather) arg).getDesc()));
            thread.start();
        }
    }
}
