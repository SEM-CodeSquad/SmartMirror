package widgets;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    public TimeDateManager(BusTimetable busTimetable) {
        addObserver(busTimetable);
    }

    /**
     *
     * @param time label to be showing the time
     */
    public void bindToTime(Label time)
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> time.setText(LocalTime.now().format(SHORT_TIME_FORMATTER))),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        setChanged();
        notifyObservers(time);
    }

    /**
     *
     * @param date label to be showing the date
     */
    public void bindToDate(Label date)
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> date.setText(LocalDate.now().format(SHORT_DATE_FORMATTER))),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     *
     * @param day label to be showing the day name
     */
    public void bindToDay(Label day)
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> day.setText(getDay())),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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

    public void bindGreetings(Label greetings)
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> greetings.setText(greetings())),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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
}
