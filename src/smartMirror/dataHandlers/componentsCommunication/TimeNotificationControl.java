package smartMirror.dataHandlers.componentsCommunication;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import smartMirror.dataModels.applicationModels.Timestamp;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;

/**
 * @author Pucci on 05/12/2016.
 */
public class TimeNotificationControl extends Observable
{
    private DateTimeFormatter SHORT_TIME_FORMATTER;
    private String widgetName;

    /**
     *
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
     *
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
                if (LocalTime.now().format(SHORT_TIME_FORMATTER).equals("23:39:00"))
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
