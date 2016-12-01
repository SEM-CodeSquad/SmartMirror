package controllers.widgetsControllers.feedController;

import dataHandlers.widgetsDataHandlers.feed.RSSStAXParser;
import dataHandlers.widgetsDataHandlers.feed.MarqueePane;
import dataModels.applicationModels.Preferences;
import dataModels.widgetsModels.feedModels.NewsSource;
import dataModels.widgetsModels.feedModels.RSSMarqueeMessage;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Observable;
import java.util.Observer;

/**
 *
 */
public class FeedController implements Observer {
    public GridPane feedPane;
    public Label notification;
    private MarqueePane marqueePane;
    private String newsSource;

    /**
     *
     */
    public FeedController() {
        Platform.runLater(this::setUpNewsFeed);
    }

    private synchronized void setVisible(boolean b) {
        Platform.runLater(() -> this.feedPane.setVisible(b));
        if (b && this.newsSource != null) {
            this.setNewsSource(this.newsSource);
        }
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
        RSSStAXParser rssStAXParser = new RSSStAXParser();
        rssStAXParser.addObserver(this);
        rssStAXParser.setUrl(newsSource.toUpperCase());
    }

    /**
     *
     */
    private void setUpNewsFeed() {
        this.marqueePane = new MarqueePane(feedPane.getWidth(), feedPane.getHeight() / 2);
        this.feedPane.add(this.marqueePane, 0, 0);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof RSSMarqueeMessage) {
            RSSMarqueeMessage marqueeMessage = (RSSMarqueeMessage) arg;
            this.marqueePane.stop();
            this.marqueePane.setText(marqueeMessage.getMsg());
            this.marqueePane.setEffect();
            this.marqueePane.play();
        } else if (arg.equals("Update Weather and News") && this.feedPane.isVisible()
                && this.newsSource != null) {
            this.setNewsSource(this.newsSource);
        } else if (arg instanceof Preferences && ((Preferences) arg).getName().equals("widget2")) {
            Thread thread = new Thread(() -> setVisible(((Preferences) arg).getValue().equals("true")));
            thread.start();
        } else if (arg instanceof NewsSource) {
            Thread thread = new Thread(() -> {
                NewsSource newsSource = (NewsSource) arg;
                setNewsSource(newsSource.getNewsSource());
            });
            thread.start();

        }
    }
}