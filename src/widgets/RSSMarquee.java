package widgets;

import dataHandlers.RSSFeed;
import dataHandlers.RSSStAXParser;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;

import static com.sun.javafx.tk.Toolkit.*;

/**
 * Created by Geoffrey on 2016/11/14.
 */


public class RSSMarquee {
    public GridPane root;


    public RSSMarquee(GridPane GridPane) {

        this.root = GridPane;
        Platform.runLater(this::NewsScene);

    }

    private static String NewsToString(String url) {
        RSSStAXParser NewsParser = new RSSStAXParser(url);
        RSSFeed feed = NewsParser.RSSParser();
        String News = feed.toString();
        for (Object message : feed.getList()) {
            News += "   ★" + message.toString();
        }
        return News;
    }

    public void NewsScene() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double size = screenSize.getWidth();

        String News = NewsToString("http://feeds.abcnews.com/abcnews/internationalheadlines");

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

        root.add(NewsFeed, 0, 0);
        root.getChildren().add(myGroup);

        transition.play();

    }

    public GridPane getGrid() {
        return root;
    }


}

