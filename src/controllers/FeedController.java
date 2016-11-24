package controllers;

import com.sun.javafx.tk.*;
import dataHandlers.RSSStAXParser;
import dataModels.MarqueePane;
import dataModels.RSSMarqueeMessage;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.*;
import javafx.util.Duration;
import java.awt.*;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import static com.sun.javafx.tk.Toolkit.getToolkit;

/**
 *
 */
public class FeedController implements Observer {
    public GridPane feedPane;
    public Label notification;
    private String news;
    private MarqueePane marqueePane;

    /**
     *
     */
    public FeedController() {
        this.news = " ";
        Platform.runLater(this::setUpNewsFeed);
    }

    /**
     *
     */
    @SuppressWarnings("deprecation")
    private void setUpNewsFeed() {
        this.marqueePane = new MarqueePane(feedPane.getWidth(), feedPane.getHeight() / 2);
        this.feedPane.add(this.marqueePane, 0, 0);
        RSSStAXParser rssStAXParser = new RSSStAXParser();
        rssStAXParser.addObserver(this);
        rssStAXParser.setUrl("ABC");
    }

    /**
     *
     *
     */
    public void setNews(String news) {
        this.news = news;
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof RSSMarqueeMessage) {
            RSSMarqueeMessage marqueeMessage = (RSSMarqueeMessage) arg;
            this.marqueePane.setText(marqueeMessage.getMsg());
            this.marqueePane.setEffect();
            this.marqueePane.play();
        }
    }
}