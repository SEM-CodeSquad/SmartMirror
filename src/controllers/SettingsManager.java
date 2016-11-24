package controllers;

import dataModels.Device;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import widgets.BusTimetable;

import java.util.Observable;
import java.util.Observer;

public class SettingsManager implements Observer
{
    private final InterfaceController gui;
    private WebView webView;
    private String busStop;
    private GridPane busPane;
    private Device settingsContent;

    SettingsManager(WebView webView, GridPane busPane, InterfaceController gui)
    {
        this.webView = webView;
        this.busPane = busPane;
        this.gui = gui;
    }

    private void assignSettings()
    {
        if (this.settingsContent.getDeviceName().equals("busStop"))
        {
            this.busStop = this.settingsContent.getStatus();
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
        //busTimetable.setBusTimetable(this.busStop/*,this.webView*/, this.busPane); // Not needed anymore i think / Axel
    }

    @Override @SuppressWarnings("unchecked")
    public void update(Observable o, Object arg) {
        if (arg.equals("settings"))
        {
            this.settingsContent = gui.getParser().getSettings();
            applySettings();
        }
    }
}
