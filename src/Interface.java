import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Interface extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Interface.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 480, 640);
        primaryStage.setTitle("SmartMirror");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);

        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
    }


    public static void main(String[] args) {launch(args);}
}





