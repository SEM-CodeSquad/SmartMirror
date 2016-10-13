package guiInterface;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Observable;
import java.util.Observer;


/**
 * Created by Axel on 10/11/2016.
 */

public class GUIcontroller implements Observer{

    @FXML
    private Label postit123;

    public GUIcontroller() {


    }


    public void update(Observable obs, Object obj)
    {
        System.out.println(obj.toString()+"asdasd");
        Gson gson = new Gson();
        Postit postit22 = gson.fromJson(obj.toString(), Postit.class);
        System.out.print(postit22.Title+ "banana");
        setTitle(postit22.Title);
    }

    @FXML
    private void setTitle(String title){

        postit123.setText(title);
    }

    private void setColor(String color){


    }
    private void setText(String text){


    }


}

class Postit{
    String Title;
    String Text;
    String Color;

    public String toString() {

        return Title + " "+ Text + " " + Color;
    }

}