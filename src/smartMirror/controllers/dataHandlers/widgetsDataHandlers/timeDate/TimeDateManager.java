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

package smartMirror.controllers.dataHandlers.widgetsDataHandlers.timeDate;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import smartMirror.dataModels.widgetsModels.dateTimeModels.DateS;
import smartMirror.dataModels.widgetsModels.dateTimeModels.Day;
import smartMirror.dataModels.widgetsModels.dateTimeModels.TimeS;
import smartMirror.dataModels.widgetsModels.greetingsModels.Greetings;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;

/**
 * @author CodeHigh @copyright on 07/12/2016.
 *         Class responsible for updating the time and date or relateds
 */
public class TimeDateManager extends Observable
{
    private static DateTimeFormatter SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private TimeS time;
    private DateS date;
    private Day dayName;
    private Greetings greeting;

    /**
     * Constructor instantiate need classes
     */
    public TimeDateManager()
    {
        this.time = new TimeS();
        this.date = new DateS();
        this.dayName = new Day();
        this.greeting = new Greetings();
    }

    /**
     * Method that binds the time and notifies observer every second
     */
    public void bindToTime()
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> this.notifyTimeChanged()),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    /**
     * Method sends time notification
     */
    private void notifyTimeChanged()
    {
        this.time.setTime(LocalTime.now().format(SHORT_TIME_FORMATTER));
        setChanged();
        notifyObservers(this.time);
    }

    /**
     * Method that binds the date and notifies observer every second
     */
    public void bindToDate()
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> this.notifyDateChanged()),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Method sends date notification
     */
    private void notifyDateChanged()
    {
        this.date.setDate(LocalDate.now().format(SHORT_DATE_FORMATTER));
        setChanged();
        notifyObservers(this.date);
    }

    /**
     * Method that binds the day and notifies observer every second
     */
    public void bindToDay()
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> this.notifyDayChanged()),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Method sends day name notification
     */
    private void notifyDayChanged()
    {
        this.dayName.setDayName(getDay());
        setChanged();
        notifyObservers(this.dayName);
    }

    /**
     * Method that gets the day name (e.g. MON or SAT)
     *
     * @return first three letters of the day name
     */
    private String getDay()
    {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime());

    }

    /**
     * Method that binds the greetings and notifies observer every second
     */
    public void bindGreetings()
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> this.notifyGreetingsChanged()),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Method sends greeting name notification
     */
    private void notifyGreetingsChanged()
    {
        this.greeting.setGreetings(greetings());
        setChanged();
        notifyObservers(this.greeting);
    }

    /**
     * Method that identifies the time and return a message appropriated for the identified time
     *
     * @return greeting message
     */
    private String greetings()
    {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12)
        {
            return "Good Morning!";
        }
        else if (timeOfDay >= 12 && timeOfDay < 16)
        {
            return "Good Afternoon!";
        }
        else if (timeOfDay >= 16 && timeOfDay < 21)
        {
            return "Good Evening!";
        }
        else if (timeOfDay >= 21 && timeOfDay < 24)
        {
            return "Good Night!";
        }

        return "";
    }
}
