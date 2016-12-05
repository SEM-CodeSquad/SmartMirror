package smartMirror.controllers.mainController;

import smartMirror.controllers.widgetsControllers.busTimetableController.BusTimetableController;
import smartMirror.controllers.widgetsControllers.devicesController.DeviceController;
import smartMirror.controllers.widgetsControllers.feedController.FeedController;
import smartMirror.controllers.widgetsControllers.greetingsController.GreetingsController;
import smartMirror.controllers.widgetsControllers.postItsController.PostItViewController;
import smartMirror.controllers.widgetsControllers.shoppingListController.ShoppingListViewController;
import smartMirror.controllers.widgetsControllers.weatherController.WeatherController;
import smartMirror.controllers.widgetsControllers.timeDateController.TimeDateController;
import smartMirror.dataHandlers.componentsCommunication.CommunicationManager;
import smartMirror.dataHandlers.componentsCommunication.JsonMessageParser;
import smartMirror.dataHandlers.widgetsDataHandlers.timeDate.TimeDateManager;
import smartMirror.dataModels.applicationModels.UUID_Generator;
import smartMirror.dataModels.widgetsModels.qrCodeModels.QRCode;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MainController extends Observable implements Observer
{
    public BorderPane pairingPane;
    public GridPane pairingDateTimeContainer;
    public GridPane qrPairingContainer;

    public AnchorPane mainPane;
    public GridPane widget1;
    public GridPane widget7;
    public GridPane widget6;
    public GridPane widget3;
    public GridPane gridMainQR;
    public GridPane widget2;
    public GridPane widget5;
    public GridPane widget4;

    public StackPane stackPaneWidget1;
    public StackPane stackPaneWidget7;
    public StackPane stackPaneWidget6;
    public StackPane stackPaneWidget3;
    public StackPane stackPaneWidgetQR;
    public StackPane stackPaneWidget2;
    public StackPane stackPaneWidget5;
    public StackPane stackPaneWidget4;

    private boolean systemRunning;
    private UUID_Generator uuid;

    private TimeDateManager timeDateManager;
    private CommunicationManager communicationManager;

    private FeedController feedController;
    private WeatherController temperatureController;
    private BusTimetableController busTimetableController;
    private DeviceController deviceController;
    private TimeDateController timeDateController;
    private GreetingsController greetingsController;
    private PostItViewController postItViewController;
    private ShoppingListViewController shoppingListController;


    public MainController()
    {
        this.systemRunning = true;
        keepScreenAlive();
        this.uuid = new UUID_Generator();
        this.communicationManager = new CommunicationManager(this.uuid.getUUID());
        this.communicationManager.addObserver(this);
        startUp();
    }

    private void startUp()
    {
        Platform.runLater(() ->
        {
            setUpDateTimeView();
            setUpQRCodeView();
            setUpTemperatureView();
            setUpDevicesView();
            setUpFeedView();
            setUpBusTimetableView();
            setUpPostItView();
            setUpGreetingsView();
            setUpShoppingListView();

            this.pairingPane.setOpacity(1);

        });
    }

    private void setUpDevicesView()
    {
        this.deviceController = loadViewMainScreen(this.stackPaneWidget7, "/smartMirror/Views/widgetsViews/deviceStatusWidget/DeviceView.fxml").getController();
        this.communicationManager.addObserver(this.deviceController);
    }

    private void setUpShoppingListView()
    {
        this.shoppingListController = loadViewMainScreen(this.stackPaneWidget3, "/smartMirror/Views/widgetsViews/shoppingListWidget/ShoppingListView.fxml").getController();
        this.communicationManager.addObserver(this.shoppingListController);
    }

    private void setUpPostItView()
    {
        this.postItViewController = loadViewMainScreen(this.stackPaneWidget6, "/smartMirror/Views/widgetsViews/postItWidget/PostitView.fxml").getController();
        this.communicationManager.addObserver(this.postItViewController);

    }

    private void setUpFeedView()
    {
        this.feedController = loadViewMainScreen(this.stackPaneWidget2, "/smartMirror/Views/widgetsViews/feedsWidget/FeedsViews.fxml").getController();
//        this.timeDateManager.addObserver(this.feedController);
        this.communicationManager.addObserver(this.feedController);

    }

    private void setUpTemperatureView()
    {
        this.temperatureController = loadViewMainScreen(this.stackPaneWidget4, "/smartMirror/Views/widgetsViews/weatherWidget/WeatherView.fxml").getController();
//        this.timeDateManager.addObserver(this.temperatureController);
        this.communicationManager.addObserver(this.temperatureController);

    }

    private void setUpBusTimetableView()
    {
        this.busTimetableController = loadViewMainScreen(this.stackPaneWidget3, "/smartMirror/Views/widgetsViews/busTimetableWidget/BusTimetable.fxml").getController();
//        this.timeDateManager.addObserver(this.busTimetableController);
        this.communicationManager.addObserver(this.busTimetableController);
    }

    private void setUpQRCodeView()
    {
        QRCode qrCode = new QRCode(this.uuid.getUUID());

        qrCode.addObserver(loadViewPairingScreen(this.qrPairingContainer, "/smartMirror/Views/widgetsViews/qrCode/QRCodeView.fxml",
                1, 0).getController());
        setComponentVisible(this.qrPairingContainer);
        qrCode.addObserver(loadViewMainScreen(this.stackPaneWidgetQR, "/smartMirror/Views/widgetsViews/qrCode/QRCodeView.fxml").getController());
        setComponentVisible(this.gridMainQR);
        qrCode.getQRCode();


    }

    private void setUpDateTimeView()
    {
        this.timeDateManager = new TimeDateManager();
        this.timeDateManager.bindToTime();
        this.timeDateManager.bindToDate();
        this.timeDateManager.bindToDay();
        this.timeDateManager.bindGreetings();

        this.timeDateManager.addObserver(loadViewPairingScreen(this.pairingDateTimeContainer, "/smartMirror/Views/widgetsViews/timeDateWidget/TimeDate.fxml",
                0, 0).getController());
        setComponentVisible(this.pairingDateTimeContainer);
        this.timeDateController = loadViewMainScreen(this.stackPaneWidget1, "/smartMirror/Views/widgetsViews/timeDateWidget/TimeDate.fxml").getController();
        this.timeDateManager.addObserver(this.timeDateController);
        this.communicationManager.addObserver(this.timeDateController);
    }

    private void setUpGreetingsView()
    {
        greetingsController = loadViewMainScreen(stackPaneWidget5, "/smartMirror/Views/widgetsViews/greetingsWidget/GreetingsView.fxml").getController();
        timeDateManager.addObserver(greetingsController);
        this.communicationManager.addObserver(this.greetingsController);
    }

    private void changeScene()
    {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), this.pairingPane);

        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), this.mainPane);

        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        fadeOut.setOnFinished(event -> fadeIn.play());
        fadeOut.play();

    }

    private synchronized void setComponentVisible(GridPane gridPane)
    {
        Platform.runLater(() ->
        {
            gridPane.setVisible(true);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), gridPane);

            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
    }

    private synchronized void setComponentInvisible(GridPane gridPane)
    {
        Platform.runLater(() ->
        {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), gridPane);

            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> gridPane.setVisible(false));
            fadeOut.play();
        });
    }

    private void keepScreenAlive()
    {
        Thread thread = new Thread(() ->
        {
            while (systemRunning)
            {
                try
                {
                    Thread.sleep(80000);//this is how long before it moves
                    Point point = MouseInfo.getPointerInfo().getLocation();
                    Robot robot = new Robot();
                    robot.mouseMove(point.x, point.y);
                }
                catch (InterruptedException | AWTException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private FXMLLoader loadViewPairingScreen(GridPane parent, String resource, int c, int r)
    {
        FXMLLoader myLoader = null;
        try
        {
            myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            parent.add(loadScreen, c, r);
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
        return myLoader;
    }

    private FXMLLoader loadViewMainScreen(StackPane parent, String resource)
    {
        FXMLLoader myLoader = null;
        try
        {
            myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            parent.getChildren().add(loadScreen);
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
        return myLoader;
    }

    // TODO: 01/12/2016   
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

    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof MqttMessage)
        {
            Thread thread = new Thread(() -> {
                JsonMessageParser parser = new JsonMessageParser();
                parser.parseMessage(arg.toString());
                if (parser.parsePairing())
                {
                    setComponentVisible(this.widget1);
                    setComponentVisible(this.widget5);
                    changeScene();
                }
            });
            thread.start();
        }
//        else if (arg instanceof CommunicationManager) {
//            Thread thread = new Thread(() -> Platform.runLater(this::setUpGreetingsView));
//            thread.start();
//            changeScene();
//            setComponentVisible(widget1);
//            setComponentVisible(gridMainQR);
//            setComponentVisible(widget5);
//
//        } else if (arg instanceof BusStop) {
//            Thread thread = new Thread(() -> this.setComponentVisible(this.widget3));
//            thread.start();
//
//        } else if (arg instanceof Town) {
//            Thread thread = new Thread(() -> this.setComponentVisible(this.widget4));
//            thread.start();
//
//        } else if (arg instanceof NewsSource) {
//            Thread thread = new Thread(() -> this.setComponentVisible(this.widget2));
//            thread.start();
//
//        } else if (arg instanceof LinkedList && ((LinkedList) arg).peek() instanceof Device) {
//            Thread thread = new Thread(() -> setComponentVisible(widget7));
//            thread.start();
//        }
    }
}
