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

/**
 *
 */
public class TimeDateManager
{
    private static DateTimeFormatter SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

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
}
