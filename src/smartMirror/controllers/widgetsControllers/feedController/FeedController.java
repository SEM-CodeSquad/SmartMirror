package smartMirror.controllers.widgetsControllers.feedController;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import smartMirror.dataHandlers.commons.JsonMessageParser;
import smartMirror.dataHandlers.commons.TimeNotificationControl;
import smartMirror.dataHandlers.commons.MQTTClient;
import smartMirror.dataHandlers.widgetsDataHandlers.feed.MarqueePane;
import smartMirror.dataHandlers.widgetsDataHandlers.feed.RSSStAXParser;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.applicationModels.Settings;
import smartMirror.dataModels.widgetsModels.feedModels.NewsSource;
import smartMirror.dataModels.widgetsModels.feedModels.RSSMarqueeMessage;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Pucci @copyright on 06/12/2016.
 *         Class responsible of making the updates in the FeedsView
 */
public class FeedController implements Observer
{
    public GridPane feedPane;
    public Label notification;
    private MarqueePane marqueePane;
    private String newsSource;

    private MQTTClient mqttClient;

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

            monitorWidgetVisibility(parentPane, parentGrid);
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
     * @param gridPane  parent parent component
     */
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
                JsonMessageParser parser = new JsonMessageParser();
                parser.parseMessage(arg.toString());

                if (parser.getContentType().equals("settings"))
                {

                    Settings settings = parser.parseSettings();
                    if (settings.getObject() instanceof NewsSource)
                    {
                        setParentVisible();
                        enforceView();
                        setNewsSource(((NewsSource) settings.getObject()).getNewsSource());
                    }
                }
                else if (parser.getContentType().equals("preferences"))
                {
                    LinkedList<Preferences> list = parser.parsePreferenceList();

                    list.stream().filter(pref -> pref.getName().equals("news")).forEach(pref ->
                            setVisible(pref.getValue().equals("true")));
                }
            });
            thread.start();

        }
        else if (arg instanceof MQTTClient)
        {
            this.mqttClient = (MQTTClient) arg;
        }
    }
}