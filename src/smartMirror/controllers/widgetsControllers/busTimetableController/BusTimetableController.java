package smartMirror.controllers.widgetsControllers.busTimetableController;


import smartMirror.dataHandlers.componentsCommunication.JsonMessageParser;
import smartMirror.dataHandlers.componentsCommunication.TimeNotificationControl;
import smartMirror.dataHandlers.widgetsDataHandlers.busTimetable.BusTimetable;
import smartMirror.dataModels.applicationModels.ChainedMap;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.applicationModels.Settings;
import smartMirror.dataModels.widgetsModels.busTimetableModels.BusInfo;
import smartMirror.dataModels.widgetsModels.busTimetableModels.BusStop;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class BusTimetableController implements Observer
{
    public GridPane busTimetables;

    public Label busStopName;


    public Label busName1;
    public Label busName2;
    public Label busName3;
    public Label busName4;
    public Label busName5;
    public Label busName6;
    public Label busName7;
    public Label busName8;
    public Label busName9;
    public Label busName10;

    public Label nextDepart1;
    public Label to1;
    public Label depart1;

    public Label nextDepart2;
    public Label to2;
    public Label depart2;

    public Label nextDepart3;
    public Label to3;
    public Label depart3;

    public Label nextDepart4;
    public Label to4;
    public Label depart4;

    public Label nextDepart5;
    public Label to5;
    public Label depart5;

    public Label to6;
    public Label depart6;
    public Label nextDepart6;

    public Label nextDepart7;
    public Label to7;
    public Label depart7;

    public Label nextDepart8;
    public Label to8;
    public Label depart8;

    public Label nextDepart9;
    public Label to9;
    public Label depart9;

    public Label nextDepart10;
    public Label to10;
    public Label depart10;

    public GridPane busTimetable1;
    public GridPane busTimetable2;
    public GridPane busTimetable3;
    public GridPane busTimetable4;
    public GridPane busTimetable5;
    public GridPane busTimetable6;
    public GridPane busTimetable7;
    public GridPane busTimetable8;
    public GridPane busTimetable9;
    public GridPane busTimetable10;


    private boolean processing = false;

    private Label[] stopNames;
    private Label[] directions;
    private Label[] departures;
    private Label[] nextDepartures;
    private GridPane[] timetableInfos;
    private BusTimetable bt;
    private String stopName;

    private boolean visible = false;

    public BusTimetableController()
    {
        bt = new BusTimetable();
        bt.addObserver(this);

        this.setUp();
    }

    private void setUp()
    {
        Platform.runLater(() ->
        {
            this.busTimetables.visibleProperty().addListener((observableValue, aBoolean, aBoolean2) ->
                    visible = this.busTimetables.isVisible());

            stopNames = new Label[]{busName1, busName2, busName3, busName4, busName5, busName6, busName7, busName8, busName9
                    , busName10};

            directions = new Label[]{to1, to2, to3, to4, to5, to6, to7, to8, to9, to10};

            departures = new Label[]{depart1, depart2, depart3, depart4, depart5, depart6, depart7, depart8, depart9, depart10};

            nextDepartures = new Label[]{nextDepart1, nextDepart2, nextDepart3, nextDepart4, nextDepart5, nextDepart6,
                    nextDepart7, nextDepart8, nextDepart9, nextDepart10};

            timetableInfos = new GridPane[]{busTimetable1, busTimetable2, busTimetable3, busTimetable4, busTimetable5, busTimetable6,
                    busTimetable7, busTimetable8, busTimetable9, busTimetable10};
        });
        TimeNotificationControl notificationControl = new TimeNotificationControl();
        notificationControl.addObserver(this);
        notificationControl.bind("HH:mm:ss", 1, "timetable");
    }

    private synchronized void setVisible(boolean b)
    {
        Platform.runLater(() ->
        {
            this.busTimetables.setVisible(b);
            StackPane parentPane = (StackPane) this.busTimetables.getParent();
            GridPane parentGrid = (GridPane) parentPane.getParent();

            monitorWidgetVisibility(parentPane, parentGrid);

        });
        if (b && this.stopName != null && !processing)
        {
            this.processing = true;
            this.setBusStopName(this.stopName);
        }
    }

    private synchronized void monitorWidgetVisibility(StackPane stackPane, GridPane gridPane)
    {
        boolean visible = false;
        ObservableList<Node> list = stackPane.getChildren();
        for (Node node : list)
        {
            visible = node.isVisible();
        }
        gridPane.setVisible(visible);
    }

    private synchronized void setParentVisible()
    {
        Platform.runLater(() ->
        {
            GridPane gridPane = (GridPane) this.busTimetables.getParent().getParent();
            if (gridPane.getOpacity() != 1)
            {
                gridPane.setVisible(true);
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), gridPane);

                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();
            }
        });
    }

    private void enforceView()
    {
        if (!visible)
        {
            StackPane sPane = (StackPane) this.busTimetables.getParent();

            ObservableList<Node> list = sPane.getChildren();
            for (Node node : list)
            {
                node.setVisible(false);
            }

            this.busTimetables.setVisible(true);
        }
    }

    private synchronized void setBusStopName(String busStopName)
    {
        this.stopName = busStopName;
        Thread thread = new Thread(() -> bt.setBusTimetable(busStopName));
        thread.start();
    }

    private synchronized void animationFadeIn(GridPane gridPane)
    {
        Platform.runLater(() ->
        {
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), gridPane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
    }

    private synchronized void animationFadeOut(GridPane gridPane)
    {
        Platform.runLater(() ->
        {
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), gridPane);
            fadeIn.setFromValue(1);
            fadeIn.setToValue(0);
            if (gridPane.getOpacity() == 1) fadeIn.play();
        });
    }

    private synchronized void setBusName(Label label, String text, String color)
    {
        String textColor;
        if (color.equals("#ffffff")) textColor = "black";
        else textColor = "white";

        label.setVisible(true);
        char[] chars = text.toCharArray();
        if (Character.isDigit(chars[0]))
        {
            Platform.runLater(() -> label.setStyle("-fx-background-color: " + color + "; " +
                    "-fx-background-radius: 30; " +
                    "-fx-background-insets: 0,1,2,3,0; " +
                    "-fx-text-fill: " + textColor + "; " +
                    "-fx-font: 20px \"Microsoft YaHei\";" +
                    "-fx-font-weight: bold;"));
            GridPane.setMargin(label, new Insets(0, 0, 0, 10));
        }
        else
        {
            Platform.runLater(() -> label.setStyle("-fx-background-color: " + color + "; " +
                    "-fx-background-radius: 30; " +
                    "-fx-background-insets: 0,1,2,3,0; " +
                    "-fx-text-fill: " + textColor + "; " +
                    "-fx-font: 14px \"Microsoft YaHei\";" +
                    "-fx-font-weight: bold;"));
            GridPane.setMargin(label, new Insets(0, 0, 0, 0));
        }
        Platform.runLater(() -> label.setText(" " + text + " "));
    }

    private synchronized void setInfo(Label l, String text)
    {
        l.setVisible(true);
        Platform.runLater(() -> l.setText(text));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object arg)
    {

        if (arg instanceof ChainedMap)
        {
            Thread thread = new Thread(() ->
            {
                ChainedMap<String, LinkedList> busInfos = (ChainedMap) arg;
                LinkedList<BusInfo> listTemp = busInfos.getMap().get(busInfos.getId().peek());
                Platform.runLater(() -> busStopName.setText(listTemp.peek().getBusFrom()));
                for (int i = 0; i < stopNames.length; i++)
                {
                    if (busInfos.size() == 0)
                    {
                        animationFadeOut(timetableInfos[i]);
                    }
                    else
                    {
                        LinkedList<BusInfo> list = busInfos.getMap().get(busInfos.getId().peek());
                        setInfo(stopNames[i], list.peek().getBusName());
                        setBusName(stopNames[i], list.peek().getBusName(), list.peek().getBusColor());
                        setInfo(directions[i], "To: " + list.peek().getBusDirection());
                        String dep;
                        if (list.peek().getBusDeparture() == 0) dep = "Now";
                        else dep = "In:" + String.valueOf(list.peek().getBusDeparture()) + " min";
                        setInfo(departures[i], dep);

                        if (list.size() > 1)
                        {
                            list.remove();
                            setInfo(nextDepartures[i], "Next:" + String.valueOf(list.peek().getBusDeparture()) + " min");
                        }
                        else setInfo(nextDepartures[i], "n/a");

                        busInfos.remove(busInfos.getId().peek());
                        animationFadeIn(timetableInfos[i]);
                    }
                }
                processing = false;
            });
            thread.start();
        }
        else if (arg.equals("Update Timetable") && this.busTimetables.isVisible()
                && this.stopName != null && !processing)
        {
            this.processing = true;
            this.setBusStopName(this.stopName);
        }
        else if (arg instanceof MqttMessage)
        {
            Thread thread = new Thread(() ->
            {
                JsonMessageParser parser = new JsonMessageParser();
                parser.parseMessage(arg.toString());

                if (parser.getContentType().equals("settings"))
                {

                    Settings settings = parser.parseSettings();
                    if (settings.getObject() instanceof BusStop)
                    {
                        setParentVisible();
                        enforceView();
                        setBusStopName(((BusStop) settings.getObject()).getBusStop());
                    }
                }
                else if (parser.getContentType().equals("preferences"))
                {
                    LinkedList<Preferences> list = parser.parsePreferenceList();

                    list.stream().filter(pref -> pref.getName().equals("bus")).forEach(pref ->
                            setVisible(pref.getValue().equals("true")));
                }
            });
            thread.start();
        }
    }
}
