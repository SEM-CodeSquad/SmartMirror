package widgets;

import dataModels.DateS;
import dataModels.Day;
import dataModels.Greetings;
import dataModels.TimeS;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;

/**
 *
 */
public class TimeDateManager extends Observable
{
    private static DateTimeFormatter SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private TimeS time;
    private DateS date;
    private Day dayName;
    private Greetings greeting;

    public TimeDateManager() {
        this.time = new TimeS();
        this.date = new DateS();
        this.dayName = new Day();
        this.greeting = new Greetings();
    }

    /**
     *
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
     *
     */
    private void notifyTimeChanged()
    {
        this.time.setTime(LocalTime.now().format(SHORT_TIME_FORMATTER));
        setChanged();
        notifyObservers(this.time);
    }

    /**
     *
     */
    public void bindToDate() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> this.notifyDateChanged()),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void notifyDateChanged() {
        this.date.setDate(LocalDate.now().format(SHORT_DATE_FORMATTER));
        setChanged();
        notifyObservers(this.date);
    }

    /**
     *
     */
    public void bindToDay()
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> this.notifyDayChanged()),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void notifyDayChanged() {
        this.dayName.setDayName(getDay());
        setChanged();
        notifyObservers(this.dayName);
    }

    /**
     *
     * @return first three letters of the day name
     */
    private String getDay()
    {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime());

    }

    public void bindGreetings()
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> this.notifyGreetingsChanged()),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void notifyGreetingsChanged() {
        this.greeting.setGreetings(greetings());
        setChanged();
        notifyObservers(this.greeting);
    }

    private String greetings()
    {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            return "Good Morning!";
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            return "Good Afternoon!";
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            return "Good Evening!";
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            return "Good Night!";
        }

        return "";
    }

    public TimeS getTime() {
        return time;
    }


}
