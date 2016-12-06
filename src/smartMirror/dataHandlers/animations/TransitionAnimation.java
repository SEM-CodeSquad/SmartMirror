package smartMirror.dataHandlers.animations;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * @author Pucci on 22/11/2016.
 *         Class responsible for making animations for components
 */
public class TransitionAnimation
{
    private SequentialTransition sequentialTransition;
    private double pauseDuration;

    /**
     * Constructor sets the desired pause duration, the desired transition duration and adds the animation for all
     * components provided as parameters
     *
     * @param pauseDuration how long the component will be showing
     * @param tranDuration  how long will take between the transitions
     * @param args          components to be animated
     * @see SequentialTransition
     */
    public void transitionAnimation(double pauseDuration, double tranDuration, StackPane... args)
    {
        setPauseDuration(pauseDuration);
        sequentialTransition = new SequentialTransition();

        for (StackPane pane : args)
        {
            sequentialTransition.getChildren().addAll(
                    fadeIn(pane, tranDuration),
                    pauseEffect(),
                    fadeOut(pane, tranDuration));
        }

        sequentialTransition.setCycleCount(Timeline.INDEFINITE);
        sequentialTransition.setAutoReverse(false);
    }

    /**
     * Fade in transition
     *
     * @param node     component to add the animation
     * @param duration duration of the animation
     * @return FadeTransition
     * @see FadeTransition
     */
    private FadeTransition fadeIn(StackPane node, double duration)
    {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(duration), node);

        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        return fadeIn;
    }

    /**
     * Fade out transition
     *
     * @param node     component to add the animation
     * @param duration duration of the animation
     * @return FadeTransition
     * @see FadeTransition
     */
    private FadeTransition fadeOut(StackPane node, double duration)
    {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(duration), node);

        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        return fadeOut;
    }

    /**
     * Pause transition animation
     *
     * @return PauseTransition
     * @see PauseTransition
     */
    private PauseTransition pauseEffect()
    {
        return new PauseTransition(Duration.seconds(this.pauseDuration));
    }

    /**
     * Mehtod that sets the pause transition animation with the desired duration
     *
     * @param pauseDuration duration that will be paused
     */
    private void setPauseDuration(double pauseDuration)
    {
        this.pauseDuration = pauseDuration;
    }

    /**
     * Method that plays the sequence transition animation
     */
    public void playSeqAnimation()
    {
        sequentialTransition.play();
    }

    /**
     * Method that stops the sequence transiton animation
     */
    public void stopSeqAnimation()
    {
        sequentialTransition.stop();
    }

    /**
     * Method that pauses the sequence transition naimation
     */
    public void pauseSeqAnimation()
    {
        sequentialTransition.pause();
    }

    /**
     * Getter that provides the sequence transition animation
     *
     * @return SequenceTransition
     */
    public SequentialTransition getSequentialTransition()
    {
        return sequentialTransition;
    }
}
