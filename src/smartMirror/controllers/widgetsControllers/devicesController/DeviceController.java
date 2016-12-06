package smartMirror.controllers.widgetsControllers.devicesController;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import smartMirror.dataHandlers.animations.TransitionAnimation;
import smartMirror.dataHandlers.commons.JsonMessageParser;
import smartMirror.dataModels.applicationModels.Preferences;
import smartMirror.dataModels.widgetsModels.devicesModels.Device;
import smartMirror.dataModels.widgetsModels.devicesModels.DevicesToggleButton;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Pucci on 21/11/2016.
 *         Class that controls the updates in the DevicesView
 */
public class DeviceController implements Observer
{


    public StackPane devicePanes;
    public GridPane gridDevices1;

    public GridPane device1;
    public Label deviceName1;

    public GridPane device2;
    public Label deviceName2;

    public GridPane device3;
    public Label deviceName3;

    public GridPane device4;
    public Label deviceName4;

    public GridPane device5;
    public Label deviceName5;

    public GridPane device6;
    public Label deviceName6;

    public GridPane device7;
    public Label deviceName7;

    public Label deviceName8;
    public GridPane device8;

    public GridPane device9;
    public Label deviceName9;

    public GridPane device10;
    public Label deviceName10;

    public GridPane gridDevices2;

    public GridPane device11;
    public Label deviceName11;

    public GridPane device12;
    public Label deviceName12;

    public GridPane device13;
    public Label deviceName13;

    public GridPane device14;
    public Label deviceName14;

    public GridPane device15;
    public Label deviceName15;

    public GridPane device16;
    public Label deviceName16;

    public GridPane device17;
    public Label deviceName17;

    public GridPane device18;
    public Label deviceName18;

    public GridPane device19;
    public Label deviceName19;

    public GridPane device20;
    public Label deviceName20;

    public StackPane deviceList2;
    public StackPane deviceList1;

    private DevicesToggleButton switchButton1;
    private DevicesToggleButton switchButton2;
    private DevicesToggleButton switchButton3;
    private DevicesToggleButton switchButton4;
    private DevicesToggleButton switchButton5;
    private DevicesToggleButton switchButton6;
    private DevicesToggleButton switchButton7;
    private DevicesToggleButton switchButton8;
    private DevicesToggleButton switchButton9;
    private DevicesToggleButton switchButton10;
    private DevicesToggleButton switchButton11;
    private DevicesToggleButton switchButton12;
    private DevicesToggleButton switchButton13;
    private DevicesToggleButton switchButton14;
    private DevicesToggleButton switchButton15;
    private DevicesToggleButton switchButton16;
    private DevicesToggleButton switchButton17;
    private DevicesToggleButton switchButton18;
    private DevicesToggleButton switchButton19;
    private DevicesToggleButton switchButton20;

    private GridPane[] gridPanes;
    private Label[] labels;
    private DevicesToggleButton[] switchButtons;

    private boolean visible = false;

    /**
     * Constructor method instantiates each toggle button that is loaded in the Devices view
     *
     * @see DevicesToggleButton
     */
    public DeviceController()
    {
        switchButton1 = new DevicesToggleButton();
        switchButton2 = new DevicesToggleButton();
        switchButton3 = new DevicesToggleButton();
        switchButton4 = new DevicesToggleButton();
        switchButton5 = new DevicesToggleButton();
        switchButton6 = new DevicesToggleButton();
        switchButton7 = new DevicesToggleButton();
        switchButton8 = new DevicesToggleButton();
        switchButton9 = new DevicesToggleButton();
        switchButton10 = new DevicesToggleButton();
        switchButton11 = new DevicesToggleButton();
        switchButton12 = new DevicesToggleButton();
        switchButton13 = new DevicesToggleButton();
        switchButton14 = new DevicesToggleButton();
        switchButton15 = new DevicesToggleButton();
        switchButton16 = new DevicesToggleButton();
        switchButton17 = new DevicesToggleButton();
        switchButton18 = new DevicesToggleButton();
        switchButton19 = new DevicesToggleButton();
        switchButton20 = new DevicesToggleButton();
        Platform.runLater(this::setUp);
    }

    /**
     * Method responsible for grouping components to facilitate manipulation, then it adds each toggle button in the interface
     */
    private void setUp()
    {
        this.devicePanes.visibleProperty().addListener((observableValue, aBoolean, aBoolean2) ->
                visible = devicePanes.isVisible());

        gridPanes = new GridPane[]{device1, device2, device3, device4, device5, device6, device7, device8,
                device9, device10, device11, device12, device13, device14, device15, device16, device17,
                device18, device19, device20};

        labels = new Label[]{deviceName1, deviceName2, deviceName3, deviceName4, deviceName5, deviceName6,
                deviceName7, deviceName8, deviceName9, deviceName10, deviceName11, deviceName12, deviceName13,
                deviceName14, deviceName15, deviceName16, deviceName17, deviceName18, deviceName19, deviceName20};

        switchButtons = new DevicesToggleButton[]{switchButton1, switchButton2, switchButton3, switchButton4
                , switchButton5, switchButton6, switchButton7, switchButton8, switchButton9
                , switchButton10, switchButton11, switchButton12, switchButton13, switchButton14
                , switchButton15, switchButton16, switchButton17, switchButton18, switchButton19
                , switchButton20};

        for (int i = 0; i < gridPanes.length; i++)
        {
            gridPanes[i].add(switchButtons[i], 1, 0);
            GridPane.setMargin(switchButtons[i], new Insets(0, 0, 0, 5));
            GridPane.setHalignment(switchButtons[i], HPos.RIGHT);
        }

        TransitionAnimation animation = new TransitionAnimation();
        animation.transitionAnimation(3, 2, this.deviceList1, this.deviceList2);
        animation.playSeqAnimation();
    }

