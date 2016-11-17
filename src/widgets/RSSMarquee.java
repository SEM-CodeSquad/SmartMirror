package widgets;

import dataHandlers.RSSFeed;
import dataHandlers.RSSStAXParser;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;

import static com.sun.javafx.tk.Toolkit.*;

/**
 * Created by Geoffrey on 2016/11/14.
 */


public class RSSMarquee extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }


    public void start(Stage primaryStage) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double size = screenSize.getWidth();

        String News = NewsToString("http://feeds.abcnews.com/abcnews/internationalheadlines");

        Group root = new Group();
        Text NewsFeed = TextBuilder.create()
                .layoutX(size)
                .textOrigin(VPos.TOP)
                .textAlignment(TextAlignment.JUSTIFY)
                .fill(Color.WHITE)
                .style("-fx-font-family:'Josefin Sans'; -fx-font-size: 20; -fx-fill: yellow;")
                .build();

        NewsFeed.setText(News);

        float width = getToolkit().getFontLoader().computeStringWidth(News, NewsFeed.getFont());
        TranslateTransition transition = TranslateTransitionBuilder.create()
                .node(NewsFeed)
                .duration(new Duration((int) width * 10))
                .interpolator(Interpolator.LINEAR)
                .toX(NewsFeed.getBoundsInLocal().getMaxX() * -1 - 500)
                .fromX(primaryStage.widthProperty().get() + 500)
                .cycleCount(Timeline.INDEFINITE)
                .build();

        Group myGroup = GroupBuilder.create()
                .children(NewsFeed)
                .build();

        root.getChildren().add(myGroup);


        Scene scene = new Scene(root, size, 25);
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Josefin+Sans");
        scene.setFill(null);


        primaryStage.setScene(scene);

        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.show();
        transition.play();

    }

    private String NewsToString(String url) {
        RSSStAXParser NewsParser = new RSSStAXParser(url);
        RSSFeed feed = NewsParser.RSSParser();
        String News = feed.toString();
        for (Object message : feed.getList()) {
            News += "   ★" + message.toString();
        }
        return News;
    }
}

