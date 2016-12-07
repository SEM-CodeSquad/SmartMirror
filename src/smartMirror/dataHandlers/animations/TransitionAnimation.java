/*
 * Copyright 2016 CodeHigh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2016 CodeHigh.
 *     Permission is granted to copy, distribute and/or modify this document
 *     under the terms of the GNU Free Documentation License, Version 1.3
 *     or any later version published by the Free Software Foundation;
 *     with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *     A copy of the license is included in the section entitled "GNU
 *     Free Documentation License".
 */

package smartMirror.dataHandlers.animations;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * @author CodeHigh on 22/11/2016.
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
