package smartMirror.controllers.widgetsControllers.postItsController;

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
import smartMirror.dataHandlers.animations.TransitionAnimation;
import smartMirror.dataHandlers.componentsCommunication.JsonMessageParser;
import smartMirror.dataHandlers.componentsCommunication.TimeNotificationControl;
import smartMirror.dataHandlers.database.MysqlCon;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.applicationModels.Timestamp;
import smartMirror.dataModels.widgetsModels.postItsModels.PostItAction;
import smartMirror.dataModels.widgetsModels.postItsModels.PostItNote;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

/**
 * @author Pucci on 22/11/2016.
 */
public class PostItViewController extends Observable implements Observer
{
    public StackPane postPanes;

    private StackPane[] panes;

    private TransitionAnimation animation;

    private boolean visible = false;

    /**
     *
     */
    public PostItViewController()
    {
        Platform.runLater(this::build);
    }

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
            Timer timer = new Timer();
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

    private synchronized void setVisible(boolean b)
    {
        Platform.runLater(() ->
        {
            this.postPanes.setVisible(b);
            StackPane parentPane = (StackPane) this.postPanes.getParent();
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

    private synchronized void setParentVisible()
    {
        Platform.runLater(() ->
        {
            GridPane gridPane = (GridPane) this.postPanes.getParent().getParent();
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

    private synchronized void notifyObs(Object arg)
    {
        setChanged();
        notifyObservers(arg);
    }

    private synchronized void deleteFromDB(String timestamp)
    {
        try
        {
            MysqlCon mysqlCon = new MysqlCon();
            mysqlCon.dbConnect();
            Connection c = mysqlCon.getCon();
            String query = "delete from Postits where Timestamp= '" + timestamp + "' ";
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

                switch (parser.getContentType())
                {
                    case "post-it":

                        PostItNote postItNote = parser.parsePostIt();
                        setParentVisible();
                        enforceView();
                        System.out.println("post");
                        notifyObs(postItNote);
                        break;
                    case "postIt action":

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
                            }
                            else if (pref.getName().equals("showOnly"))
                            {
                                showSpecificTable(pref.getValue());
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
            Thread thread = new Thread(() ->
            {
                System.out.println("timestamp");
                Timestamp timestamp = (Timestamp) arg;
                notifyObs(timestamp);
            });
            Thread thread1 = new Thread(() ->
                    deleteFromDB(String.valueOf(((Timestamp) arg).getTimestamp())));
            thread1.start();
            thread.start();
        }
        else if (arg instanceof PostItsController)
        {
            Thread thread = new Thread(() ->
            {
                PostItsController postItsController = (PostItsController) arg;
                notifyObs(postItsController);
            });
            thread.start();
        }
        else if (arg.equals("success"))
        {
            Thread thread = new Thread(() -> notifyObs("success"));
            thread.start();
        }
    }
}
