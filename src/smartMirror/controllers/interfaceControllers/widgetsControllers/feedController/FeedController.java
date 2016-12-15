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

package smartMirror.controllers.interfaceControllers.widgetsControllers.feedController;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import smartMirror.controllers.dataHandlers.dataHandlersCommons.JsonMessageParser;
import smartMirror.controllers.dataHandlers.dataHandlersCommons.TimeNotificationControl;
import smartMirror.controllers.dataHandlers.widgetsDataHandlers.feed.MarqueePane;
import smartMirror.controllers.dataHandlers.widgetsDataHandlers.feed.RSSStAXParser;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.applicationModels.Settings;
import smartMirror.dataModels.modelCommons.MQTTClient;
import smartMirror.dataModels.modelCommons.SmartMirror_Publisher;
import smartMirror.dataModels.widgetsModels.feedModels.NewsSource;
import smartMirror.dataModels.widgetsModels.feedModels.RSSMarqueeMessage;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @author CodeHigh @copyright on 06/12/2016.
 *         Class responsible of making the updates in the FeedsView
 */
public class FeedController implements Observer
{
    public GridPane feedPane;
    private MarqueePane marqueePane;
    private String newsSource;

    private SmartMirror_Publisher publisher;

    private boolean visible = false;

    /**
     * Constructor method responsible for calling the set up
     */
    public FeedController()
    {
        Platform.runLater(this::setUpNewsFeed);
    }

    /**
     * Method responsible for setting this widget visible
     *
     * @param b true for visible false for not visible
     */
    private synchronized void setVisible(boolean b)
    {
        Platform.runLater(() ->
        {
            this.feedPane.setVisible(b);
            StackPane parentPane = (StackPane) this.feedPane.getParent();
            GridPane parentGrid = (GridPane) parentPane.getParent();

            monitorWidgetVisibility(parentGrid, parentPane);
        });

        if (b && this.newsSource != null)
        {
            this.setNewsSource(this.newsSource);
        }
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
     * Method responsible to ensure that only one widget is showing at the time in the parent
     */
    private void enforceView()
    {
        if (!visible)
        {
            StackPane sPane = (StackPane) this.feedPane.getParent();

            ObservableList<Node> list = sPane.getChildren();
            for (Node node : list)
            {
                node.setVisible(false);
            }

            this.feedPane.setVisible(true);
        }
    }

    /**
     * Method responsible for setting the widget holder visible
     */
    private synchronized void setParentVisible()
    {
        Platform.runLater(() ->
        {
            GridPane gridPane = (GridPane) this.feedPane.getParent().getParent();
            gridPane.setVisible(true);
            if (gridPane.getOpacity() != 1)
            {
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), gridPane);

                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();
            }
        });
    }

    /**
     * Method responsible for setting the name of the news source and providing it to the RSSStAXParser
     *
     * @param newsSource news source
     * @see RSSStAXParser
     */
    private void setNewsSource(String newsSource)
    {
        this.newsSource = newsSource;
        RSSStAXParser rssStAXParser = new RSSStAXParser();
        rssStAXParser.addObserver(this);
        rssStAXParser.setUrl(newsSource.toUpperCase());
    }

    /**
     * Method responsible for setting up the marquee pane in the FeedsView, then it starts the time monitoring
     *
     * @see TimeNotificationControl
     */
    private void setUpNewsFeed()
    {
        this.feedPane.visibleProperty().addListener((observableValue, aBoolean, aBoolean2) ->
                visible = feedPane.isVisible());

        this.marqueePane = new MarqueePane(feedPane.getWidth(), feedPane.getHeight() / 2);
        this.feedPane.add(this.marqueePane, 0, 0);

        TimeNotificationControl notificationControl = new TimeNotificationControl();
        notificationControl.addObserver(this);
        notificationControl.bind("HH:mm:ss", 3600, "news");
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
        if (arg instanceof RSSMarqueeMessage)
        {
            RSSMarqueeMessage marqueeMessage = (RSSMarqueeMessage) arg;
            this.marqueePane.stop();
            this.marqueePane.setText(marqueeMessage.getMsg());
            this.marqueePane.setEffect();
            this.marqueePane.play();
        }
        else if (arg.equals("Update Weather and News") && this.feedPane.isVisible()
                && this.newsSource != null)
        {
            this.setNewsSource(this.newsSource);
        }
        else if (arg instanceof MqttMessage)
        {
            Thread thread = new Thread(() ->
            {
                String msg = null;
                try
                {
                    msg = new String(arg.toString().getBytes("ISO-8859-1"), "UTF-8");

                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                JsonMessageParser parser = new JsonMessageParser();
                parser.parseMessage(msg);

                if (parser.getContentType().equals("settings"))
                {

                    Settings settings = parser.parseSettings();
                    if (settings.getObject() instanceof NewsSource)
                    {
                        setParentVisible();
                        enforceView();
                        setNewsSource(((NewsSource) settings.getObject()).getNewsSource());
                        publisher.echo("News source name received");
                    }
                }
                else if (parser.getContentType().equals("preferences"))
                {
                    LinkedList<Preferences> list = parser.parsePreferenceList();

                    list.stream().filter(pref -> pref.getName().equals("news")).forEach(pref ->
                    {
                        setVisible(pref.getValue().equals("true"));
                        publisher.echo("News feed preference changed");
                    });

                }
            });
            thread.start();

        }
        else if (arg instanceof MQTTClient)
        {
            MQTTClient mqttClient = (MQTTClient) arg;
            this.publisher = new SmartMirror_Publisher(mqttClient);
        }
    }
}