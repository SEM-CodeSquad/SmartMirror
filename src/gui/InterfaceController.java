package gui;

import com.sun.java.swing.plaf.windows.WindowsBorders;
import dataHandlers.QRCode;
import dataHandlers.UUID_Generator;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import mqttClient.MQTTClient;
import mqttClient.SmartMirror_Subscriber;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import widgets.TimeDateManager;

import java.io.File;
import java.util.*;

public class InterfaceController implements Observer {

    public WebView webViewBus;
    public AnchorPane mainPane;
    public GridPane postItsGrid;
    public Label device1;
    public Label device2;
    public Label device3;
    public Label device4;
    public Label device5;
    public Label device6;
    public Label device7;
    public Label device8;
    public Label device9;
    public Label device10;
    public ImageView imgDevice1;
    public ImageView imgDevice2;
    public ImageView imgDevice3;
    public ImageView imgDevice4;
    public ImageView imgDevice5;
    public ImageView imgDevice7;
    public ImageView imgDevice6;
    public ImageView imgDevice8;
    public ImageView imgDevice9;
    public ImageView imgDevice10;
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
    public StackPane postPane13;
    public StackPane postPane10;
    public StackPane postPane11;
    public StackPane postPane12;
    public StackPane postPane14;
    public StackPane postPane15;
    public StackPane postPane16;
    public StackPane postPane17;
    public StackPane postPane18;
    public TextArea postText1;
    public Label postId1;


    private String clientId;
    private MQTTClient mqttClient;


    public InterfaceController()
    {
        Platform.runLater(this::setUp);

    }

    private void setUp()
    {
        UUID_Generator uuid_generator = new UUID_Generator();
        clientId = uuid_generator.getUUID();
        QRCode qrCode = new QRCode(clientId);
        imgQRCode.setImage(qrCode.getQRCode());
        qrCodeView.setImage(qrCode.getQRCode());
        TimeDateManager timeDateManager = new TimeDateManager();
        timeDateManager.bindToTime(this.timePairingScreen);
        timeDateManager.bindToDate(this.datePairingScreen);
        timeDateManager.bindToDay(this.dayNamePairingScreen);
        timeDateManager.bindToTime(this.time);
        timeDateManager.bindToDate(this.date);
        timeDateManager.bindToDay(this.dayName);
        File f = new File("resources/post-it-.png");
        Image i = new Image(f.toURI().toString());




        postId1.setStyle("-fx-background-color: linear-gradient(#329932, #66b266),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #008000 45%, #004c00 50%);" +
                "-fx-border-radius: 10 10 10 10;\n" +
                "  -fx-background-radius: 10 10 10 10;" +
                "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");

        postPane1.setStyle("-fx-background-image: url('" + i.impl_getUrl() + "'); " +
                "-fx-background-repeat: stretch;" +
                "-fx-background-size: 320 101;" +
                "    -fx-background-position: center center;" +
                "    -fx-effect: dropshadow(three-pass-box, black, 30, 0.5, 0, 0);");




//        Image image = new Image(f.toURI().toString());
//
//        BackgroundSize backgroundSize = new BackgroundSize(100, 100, false, false, false, false);
//        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
//        Background background = new Background(backgroundImage);
//
//        postIt1.setBackground(background);
//
//
//
//        authentication();

    }

    private void changeScene() throws Exception
    {

    }

    private void authentication()
    {
        //TODO set pairing code back after testing
        this.mqttClient = new MQTTClient("tcp://codehigh.ddns.me", clientId);
        SmartMirror_Subscriber subscriber = new SmartMirror_Subscriber(mqttClient, "dit029/SmartMirror/" +
                "authentication");
        subscriber.addObserver(this);
    }



    @Override
    public void update(Observable o, Object obj) {


    }



    private void setPost(String title, String msg, String color){
//        String newLine = System.getProperty("line.separator");
//        postNote.setPrefColumnCount(4);
//        postNote.setPrefRowCount(4);
//        postNote.setText(title + newLine + msg + newLine + color);
//        postNote.setVisible(true);
    }


}
