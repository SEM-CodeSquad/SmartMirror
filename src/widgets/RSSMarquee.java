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

/**
 *
 */
public class RSSMarquee {
    private GridPane root;
    private String news;

    /**
     * @param GridPane GridPane
     */
    public RSSMarquee(GridPane GridPane) {
        this.root = GridPane;
        this.news = " ";
        Platform.runLater(this::setUp);
    }

    /**
     *
     */
    @SuppressWarnings("deprecation")
    private void setUp() {

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
                .fromX(root.widthProperty().get() + 1000)
                .cycleCount(Timeline.INDEFINITE)
                .build();

        Group myGroup = GroupBuilder.create()
                .children(newsFeed)
                .build();

        root.add(newsFeed, 0, 0);
        root.getChildren().add(myGroup);

        transition.play();

    }

    /**
     *
     * @return String news
     */
    public String getNews() {
        return this.news;
    }

}