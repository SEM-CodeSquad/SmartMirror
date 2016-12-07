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

package smartMirror.dataHandlers.widgetsDataHandlers.feed;

import com.sun.javafx.tk.Toolkit;
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

/**
 * @author CodeHigh @copyright on 06/12/2016.
 *         Class responsible for generating a marquee pane that holds the news text in the interface
 */
public class MarqueePane extends Pane
{
    private Timeline timeline;
    private Pane marqueePane;
    private Text marqueeText;
    private String text;

    /**
     * Private constructor
     */
    private MarqueePane()
    {
        marqueePane = this;
        newText();

    }

    /**
     * Constructor method that generates the pane that will hold the text component
     *
     * @param width  width to be set to the marquee pane
     * @param height height to be set to the marquee pane
     */
    public MarqueePane(double width, double height)
    {
        this();

        marqueeText.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 20));
        setTextColor();

        marqueePane.setPrefSize(width, height);
        marqueePane.setClip(new Rectangle(width, height));
        setBackgroundColor();

        double textHeight = getTextHeight(marqueeText);
        marqueeText.relocate(width, (height / 2) - (textHeight / 2));
    }

    /**
     * Method that will hold the nex text
     */
    private void newText()
    {
        marqueeText = new Text();
        marqueeText.setStrokeWidth(0);
        marqueePane.getChildren().add(marqueeText);
    }

    /**
     * Method that sets the text in the text component
     *
     * @param text text to be set
     */
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * Method that sets the translation effect to the text
     */
    public void setEffect()
    {
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(false);
        int duration;
        if (text.length() <= 815) duration = 85000;
        else if (text.length() < 816 && text.length() <= 1500) duration = 100000;
        else duration = 200000;
        KeyValue value = new KeyValue(marqueeText.layoutXProperty(), -text.length() * 11);
        KeyFrame frame = new KeyFrame(Duration.millis(duration), value);
        timeline.getKeyFrames().add(frame);
    }

    /**
     * Method that provides the height of the text component
     *
     * @param text text component
     * @return the height of the component
     */
    private double getTextHeight(Text text)
    {
        return Toolkit.getToolkit().getFontLoader().getFontMetrics(text.getFont()).getLineHeight();
    }

    /**
     * Method that sets the color in the marquee pane
     */
    private void setBackgroundColor()
    {
        if (marqueePane == null)
        {
            return;
        }

        marqueePane.setStyle("-fx-background-color: #000000");
    }

    /**
     * Method that sets the color for the text
     */
    private void setTextColor()
    {
        if (marqueeText == null)
        {
            return;
        }

        marqueeText.setFill(Color.WHITE);
    }

    /**
     * Method that is called to start the animation
     */
    public void play()
    {
        marqueeText.setText(this.text);
        if (timeline == null)
        {
            return;
        }

        timeline.playFrom(Duration.ZERO);
    }

    /**
     * Method that is called to pause the animation
     */
    private void pause()
    {
        if (timeline == null)
        {
            return;
        }

        timeline.pause();
    }

    /**
     * Method that is called to pause the animation
     */
    public void stop()
    {
        if (timeline == null)
        {
            return;
        }

        pause();
        timeline.stop();
    }
}
