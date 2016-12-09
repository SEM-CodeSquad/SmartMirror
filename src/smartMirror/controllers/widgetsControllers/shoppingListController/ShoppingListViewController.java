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

package smartMirror.controllers.widgetsControllers.shoppingListController;

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
import smartMirror.dataHandlers.commons.JsonMessageParser;
import smartMirror.dataHandlers.commons.MQTTClient;
import smartMirror.dataHandlers.commons.SmartMirror_Publisher;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.widgetsModels.shoppingListModels.ShoppingList;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author CodeHigh on 02/12/2016.
 *         Class responsible for updating the ShoppingListView
 */
public class ShoppingListViewController extends Observable implements Observer
{
    public GridPane shoppingListGrid;
    public StackPane shoppingListPane;

    private SmartMirror_Publisher publisher;

    private boolean visible = false;

    /**
     * Constructor that calls build
     */
    public ShoppingListViewController()
    {
        Platform.runLater(this::build);
    }

    /**
     * Method responsible for loading the shopping list table component and adding its controller as observer for this class
     *
     * @see ShoppingListController
     */
    private void build()
    {
        this.shoppingListGrid.visibleProperty().addListener((observableValue, aBoolean, aBoolean2) ->
                visible = shoppingListGrid.isVisible());

        ShoppingListController shoppingListController = loadView("/smartMirror/Views/widgetsViews/shoppingListWidget/ShoppingListTable.fxml")
                .getController();

        this.addObserver(shoppingListController);
    }

    /**
     * Method responsible for loading components in the shopping list interface. It loads the FXML file
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
            myLoader.setController(new ShoppingListController());
            Parent loadScreen = myLoader.load();
            this.shoppingListPane.getChildren().add(loadScreen);
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return myLoader;
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
            this.shoppingListGrid.setVisible(b);

            StackPane parentPane = (StackPane) this.shoppingListGrid.getParent();
            GridPane parentGrid = (GridPane) parentPane.getParent();

            monitorWidgetVisibility(b, parentGrid);
        });

    }

    /**
     * Method responsible for setting the parent visibility. In case of all the widgets in the parent are not visible
     * the parent also shall be not visible and vice-versa
     *
     * @param b boolean
     * @param gridPane  parent parent component
     */
    private synchronized void monitorWidgetVisibility(boolean b, GridPane gridPane)
    {
        gridPane.setVisible(b);
    }

    /**
     * Method responsible for setting the widget holder visible
     */
    private synchronized void setParentVisible()
    {
        Platform.runLater(() ->
        {
            GridPane gridPane = (GridPane) this.shoppingListGrid.getParent().getParent();
            if (gridPane.getOpacity() != 1)
            {
                gridPane.setVisible(true);
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), gridPane);

                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();
            }
        });
    }

    /**
     * Method responsible to ensure that only one widget is showing at the time in the parent
     */
    private void enforceView()
    {
        if (!visible)
        {
            StackPane sPane = (StackPane) this.shoppingListGrid.getParent();

            ObservableList<Node> list = sPane.getChildren();
            for (Node node : list)
            {
                node.setVisible(false);
            }

            this.shoppingListGrid.setVisible(true);
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
                JsonMessageParser parser = new JsonMessageParser();
                parser.parseMessage(arg.toString());

                if (parser.getContentType().equals("shoppinglist"))
                {
                    setParentVisible();
                    enforceView();
                    ShoppingList shoppingList = parser.parseShoppingList();
                    setChanged();
                    notifyObservers(shoppingList);

                }
                else if (parser.getContentType().equals("preferences"))
                {
                    LinkedList<Preferences> list = parser.parsePreferenceList();

                    list.stream().filter(pref -> pref.getName().equals("shoppinglist")).forEach(pref ->
                            setVisible(pref.getValue().equals("true")));
                    publisher.echo("Shopping list preference changed");
                }
            });
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
