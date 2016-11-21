package widgets;


import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.*;
import javafx.util.Duration;

import java.awt.*;

import static com.sun.javafx.tk.Toolkit.getToolkit;
import static dataHandlers.RSSStAXParser.NewsToString;

/**
 * Created by Geoffrey on 2016/11/14.
 */


public class RSSMarquee {


    public GridPane root;
    public String News = "ABC";


    public RSSMarquee(GridPane GridPane, String News) {
        this.News = NewsToString(News);
        this.root = GridPane;
        Platform.runLater(this::setUp);
    }


    public void setUp() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double size = screenSize.getWidth();

        Text NewsFeed = TextBuilder.create()
                .layoutX(size)
                .textOrigin(VPos.TOP)
                .textAlignment(TextAlignment.JUSTIFY)
                .fill(Color.WHITE)
                .font(Font.font("Microsoft YaHei", FontPosture.REGULAR, 20))
                .build();

        NewsFeed.setText(News);

        float width = getToolkit().getFontLoader().computeStringWidth(News, NewsFeed.getFont());
        TranslateTransition transition = TranslateTransitionBuilder.create()
                .node(NewsFeed)
                .duration(new Duration((int) width * 10))
                .interpolator(Interpolator.LINEAR)
                .toX(NewsFeed.getBoundsInLocal().getMaxX() * -1 - 500)
                .fromX(root.widthProperty().get() + 1000)
                .cycleCount(Timeline.INDEFINITE)
                .build();

        Group myGroup = GroupBuilder.create()
                .children(NewsFeed)
                .build();

        this.root.add(NewsFeed, 0, 0);
        this.root.getChildren().add(myGroup);

        transition.play();

    }

    public GridPane getGrid() {
        return this.root;
    }

}