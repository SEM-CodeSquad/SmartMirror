package smartMirror.controllers.widgetsControllers.shoppingListController;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import smartMirror.dataHandlers.animations.TransitionAnimation;
import smartMirror.dataModels.widgetsModels.shoppingListModels.ShoppingList;

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
    public StackPane table1;

    @FXML
    public StackPane table2;

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

    @FXML
    public GridPane list13;
    @FXML
    public Label item13;

    @FXML
    public GridPane list14;
    @FXML
    public Label item14;

    @FXML
    public GridPane list15;
    @FXML
    public Label item15;

    @FXML
    public GridPane list16;
    @FXML
    public Label item16;

    @FXML
    public GridPane list17;
    @FXML
    public Label item17;

    @FXML
    public GridPane list18;
    @FXML
    public Label item18;

    @FXML
    public GridPane list19;
    @FXML
    public Label item19;

    @FXML
    public GridPane list20;
    @FXML
    public Label item20;

    @FXML
    public GridPane list21;
    @FXML
    public Label item21;

    @FXML
    public GridPane list22;
    @FXML
    public Label item22;

    @FXML
    public GridPane list23;
    @FXML
    public Label item23;

    @FXML
    public GridPane list24;
    @FXML
    public Label item24;

    @FXML
    public GridPane list25;
    @FXML
    public Label item25;

    @FXML
    public GridPane list26;
    @FXML
    public Label item26;

    @FXML
    public GridPane list27;
    @FXML
    public Label item27;

    @FXML
    public GridPane list28;
    @FXML
    public Label item28;

    @FXML
    public GridPane list29;
    @FXML
    public Label item29;

    @FXML
    public GridPane list30;
    @FXML
    public Label item30;

    private GridPane[] gridPanes;
    private Label[] labels;

    private LinkedList<String> itemsList;

    private TransitionAnimation animation;

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

    private synchronized void showOnlyTable1()
    {
        animation.stopSeqAnimation();
        if (table2.getOpacity() == 1) table2.setOpacity(0);
        if (table1.getOpacity() == 0) table1.setOpacity(1);
    }

    private synchronized void play()
    {
        if (table2.getOpacity() == 1) table2.setOpacity(0);
        if (table1.getOpacity() == 1) table1.setOpacity(0);
        animation.getSequentialTransition().playFromStart();
    }

    private synchronized void setItem(String item, Label label)
    {
        Platform.runLater(() ->
                label.setText(item));
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof ShoppingList)
        {
            Thread thread = new Thread(() ->
            {
                ShoppingList shoppingList = (ShoppingList) arg;
                System.out.println(shoppingList.getItemLinkedList().size());
                itemsList = new LinkedList<>();

                for (int i = 0; i < gridPanes.length; i++)
                {
                    System.out.println(itemsList.size() + " i: " + i);
                    if (shoppingList.getItemLinkedList().size() == 0)
                    {
                        animationFadeOut(gridPanes[i]);
                    }
                    else
                    {
                        itemsList.add(shoppingList.getItemLinkedList().peek().getItem());

                        if (itemsList.size() <= 15)
                        {
                            showOnlyTable1();
                            setItem(shoppingList.getItemLinkedList().remove().getItem(), labels[i]);
                            animationFadeIn(gridPanes[i]);
                        }
                        else if (itemsList.size() > 15)
                        {
                            play();
                            setItem(shoppingList.getItemLinkedList().remove().getItem(), labels[i]);
                            animationFadeIn(gridPanes[i]);
                        }
                    }
                }
            });
            thread.start();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // initialize your logic here: all @FXML variables will have been injected

        gridPanes = new GridPane[]
                {list1, list2, list3, list4, list5, list6, list7, list8, list9, list10, list11, list12,
                        list13, list14, list15, list16, list17, list18, list19, list20, list21, list22, list23, list24,
                        list25, list26, list27, list28, list29, list30};
        labels = new Label[]
                {item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12,
                        item13, item14, item15, item16, item17, item18, item19, item20, item21, item22, item23, item24,
                        item25, item26, item27, item28, item29, item30};

        animation = new TransitionAnimation();
        animation.transitionAnimation(5, 5, table1, table2);
    }
}
