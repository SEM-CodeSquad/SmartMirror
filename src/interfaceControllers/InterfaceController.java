package interfaceControllers;

import dataHandlers.PairingManager;
import dataHandlers.JsonMessageParser;
import dataHandlers.QRCode;
import dataHandlers.UUID_Generator;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import mqttClient.MQTTClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import widgets.BusTimetable;
import widgets.TimeDateManager;

import java.awt.*;
import java.util.*;

public class InterfaceController implements Observer {

    public WebView webViewBus;
    public AnchorPane mainPane;
    public GridPane postItsGrid;
    public Label time;
    public Label dayName;
    public Label date;
    public Label outdoorsTemp;
    public Label indoorsTemp;
    public ImageView imgOutdoorsTemp;
    public Label forecast1;
    public Label forecast2;
    public Label forecast3;
    public ImageView imgForecast1;
    public ImageView imgForecast2;
    public ImageView imgForecast3;
    public Label newsFeed;
    public Label greetings;
    public Label secondaryMsg;
    public AnchorPane pairingPane;
    public Label timePairingScreen;
    public Label datePairingScreen;
    public Label dayNamePairingScreen;
    public ImageView imgQRCode;
    public Label notificationPairingScreen;
    public ImageView qrCodeView;
    public StackPane postPane1;
    public StackPane postPane2;
    public StackPane postPane3;
    public StackPane postPane4;
    public StackPane postPane5;
    public StackPane postPane6;
    public StackPane postPane7;
    public StackPane postPane8;
    public StackPane postPane9;
    public StackPane postPane10;
    public StackPane postPane11;
    public StackPane postPane12;
    public TextArea postText1;
    public TextArea postText2;
    public TextArea postText3;
    public TextArea postText4;
    public TextArea postText5;
    public TextArea postText6;
    public TextArea postText9;
    public TextArea postText7;
    public TextArea postText8;
    public TextArea postText10;
    public TextArea postText11;
    public TextArea postText12;
    public GridPane timetableContainer;
    private PostItGuiManager postitGuiManager;
    private SettingsManager settings;
    private PairingManager pairingManager;
    private JsonMessageParser parser;
    private boolean systemRunning;
    private PostItComponents postItComponents;
    private BusTimetable busTimetable;


    private String clientId;
    private MQTTClient mqttClient;


    public InterfaceController()
    {
        systemRunning = true;
        this.postitGuiManager = new PostItGuiManager(this);
        this.postItComponents = new PostItComponents();
        Platform.runLater(this::setUp);
    }

    private void setUp()
    {
        keepScreenAlive();
        UUID_Generator uuid_generator = new UUID_Generator();
        clientId = uuid_generator.getUUID();
        QRCode qrCode = new QRCode(clientId);
        imgQRCode.setImage(qrCode.getQRCode());
        qrCodeView.setImage(qrCode.getQRCode());
        this.mqttClient = new MQTTClient("tcp://codehigh.ddns.me", clientId);
        this.pairingManager = new PairingManager(this.clientId, this.mqttClient, this);
        BusTimetable busTimetable = new BusTimetable();
        TimeDateManager timeDateManager = new TimeDateManager(busTimetable);
        timeDateManager.bindToTime(this.timePairingScreen);
        timeDateManager.bindToDate(this.datePairingScreen);
        timeDateManager.bindToDay(this.dayNamePairingScreen);
        timeDateManager.bindToTime(this.time);
        timeDateManager.bindToDate(this.date);
        timeDateManager.bindToDay(this.dayName);
        timeDateManager.bindGreetings(this.greetings);
        this.settings = new SettingsManager(this.webViewBus, this.timetableContainer, this);
        this.pairingManager.listenPairing();
        this.parser = new JsonMessageParser(settings, pairingManager, postitGuiManager);
        this.postItComponents.addPane(0, postPane1);
        this.postItComponents.addPane(1, postPane2);
        this.postItComponents.addPane(2, postPane3);
        this.postItComponents.addPane(3, postPane4);
        this.postItComponents.addPane(4, postPane5);
        this.postItComponents.addPane(5, postPane6);
        this.postItComponents.addPane(6, postPane7);
        this.postItComponents.addPane(7, postPane8);
        this.postItComponents.addPane(8, postPane9);
        this.postItComponents.addPane(9, postPane10);
        this.postItComponents.addPane(10, postPane11);
        this.postItComponents.addPane(11, postPane12);
        this.postItComponents.addTextArea(0, postText1);
        this.postItComponents.addTextArea(1, postText2);
        this.postItComponents.addTextArea(2, postText3);
        this.postItComponents.addTextArea(3, postText4);
        this.postItComponents.addTextArea(4, postText5);
        this.postItComponents.addTextArea(5, postText6);
        this.postItComponents.addTextArea(6, postText7);
        this.postItComponents.addTextArea(7, postText8);
        this.postItComponents.addTextArea(8, postText9);
        this.postItComponents.addTextArea(9, postText10);
        this.postItComponents.addTextArea(10, postText11);
        this.postItComponents.addTextArea(11, postText12);
    }

    private void changeScene(AnchorPane one, AnchorPane two)
    {
        one.setVisible(false);
        two.setVisible(true);
    }

    private void keepScreenAlive()
    {
        Thread thread = new Thread(()->{
            while (systemRunning) {
                try {
                    Thread.sleep(80000);//this is how long before it moves
                    Point point = MouseInfo.getPointerInfo().getLocation();
                    Robot robot = new Robot();
                    robot.mouseMove(point.x, point.y);
                } catch (InterruptedException | AWTException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public JsonMessageParser getParser()
    {
        return this.parser;
    }

    public PostItComponents getPostItComponents()
    {
        return this.postItComponents;
    }

    @Override
    public void update(Observable o, Object obj)
    {
        if (obj instanceof MqttMessage)
        {
            Thread thread = new Thread(()->{
                String str = obj.toString();
                System.out.println(str);

                parser.parseMessage(str);
                parser.parseContent();
            });
            thread.start();
        }
        else if (obj instanceof PairingManager)
        {
            changeScene(pairingPane, mainPane);
        }

    }






}
