package smartMirror.controllers.widgetsControllers.feedController;

import smartMirror.dataHandlers.componentsCommunication.JsonMessageParser;
import smartMirror.dataHandlers.componentsCommunication.TimeNotificationControl;
import smartMirror.dataHandlers.widgetsDataHandlers.feed.MarqueePane;
import smartMirror.dataHandlers.widgetsDataHandlers.feed.RSSStAXParser;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.applicationModels.Settings;
import smartMirror.dataModels.widgetsModels.feedModels.NewsSource;
import smartMirror.dataModels.widgetsModels.feedModels.RSSMarqueeMessage;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 */
public class FeedController implements Observer
{
    public GridPane feedPane;
    public Label notification;
    private MarqueePane marqueePane;
    private String newsSource;

    private boolean visible = false;

    /**
     *
     */
    public FeedController()
    {
        Platform.runLater(this::setUpNewsFeed);
    }

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
            StackPane sPane = (StackPane) this.feedPane.getParent();

            ObservableList<Node> list = sPane.getChildren();
            for (Node node : list)
            {
                node.setVisible(false);
            }

            this.feedPane.setVisible(true);
        }
    }

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

    public void setNewsSource(String newsSource)
    {
        this.newsSource = newsSource;
        RSSStAXParser rssStAXParser = new RSSStAXParser();
        rssStAXParser.addObserver(this);
        rssStAXParser.setUrl(newsSource.toUpperCase());
    }

    /**
     *
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
    }
}