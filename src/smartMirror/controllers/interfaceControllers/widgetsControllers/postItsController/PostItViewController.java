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

package smartMirror.controllers.interfaceControllers.widgetsControllers.postItsController;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import smartMirror.dataModels.animations.TransitionAnimation;
import smartMirror.controllers.dataHandlers.dataHandlersCommons.*;
import smartMirror.dataModels.database.MysqlCon;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.modelCommons.MQTTClient;
import smartMirror.dataModels.modelCommons.SmartMirror_Publisher;
import smartMirror.dataModels.modelCommons.Timestamp;
import smartMirror.dataModels.widgetsModels.postItsModels.PostItAction;
import smartMirror.dataModels.widgetsModels.postItsModels.PostItNote;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

/**
 * @author CodeHigh on 22/11/2016.
 *         Class responsible for updating the PostItView
 */
public class PostItViewController extends Observable implements Observer
{
    public StackPane postPanes;

    private StackPane[] panes;

    private TransitionAnimation animation;

    private SmartMirror_Publisher publisher;

    private boolean visible = false;

    private Timer timer;

    /**
     * Constructor responsible for calling build
     */
    public PostItViewController()
    {
        Platform.runLater(this::build);
    }

    /**
     * Method responsible for loading each post-it table for each color in the post-it interface, then it starts the time monitoring
     *
     * @see TimeNotificationControl
     * @see PostItsController
     */
    private void build()
    {
        this.postPanes.visibleProperty().addListener((observableValue, aBoolean, aBoolean2) ->
                visible = this.postPanes.isVisible());

        PostItsController standardController = loadView("/smartMirror/Views/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        StackPane standard = standardController.getPostPane();
        setOpacity(standard, false);
        standardController.setTableColor("standard");
        this.addObserver(standardController);
        standardController.addObserver(this);

        PostItsController blueController = loadView("/smartMirror/Views/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        StackPane blue = blueController.getPostPane();
        setOpacity(blue, false);
        blueController.setTableColor("blue");
        this.addObserver(blueController);
        blueController.addObserver(this);

        PostItsController greenController = loadView("/smartMirror/Views/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        StackPane green = greenController.getPostPane();
        setOpacity(green, false);
        greenController.setTableColor("green");
        this.addObserver(greenController);
        greenController.addObserver(this);

        PostItsController purpleController = loadView("/smartMirror/Views/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        StackPane purple = purpleController.getPostPane();
        setOpacity(purple, false);
        purpleController.setTableColor("purple");
        this.addObserver(purpleController);
        purpleController.addObserver(this);

        PostItsController orangeController = loadView("/smartMirror/Views/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        StackPane orange = orangeController.getPostPane();
        setOpacity(orange, false);
        orangeController.setTableColor("orange");
        this.addObserver(orangeController);
        orangeController.addObserver(this);

        PostItsController yellowController = loadView("/smartMirror/Views/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        StackPane yellow = yellowController.getPostPane();
        setOpacity(yellow, false);
        yellowController.setTableColor("yellow");
        this.addObserver(yellowController);
        yellowController.addObserver(this);

        PostItsController pinkController = loadView("/smartMirror/Views/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        StackPane pink = pinkController.getPostPane();
        setOpacity(pink, false);
        pinkController.setTableColor("pink");
        this.addObserver(pinkController);
        pinkController.addObserver(this);

        this.animation = new TransitionAnimation();
        this.animation.transitionAnimation(15, 5, standard, blue, green, purple, orange,
                pink, yellow);
        this.animation.playSeqAnimation();

        this.panes = new StackPane[]{standard, blue, green, purple, orange,
                pink, yellow};

        TimeNotificationControl notificationControl = new TimeNotificationControl();
        notificationControl.addObserver(this);
        notificationControl.bind("HH:mm:ss", 1, "post-it");
    }

    /**
     * Method responsible for stopping the animation and showing the user desired table color, after 30000 milliseconds
     * the animation resumes
     *
     * @param colourIndex index for the desired color
     * @see TransitionAnimation
     */
    private synchronized void showSpecificTable(String colourIndex)
    {
        int index = Integer.parseInt(colourIndex);

        if (!(index > panes.length))
        {
            this.animation.pauseSeqAnimation();
            this.animation.getSequentialTransition().getCurrentTime();

            for (int i = 0; i < panes.length; i++)
            {
                if (i != index)
                {
                    panes[i].setOpacity(0);
                }
                else
                {
                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), panes[index]);

                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);
                    fadeIn.play();
                }
            }
            Platform.runLater(() ->
            {

            });
            timer = new Timer();
            timer.schedule(new TimerTask()
            {

                @Override
                public void run()
                {
                    Platform.runLater(() ->
                    {
                        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), panes[index]);

                        fadeOut.setFromValue(1);
                        fadeOut.setToValue(0);

                        fadeOut.setOnFinished(event -> animation.playSeqAnimation());
                        fadeOut.play();
                    });
                }
            }, 30000);
        }
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
            this.postPanes.setVisible(b);
            StackPane parentPane = (StackPane) this.postPanes.getParent();
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
            showing = widget.isVisible();
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
            StackPane sPane = (StackPane) this.postPanes.getParent();

            ObservableList<Node> list = sPane.getChildren();
            for (Node node : list)
            {
                node.setVisible(false);
            }

            this.postPanes.setVisible(true);
        }
    }

    /**
     * Method responsible for setting the widget holder visible
     */
    private synchronized void setParentVisible()
    {
        Platform.runLater(() ->
        {
            GridPane gridPane = (GridPane) this.postPanes.getParent().getParent();
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
     * Method responsible for loading components in the post-it interface. It loads the FXML file
     *
     * @param resource FXML path resource
     * @return FXMLLoader
     */
    private FXMLLoader loadView(String resource)
    {
        FXMLLoader myLoader = null;
        try
        {
            myLoader = new FXMLLoader(getClass().getResource(resource));
            myLoader.setController(new PostItsController());
            Parent loadScreen = myLoader.load();
            this.postPanes.getChildren().add(loadScreen);
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return myLoader;
    }

    /**
     * Method responsible for changing the opacity of a component
     *
     * @param stackPane component to change the opacity
     * @param b         true for visible or false for not visble
     */
    private void setOpacity(StackPane stackPane, boolean b)
    {
        if (b)
        {
            stackPane.setOpacity(1.0);
        }
        else
        {
            stackPane.setOpacity(0.0);
        }
    }

    /**
     * Method responsible for notifying the observers
     *
     * @param arg object to notify
     */
    private synchronized void notifyObs(Object arg)
    {
        setChanged();
        notifyObservers(arg);
    }

    /**
     * Method responsible for deleting post-its form the database. It deletes any post-it with timestamp smaller than
     * the provided one
     *
     * @param timestamp current timestamp
     */
    private synchronized void deleteFromDB(Long timestamp)
    {
        try
        {
            MysqlCon mysqlCon = new MysqlCon();
            mysqlCon.dbConnect();
            Connection c = mysqlCon.getCon();
            String query = "delete from Postits where Timestamp< '" + timestamp + "' ";
            PreparedStatement psPost = c.prepareStatement(query);
            psPost.executeUpdate();
            psPost.close();

            mysqlCon.closeConnection();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method that sends the echo message
     * @param msg message to be send
     */
    private synchronized void publishEcho(String msg)
    {
        this.publisher.echo(msg);
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
        if (arg instanceof MqttMessage)
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

                switch (parser.getContentType())
                {
                    case "post-it":

                        PostItNote postItNote = parser.parsePostIt();
                        setParentVisible();
                        enforceView();
                        notifyObs(postItNote);
                        break;
                    case "post-it action":

                        PostItAction postItAction = parser.parsePostItAction();
                        notifyObs(postItAction);
                        break;
                    case "preferences":

                        LinkedList<Preferences> list = parser.parsePreferenceList();
                        for (Preferences pref : list)
                        {
                            if (pref.getName().equals("postits"))
                            {
                                setVisible(pref.getValue().equals("true"));
                                publisher.echo("Post-it preference changed");
                            }
                            else if (pref.getName().equals("showOnly"))
                            {
                                if (timer != null) timer.cancel();
                                showSpecificTable(pref.getValue());
                                publisher.echo("Showing your requested post-it table in 30 seconds it will resume " +
                                        "the animation");
                            }
                        }
                        break;
                    default:
                        break;
                }
            });
            thread.start();

        }
        else if (arg instanceof Timestamp)
        {
            Timestamp timestamp = (Timestamp) arg;
            Thread thread = new Thread(() ->
                    notifyObs(timestamp));
            Thread thread1 = new Thread(() ->
                    deleteFromDB(timestamp.getTimestamp()));
            thread1.start();
            thread.start();
        }
        else if (arg instanceof MQTTClient)
        {
            MQTTClient mqttClient = (MQTTClient) arg;
            this.publisher = new SmartMirror_Publisher(mqttClient);
        }
        else if (arg instanceof String)
        {
            Thread thread = new Thread(() -> publishEcho((String) arg));
            thread.start();
        }
    }
}
