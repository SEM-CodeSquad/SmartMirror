package beaver;

import javafx.scene.control.Label;
import java.util.Observable;
import java.util.Observer;


/**
 * Created by Axel on 10/11/2016.
 */

public class guiController implements Observer{

    public Label postit;

    public void update(Observable obs, Object obj)
    {
        System.out.println(obj + "Lols");
        //TODO:
        // If the line below is uncommented it gives a weird error message
        // "MqttException (0) - java.lang.NullPointerException" and loses connection.

        //setPostit(obj);
    }

    private void setPostit(Object postitMsg){
        postit.setText(postitMsg.toString());
    }

}
