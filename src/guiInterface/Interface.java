package guiInterface;

/**
 * Created by Axel on 10/4/2016.
 */

import clientConnection.Client;
import subscriber.SmartMirror_Subscriber;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Interface extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../fxml/Interface.fxml"));
        primaryStage.setTitle("SmartMirror");
        primaryStage.setScene(new Scene(root, 480, 640));
        primaryStage.show();

        Client client = new Client("tcp://codehigh.ddns.net:1883", "Tester");
        //guiController guiCont = new guiController();
        SmartMirror_Subscriber sms = new SmartMirror_Subscriber(client, "test");
        //sms.addObserver(guiCont);

        System.out.println(sms.Color);
        System.out.println(sms.Text);
        System.out.println(sms.Title);

        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
    }


    public static void main(String[] args) {launch(args);}
}





