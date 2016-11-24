package dataModels;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import com.sun.javafx.tk.Toolkit;


public class MarqueePane extends Pane {
    private Timeline timeline;
    private Pane marqueePane;
    private Text marqueeText;
    private String text;

    private MarqueePane() {
        marqueeText = new Text();
        marqueeText.setStrokeWidth(0);

        marqueePane = this;
        marqueePane.getChildren().add(marqueeText);

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(false);
    }


    public MarqueePane(double width, double height) {
        this();

        marqueeText.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 20));
        setTextColor();

        marqueePane.setPrefSize(width, height);
        marqueePane.setClip(new Rectangle(width, height));
        setBackgroundColor();

        double textHeight = getTextHeight(marqueeText);
        marqueeText.relocate(width, (height / 2) - (textHeight / 2));
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEffect() {
        KeyValue value = new KeyValue(marqueeText.layoutXProperty(), -text.length() * 11);
        System.out.println(text.length());
        KeyFrame frame = new KeyFrame(Duration.millis(80900), value);
        timeline.getKeyFrames().add(frame);
    }

    private double getTextWidth(Text text) {
        return Toolkit.getToolkit().getFontLoader().computeStringWidth(text.getText(), text.getFont());
    }

    private double getTextHeight(Text text) {
        return Toolkit.getToolkit().getFontLoader().getFontMetrics(text.getFont()).getLineHeight();
    }


    private void setBackgroundColor() {
        if (marqueePane == null) {
            return;
        }

        marqueePane.setStyle("-fx-background-color: #000000");
    }

    private void setTextColor() {
        if (marqueeText == null) {
            return;
        }

        marqueeText.setFill(Color.WHITE);
    }


    public void play() {
        marqueeText.setText(this.text);
        if (timeline == null) {
            return;
        }

        timeline.play();
    }

    public void pause() {
        if (timeline == null) {
            return;
        }

        timeline.pause();
    }

    public void stop() {
        if (timeline == null) {
            return;
        }

        pause();
        timeline.jumpTo(Duration.ZERO);
    }
}
