package controllers.widgetsControllers.shoppingListController;

import dataModels.widgetsModels.shoppingListModels.ShoppingList;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * @author Pucci on 02/12/2016.
 */
public class ShoppingListController extends Observable implements Observer, Initializable
{
    @FXML
    public StackPane listTable;

    @FXML
    public GridPane list1;
    @FXML
    public Label item1;

    @FXML
    public GridPane list2;
    @FXML
    public Label item2;

    @FXML
    public GridPane list3;
    @FXML
    public Label item3;

    @FXML
    public GridPane list4;
    @FXML
    public Label item4;

    @FXML
    public GridPane list5;
    @FXML
    public Label item5;

    @FXML
    public GridPane list6;
    @FXML
    public Label item6;

    @FXML
    public GridPane list7;
    @FXML
    public Label item7;

    @FXML
    public GridPane list8;
    @FXML
    public Label item8;

    @FXML
    public GridPane list9;
    @FXML
    public Label item9;

    @FXML
    public GridPane list10;
    @FXML
    public Label item10;

    @FXML
    public GridPane list11;
    @FXML
    public Label item11;

    @FXML
    public GridPane list12;
    @FXML
    public Label item12;

    private GridPane[] gridPanes;
    private Label[] labels;

    private LinkedList<String> shoppingList;

    private int listNumber;

    public int getListNumber()
    {
        return listNumber;
    }

    public void setListNumber(int listNumber)
    {
        this.listNumber = listNumber;
    }

    public LinkedList<String> getShoppingList()
    {
        return shoppingList;
    }

    private synchronized void animationFadeIn(GridPane gridPane)
    {
        Platform.runLater(() ->
        {
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), gridPane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
    }

    private synchronized void animationFadeOut(GridPane gridPane)
    {
        Platform.runLater(() ->
        {
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), gridPane);
            fadeIn.setFromValue(1);
            fadeIn.setToValue(0);
            if (gridPane.getOpacity() == 1) fadeIn.play();
        });
    }

    private synchronized void setItem(String color, String item, GridPane gridPane, Label label)
    {
        Platform.runLater(() ->
        {
            gridPane.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10;");
            if (color.equals("pink") || color.equals("yellow"))
            {
                label.setTextFill(Color.BLACK);
            }
            else
            {
                label.setTextFill(Color.WHITE);
            }
            label.setText(item);
        });
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof ShoppingList && !(shoppingList.size() > 12))
        {
            for (int i = 0; i < gridPanes.length; i++)
            {
                if (i == gridPanes.length && ((ShoppingList) arg).getItemLinkedList().size() > 0)
                {
                    ShoppingList shoppingList = (ShoppingList) arg;
                    setChanged();
                    notifyObservers(shoppingList);

                }
                else if (((ShoppingList) arg).getItemLinkedList().size() == 0)
                {
                    animationFadeOut(gridPanes[i]);

                }
                else if (!shoppingList.contains(((ShoppingList) arg).getItemLinkedList().peek().getItem()))
                {
                    setItem(((ShoppingList) arg).getColor(), ((ShoppingList) arg).getItemLinkedList().remove().getItem(),
                            gridPanes[i], labels[i]);
                    shoppingList.add(((ShoppingList) arg).getItemLinkedList().remove().getItem());
                    animationFadeIn(gridPanes[i]);
                }
                else if (shoppingList.contains(((ShoppingList) arg).getItemLinkedList().peek().getItem()))
                {
                    ((ShoppingList) arg).getItemLinkedList().remove();
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // initialize your logic here: all @FXML variables will have been injected
        shoppingList = new LinkedList<>();

        gridPanes = new GridPane[]{list1, list2, list3, list4, list5, list6, list7, list8, list9, list10, list11, list12};
        labels = new Label[]{item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12};
    }
}
