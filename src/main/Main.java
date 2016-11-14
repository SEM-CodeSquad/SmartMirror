package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaceView/MainInterface.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 480, 640);
        primaryStage.setTitle("SmartMirror");
        primaryStage.setScene(scene);
        primaryStage.show();


        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    //DO NOT DELETE
//    public static void main(String[] args) {launch(args);}
}