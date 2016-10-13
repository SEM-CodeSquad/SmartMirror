package guiInterface;/**
 * Created by Axel on 10/4/2016.
 */

import clientConnection.Client;
import subscriber.SmartMirror_Subscriber;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class InterfaceBinary extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/Interface.fxml"));
        primaryStage.setTitle("SmartMirror");
        primaryStage.setScene(new Scene(root, 480, 640));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
    }


    public static void main(String[] args) {launch(args);

        Client client = new Client("tcp://codehigh.ddns.net:1883", "Tester");
        new SmartMirror_Subscriber(client, "test");
    }
}




