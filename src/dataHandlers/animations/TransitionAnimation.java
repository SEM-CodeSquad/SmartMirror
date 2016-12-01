package dataHandlers.animations;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * @author Pucci on 22/11/2016.
 */
public class TransitionAnimation {
    private SequentialTransition sequentialTransition;
    private double pauseDuration;


    public void transitionAnimation(double pauseDuration, double tranDuration, StackPane... args) {
        setPauseDuration(pauseDuration);
        sequentialTransition = new SequentialTransition();

        for (StackPane pane : args) {
            sequentialTransition.getChildren().addAll(
                    fadeIn(pane, tranDuration),
                    pauseEffect(),
                    fadeOut(pane, tranDuration));
        }

        sequentialTransition.setCycleCount(Timeline.INDEFINITE);
        sequentialTransition.setAutoReverse(false);
    }

    private FadeTransition fadeIn(StackPane node, double duration) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(duration), node);

        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        return fadeIn;
    }

    private FadeTransition fadeOut(StackPane node, double duration) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(duration), node);

        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        return fadeOut;
    }

    private PauseTransition pauseEffect() {
        return new PauseTransition(Duration.seconds(this.pauseDuration));
    }

    private void setPauseDuration(double pauseDuration) {
        this.pauseDuration = pauseDuration;
    }

    public void playSeqAnimation() {
        sequentialTransition.play();
    }

    public void stopSeqAnimation() {
        sequentialTransition.stop();
    }

    public void pauseSeqAnimation() {
        sequentialTransition.pause();
    }

    public SequentialTransition getSequentialTransition() {
        return sequentialTransition;
    }
}
