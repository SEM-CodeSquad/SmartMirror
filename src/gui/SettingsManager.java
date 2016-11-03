package gui;

import dataHandlers.Content;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import widgets.BusTimetable;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class SettingsManager implements Observer
{
    private WebView webView;
    private String busStop;
    private GridPane busPane;
    //private String newsSource;
    private LinkedList<Content> contents;

    SettingsManager(WebView webView, GridPane busPane)
    {
        this.webView = webView;
        this.busPane = busPane;
    }

    private void assignSettings()
    {
        for (int i = 0; i <= contents.size(); i++)
        {
            if (contents.remove(i).getKey().equals("busStop"))
            {
                this.busStop = contents.remove(i).getValue();
            }
        }
    }

    private void applySettings()
    {
       assignSettings();
       if (!busStop.equals(""))
       {
           Platform.runLater(this::showTimetable);
       }
    }

    private void showTimetable()
    {
        BusTimetable busTimetable = new BusTimetable();
        this.busPane.setVisible(true);
        busTimetable.setBusTimetable(this.busStop, this.webView);
    }

    @Override @SuppressWarnings("unchecked")
    public void update(Observable o, Object arg) {
        if (arg.equals("settings"))
        {

            applySettings();
        }
    }
}