    /**
     * Method responsible for setting this widget visible
     *
     * @param b true for visible false for not visible
     */
    private synchronized void setVisible(boolean b)
    {
        Platform.runLater(() ->
        {
            this.devicePanes.setVisible(b);
            StackPane parentPane = (StackPane) this.devicePanes.getParent();
            GridPane parentGrid = (GridPane) parentPane.getParent();

            monitorWidgetVisibility(parentPane, parentGrid);
        });
    }

    /**
     * Method responsible for setting the parent visibility. In case of all the widgets in the parent are not visible
     * the parent also shall be not visible and vice-versa
     *
     * @param stackPane parent component
     * @param gridPane  parent parent component
     */
    private synchronized void monitorWidgetVisibility(StackPane stackPane, GridPane gridPane)
    {
        boolean visible = false;
        ObservableList<Node> list = stackPane.getChildren();
        for (Node node : list)
        {
            visible = node.isVisible();
        }
        gridPane.setVisible(visible);
    }

    /**
     * Method responsible to ensure that only one widget is showing at the time in the parent
     */
    private void enforceView()
    {
        if (!visible)
        {
            StackPane sPane = (StackPane) this.devicePanes.getParent();

            ObservableList<Node> list = sPane.getChildren();
            for (Node node : list)
            {
                node.setVisible(false);
            }

            this.devicePanes.setVisible(true);
        }
    }

    /**
     * Method responsible for setting the widget holder visible
     */
    private synchronized void setParentVisible()
    {
        Platform.runLater(() ->
        {
            GridPane gridPane = (GridPane) this.devicePanes.getParent().getParent();
            if (gridPane.getOpacity() != 1)
            {
                gridPane.setVisible(true);
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), gridPane);

                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();
            }
        });
    }

    /**
     * Method responsible for setting the device info on the specified component
     *
     * @param l    component to set the info on
     * @param text info to be set
     */
    private synchronized void setInfo(Label l, String text)
    {
        l.setVisible(true);
        Platform.runLater(() -> l.setText(text));
    }

    /**
     * Method responsible for setting the status of the device in the toggle button
     *
     * @param button toggle button to set the status on
     * @param status status to be set true for on or false to off
     */
    private synchronized void setStatus(DevicesToggleButton button, String status)
    {
        Platform.runLater(() -> button.switchProperty().set(status.equals("true")));
    }

    /**
     * Method responsible for setting the opacity of a component from 0 to 1 in a animation
     *
     * @param gridPane component to be set visible
     */
    private synchronized void animationFadeIn(GridPane gridPane)
    {
        Platform.runLater(() ->
        {
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), gridPane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
    }

    /**
     * Method responsible for setting the opacity of a component from 1 to 0 in a animation
     *
     * @param gridPane component to be set not visible
     */
    private synchronized void animationFadeOut(GridPane gridPane)
    {
        Platform.runLater(() ->
        {
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), gridPane);
            fadeIn.setFromValue(1);
            fadeIn.setToValue(0);
            if (gridPane.getOpacity() == 1) fadeIn.play();
        });
    }

    /**
     * Update method where the observable classes sends notifications messages
     *
     * @param o   observable object
     * @param arg object arg
     */
    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object arg)
    {
        if (arg instanceof MqttMessage)
        {
            Thread thread = new Thread(() ->
            {
                JsonMessageParser parser = new JsonMessageParser();
                parser.parseMessage(arg.toString());

                if (parser.getContentType().equals("device"))
                {
                    LinkedList<Device> list = parser.parseDeviceList();
                    enforceView();
                    setParentVisible();
                    for (int i = 0; i < labels.length; i++)
                    {
                        if (list.size() > 0)
                        {
                            setInfo(labels[i], list.peek().getDeviceName());
                            setStatus(switchButtons[i], list.remove().getStatus());
                            animationFadeIn(gridPanes[i]);
                        }
                        else
                        {
                            animationFadeOut(gridPanes[i]);
                            setInfo(labels[i], "");
                            setStatus(switchButtons[i], "off");
                        }
                    }
                }
                else if (parser.getContentType().equals("preferences"))
                {
                    LinkedList<Preferences> list = parser.parsePreferenceList();

                    list.stream().filter(pref -> pref.getName().equals("device")).forEach(pref ->
                            setVisible(pref.getValue().equals("true")));
                }
            });
            thread.start();

        }
    }
}
