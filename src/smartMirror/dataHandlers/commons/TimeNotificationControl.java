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

package smartMirror.dataHandlers.commons;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;

/**
 * @author CodeHigh on 05/12/2016.
 *         Class responsible for monitoring the time and notifing the observers about this time change
 */
public class TimeNotificationControl extends Observable
{
    private DateTimeFormatter SHORT_TIME_FORMATTER;
    private String widgetName;

    /**
     * This method loops once every certain time (to be specified) and notifies its observer about the time change
     *
     * @param SHORT_TIME_FORMATTER time format
     * @param duration             duration until next loop
     * @param widgetName           name of the widget
     */
    public void bind(String SHORT_TIME_FORMATTER, double duration, String widgetName)
    {
        this.SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern(SHORT_TIME_FORMATTER);
        this.widgetName = widgetName;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> this.notifyTimeChanged()),
                new KeyFrame(Duration.seconds(duration)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    /**
     * This method identifies the widget name and notify the observer according to the widget name
     */
    private void notifyTimeChanged()
    {
        switch (widgetName)
        {
            case "timetable":
                if (LocalTime.now().format(SHORT_TIME_FORMATTER).endsWith("10") || LocalTime.now().format(SHORT_TIME_FORMATTER).endsWith("30")
                        || LocalTime.now().format(SHORT_TIME_FORMATTER).endsWith("50"))
                {
                    setChanged();
                    notifyObservers("Update Timetable");
                }
                break;

            case "weather":
                setChanged();
                notifyObservers("Update Weather and News");
                break;

            case "news":
                setChanged();
                notifyObservers("Update Weather and News");
                break;

            case "post-it":
                if (LocalTime.now().format(SHORT_TIME_FORMATTER).equals("00:30:00"))
                {
                    setChanged();
                    notifyObservers(new Timestamp());
                }
                break;
            default:
                break;
        }
    }
}
