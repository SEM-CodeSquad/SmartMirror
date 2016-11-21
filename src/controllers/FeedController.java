package controllers;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
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

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double size = screenSize.getWidth();


        //noinspection SuspiciousNameCombination
        Text newsFeed = TextBuilder.create()
                .layoutX(size)
                .textOrigin(VPos.TOP)
                .textAlignment(TextAlignment.JUSTIFY)
                .fill(Color.WHITE)
                .font(Font.font("Microsoft YaHei", FontPosture.REGULAR, 20))
                .build();

        newsFeed.setText(this.news);

        float width = getToolkit().getFontLoader().computeStringWidth(this.news, newsFeed.getFont());
        TranslateTransition transition = TranslateTransitionBuilder.create()
                .node(newsFeed)
                .duration(new Duration((int) width * 10))
                .interpolator(Interpolator.LINEAR)
                .toX(newsFeed.getBoundsInLocal().getMaxX() * -1 - 500)
                .fromX(feedPane.widthProperty().get() + 1000)
                .cycleCount(Timeline.INDEFINITE)
                .build();

        Group myGroup = GroupBuilder.create()
                .children(newsFeed)
                .build();

        feedPane.add(newsFeed, 0, 0);
        feedPane.getChildren().add(myGroup);

        transition.play();

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

    }
}