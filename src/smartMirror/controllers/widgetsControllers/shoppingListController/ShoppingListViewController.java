package smartMirror.controllers.widgetsControllers.shoppingListController;

import smartMirror.dataHandlers.animations.TransitionAnimation;
import smartMirror.dataHandlers.componentsCommunication.JsonMessageParser;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.widgetsModels.shoppingListModels.ShoppingList;
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

import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Pucci on 02/12/2016.
 */
public class ShoppingListViewController extends Observable implements Observer
{
    public GridPane shoppingListGrid;
    public StackPane shoppingListPane;

    private boolean visible = false;

    public ShoppingListViewController()
    {
        Platform.runLater(this::build);
    }

    private void build()
    {
        this.shoppingListGrid.visibleProperty().addListener((observableValue, aBoolean, aBoolean2) ->
                visible = shoppingListGrid.isVisible());

        ShoppingListController shoppingListController = loadView("/smartMirror/Views/widgetsViews/shoppingListWidget/ShoppingListTable.fxml")
                .getController();

        this.addObserver(shoppingListController);
    }

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

    private synchronized void setVisible(boolean b)
    {
        Platform.runLater(() ->
        {
            this.shoppingListGrid.setVisible(b);

            StackPane parentPane = (StackPane) this.shoppingListGrid.getParent();
            GridPane parentGrid = (GridPane) parentPane.getParent();

            monitorWidgetVisibility(parentPane, parentGrid);
        });

    }

    private synchronized void monitorWidgetVisibility(StackPane stackPane, GridPane gridPane)
    {
        boolean visible = false;
        ObservableList<Node> list = stackPane.getChildren();
        for (Node node : list)
        {
            visible = node.isVisible();
        }
        gridPane.setVisible(visible);
    }

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
                }
            });
            thread.start();
        }
    }
}
