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

package smartMirror.controllers.interfaceControllers.widgetsControllers.timeDateController;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import smartMirror.controllers.dataHandlers.dataHandlersCommons.JsonMessageParser;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.widgetsModels.dateTimeModels.DateS;
import smartMirror.dataModels.widgetsModels.dateTimeModels.Day;
import smartMirror.dataModels.widgetsModels.dateTimeModels.TimeS;

import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

/**
 * @author CodeHigh @copyright on 06/12/2016.
 *         Class responsible for updating the TimeDateView
 */
public class TimeDateController implements Observer
{
    public GridPane dateTime;
    public Label time;
    public Label date;
    public Label dayName;

    /**
     * Method responsible for setting this widget visible
     *
     * @param b true for visible false for not visible
     */
    private synchronized void setVisible(boolean b)
    {
        Platform.runLater(() ->
        {
            this.dateTime.setVisible(b);
            StackPane parentPane = (StackPane) this.dateTime.getParent();
            GridPane parentGrid = (GridPane) parentPane.getParent();

            monitorWidgetVisibility(parentGrid, parentPane);
        });
    }

    /**
     * Method responsible for setting the parent visibility. In case of all the widgets in the parent are not visible
     * the parent also shall be not visible and vice-versa
     *
     * @param stackPane parent component
     * @param gridPane parent parent component
     */
    private synchronized void monitorWidgetVisibility(GridPane gridPane, StackPane stackPane)
    {
        boolean showing = false;
        List<Node> widgets = stackPane.getChildren();
        for (Node widget : widgets)
        {
            if (widget.isVisible())
            {
                showing = widget.isVisible();
            }
        }

        gridPane.setVisible(showing);
    }

    /**
     * Method that sets the time in the component
     *
     * @param time time to be set
     */
    private void setTime(String time)
    {
        this.time.setText(time);
    }

    /**
     * Method that sets the date tin the component
     *
     * @param date date to be set
     */
    private void setDate(String date)
    {
        this.date.setText(date);
    }

    /**
     * Method that set the day name in the component
     *
     * @param dayName day name to be set
     */
    private void setDayName(String dayName)
    {
        this.dayName.setText(dayName);
    }

    /**
     * Update method where the observable classes sends notifications messages
     *
     * @param o   observable object
     * @param arg object arg
     */
    @Override
    @SuppressWarnings("unchecked")
    public void update(java.util.Observable o, Object arg)
    {

        if (arg instanceof TimeS)
        {
            TimeS now = (TimeS) arg;
            setTime(now.getTime());
        }
        else if (arg instanceof DateS)
        {
            DateS now = (DateS) arg;
            setDate(now.getDate());
        }
        else if (arg instanceof Day)
        {
            Day now = (Day) arg;
            setDayName(now.getDayName());
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

                    list.stream().filter(pref -> pref.getName().equals("clock")).forEach(pref ->
                            setVisible(pref.getValue().equals("true")));
                }
            });
            thread.start();
        }
    }
}
